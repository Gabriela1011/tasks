package com.example.tasks.service;

import com.example.tasks.domain.StatusType;
import com.example.tasks.domain.Task;
import com.example.tasks.domain.User;
import com.example.tasks.dto.request.CreateTaskDTO;
import com.example.tasks.dto.request.UpdateTaskDTO;
import com.example.tasks.dto.request.UpdateTaskStatusDTO;
import com.example.tasks.dto.response.StatusCountDTO;
import com.example.tasks.dto.response.TaskDTO;
import com.example.tasks.exception.NoFieldsProvidedException;
import com.example.tasks.exception.ResourceNotFoundException;
import com.example.tasks.mapper.TaskMapper;
import com.example.tasks.repository.StatusTypeRepository;
import com.example.tasks.repository.TaskRepository;
import com.example.tasks.repository.TaskSpecifications;
import com.example.tasks.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {
    private final TaskRepository taskRepository;
    private final StatusTypeRepository statusTypeRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    @Value("${status.completed.id}")
    private String completedStatusId;

    public List<TaskDTO> getTasks() {
        log.info("Getting tasks: ");
        return taskRepository.findAll()
                .stream()
                .map(taskMapper::toDto)
                .toList();
    }

    public TaskDTO getTaskById(Long id) {
        Task task = findTaskOrThrow(id);

        log.info("Found task with id {}", id);
        return taskMapper.toDto(task);
    }

    public List<TaskDTO> searchTasks(String taskName, List<Long> userIds, List<String> statuses, LocalDate dueDateFrom, LocalDate dueDateTo) {
        boolean hasTaskName = StringUtils.hasText(taskName);
        boolean hasUserIds = (userIds != null && !userIds.isEmpty());
        boolean hasStatuses = (statuses != null && !statuses.isEmpty());
        boolean hasDueDateFrom = (dueDateFrom != null);
        boolean hasDueDateTo = (dueDateTo != null);

        if (!hasTaskName && !hasUserIds && !hasStatuses && !hasDueDateFrom && !hasDueDateTo) {
            throw new NoFieldsProvidedException("At least one search criterion must be provided");
        }

        Specification<Task> spec = TaskSpecifications.fetchAssociations();

        if (hasTaskName) {
            spec = spec.and(TaskSpecifications.hasTaskNameLike(taskName));
        }
        if (hasUserIds) {
            spec = spec.and(TaskSpecifications.hasUserIdIn(userIds));
        }
        if (hasStatuses) {
            spec = spec.and(TaskSpecifications.hasStatusIn(statuses));
        }
        if (hasDueDateFrom || hasDueDateTo) {
            spec = spec.and(TaskSpecifications.hasDueDateBetween(dueDateFrom, dueDateTo));
        }

        List<TaskDTO> filteredTasks = taskRepository.findAll(spec).stream()
                .map(taskMapper::toDto)
                .toList();

        log.info("Found {} tasks matching search criteria", filteredTasks.size());
        return filteredTasks;
    }

    public List<TaskDTO> searchTasksDueBefore(LocalDateTime dueBefore) {
        Specification<Task> spec = TaskSpecifications.fetchAssociations()
                .and(TaskSpecifications.hasDueDateBefore(dueBefore));

        List<TaskDTO> filteredTasks = taskRepository.findAll(spec).stream()
                .map(taskMapper::toDto)
                .toList();

        log.info("Found {} tasks due before {}", filteredTasks.size(), dueBefore);
        return filteredTasks;
    }

    public List<StatusCountDTO> countTasksByStatusForUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException(User.class, userId);
        }

        List<StatusCountDTO> result = taskRepository.countTasksByStatusForUser(userId);
        log.info("User {} has tasks in {} distinct statuses", userId, result.size());
        return result;
    }

    public List<Long> getOverdueTaskIds() {
        List<Long> overdueIds = taskRepository.findOverdueTaskIds(LocalDateTime.now(), completedStatusId);

        log.info("Found {} overdue tasks", overdueIds.size());
        return overdueIds;
    }


    @Transactional
    public TaskDTO addTask(CreateTaskDTO dto) {
        StatusType statusType = findStatusTypeOrThrow(dto.getStatusTypeId());
        User user = (dto.getUserId() != null) ? findUserOrThrow(dto.getUserId()) : null;

        Task task = taskMapper.toEntity(dto, statusType, user);
        Task savedTask = taskRepository.save(task);

        log.info("Added task: {}", savedTask);
        return taskMapper.toDto(savedTask);
    }

    @Transactional
    public List<TaskDTO> addTasks(List<CreateTaskDTO> dtos) {
        List<Task> tasks = dtos.stream()
                .map(dto -> {
                    StatusType statusType = findStatusTypeOrThrow(dto.getStatusTypeId());
                    User user = (dto.getUserId() != null) ? findUserOrThrow(dto.getUserId()) : null;
                    return taskMapper.toEntity(dto, statusType, user);
                })
                .toList();

        List<Task> savedTasks = taskRepository.saveAll(tasks);

        log.info("Added {} tasks", savedTasks.size());
        return savedTasks.stream().map(taskMapper::toDto).toList();
    }

    @Transactional
    public TaskDTO updateTask(UpdateTaskDTO dto, Long id) {
        if (dto.getTaskName() == null && dto.getContent() == null && dto.getDueDate() == null) {
            throw new NoFieldsProvidedException("At least one field must be provided for task update");
        }

        Task task = findTaskOrThrow(id);

        Optional.ofNullable(dto.getTaskName()).ifPresent(task::setTaskName);
        Optional.ofNullable(dto.getContent()).ifPresent(task::setContent);
        Optional.ofNullable(dto.getDueDate()).ifPresent(task::setDueDate);

        log.info("Updated task with id {}", id);
        return taskMapper.toDto(task);
    }

    @Transactional
    public TaskDTO updateTaskStatus(UpdateTaskStatusDTO dto, Long id) {
        Task task = findTaskOrThrow(id);
        StatusType newStatus = findStatusTypeOrThrow(dto.getStatusTypeId());

        // TODO: validate allowed status transitions (currentStatus -> newStatus)

        task.setStatusType(newStatus);

        log.info("Updated task status with id {}: {}", id, task);
        return taskMapper.toDto(task);
    }

    @Transactional
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException(Task.class, id);
        }
        taskRepository.deleteById(id);

        log.info("Deleted task with id {}", id);
    }

    @Transactional
    public void deleteAllTasks() {
        long deletedCount = taskRepository.count();
        taskRepository.deleteAll();
        log.info("Deleted all tasks. Count: {}", deletedCount);
    }


    private Task findTaskOrThrow(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> {
                        log.warn("Task with id {} not found", id);
                        return new ResourceNotFoundException(Task.class, id);
                });
    }

    private StatusType findStatusTypeOrThrow(String statusTypeId) {
        return statusTypeRepository.findById(statusTypeId)
                .orElseThrow(() -> {
                    log.warn("Status with id {} not found", statusTypeId);
                    return new ResourceNotFoundException(StatusType.class, statusTypeId);
                });
    }

    private User findUserOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.warn("User with id {} not found", userId);
                    return new ResourceNotFoundException(User.class, userId);
                });
    }
}
