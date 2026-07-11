package com.example.tasks.service;

import com.example.tasks.dto.request.UpdateTaskStatusDTO;
import com.example.tasks.dto.response.TaskDTO;
import com.example.tasks.dto.request.UpdateTaskContentDTO;
import com.example.tasks.dto.request.UpdateTaskDTO;
import com.example.tasks.exception.NoSearchCriteriaProvidedException;
import com.example.tasks.exception.TaskNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TaskService {
    private List<TaskDTO> tasks = new ArrayList<>();

    public List<TaskDTO> getTasks() {
        log.info("Getting tasks: ");
        return tasks;
    }

    public List<TaskDTO> searchTasks(LocalDateTime dueBefore, String status) {
        boolean hasDueBefore = (dueBefore != null);
        boolean hasStatus = StringUtils.hasText(status);

        if(!hasDueBefore && !hasStatus) {
            throw new NoSearchCriteriaProvidedException();
        }

        List<TaskDTO> filteredTasks = tasks.stream()
                                .filter(task -> !hasDueBefore || task.getDueDate().isBefore(dueBefore))
                                .filter(task -> !hasStatus || task.getStatus().equals(status))
                                .toList();

        log.info("Found {} tasks matching search criteria", filteredTasks.size());
        return filteredTasks;
    }

    public TaskDTO getTaskById(Long id) {
        TaskDTO existingTask = findTaskOrThrow(id);

        log.info("Found task with id {}: {}", id, existingTask);
        return existingTask;
    }

    public TaskDTO addTask(TaskDTO taskDTO) {
        TaskDTO builtTask = buildTask(taskDTO);
        tasks.add(builtTask);

        log.info("Added task: {}", builtTask);
        return builtTask;
    }

    public List<TaskDTO> addTasks(List<TaskDTO> tasksList) {
        List<TaskDTO> createdTasks = new ArrayList<>();

        for(TaskDTO task : tasksList) {
            TaskDTO builtTask = buildTask(task);
            tasks.add(builtTask);
            createdTasks.add(builtTask);
        }

        log.info("Added tasks: {}", createdTasks);
        return createdTasks;
    }

    public TaskDTO updateTask(UpdateTaskDTO task, Long id) {
        TaskDTO existingTask = findTaskOrThrow(id);
        existingTask.setContent(task.getContent());
        existingTask.setDueDate(task.getDueDate());
        existingTask.setStatus(task.getStatus());

        log.info("Updated task with id {}: {}", id, existingTask);
        return existingTask;
    }

    public TaskDTO updateTaskContent(UpdateTaskContentDTO task, Long id) {
        TaskDTO existingTask = findTaskOrThrow(id);
        existingTask.setContent(task.getContent());

        log.info("Updated task content with id {}: {}", id, existingTask);
        return existingTask;
    }

    public TaskDTO updateTaskStatus(UpdateTaskStatusDTO task, Long id) {
        TaskDTO existingTask = findTaskOrThrow(id);
        existingTask.setStatus(task.getStatus());

        log.info("Updated task status with id {}: {}", id, existingTask);
        return existingTask;
    }

    public void deleteTask(Long id) {
        boolean removed = tasks.removeIf(task -> task.getId().equals(id));

        if(!removed){
            log.warn("Task with id {} not found for deletion", id);
            throw new TaskNotFoundException(id);
        }

        log.info("Deleted task with id {}", id);
    }

    public void deleteAllTasks() {
        int deletedCount = tasks.size();
        tasks.clear();
        log.info("Deleted all tasks. Count: {}", deletedCount);
    }

    private TaskDTO buildTask(TaskDTO task) {
        return TaskDTO.builder()
                .id(task.getId())
                .content(task.getContent())
                .dueDate(task.getDueDate())
                .status(task.getStatus())
                .build();
    }

    private TaskDTO findTaskOrThrow(Long id) {
        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> {
                    log.warn("Task with id {} not found", id);
                    return new TaskNotFoundException(id);
                });
    }
}
