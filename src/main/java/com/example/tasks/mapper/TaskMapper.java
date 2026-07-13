package com.example.tasks.mapper;

import com.example.tasks.domain.StatusType;
import com.example.tasks.domain.Task;
import com.example.tasks.domain.User;
import com.example.tasks.dto.request.CreateTaskDTO;
import com.example.tasks.dto.response.TaskDTO;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public TaskDTO toDto(Task task) {
        TaskDTO.TaskDTOBuilder builder = TaskDTO.builder()
                .taskId(task.getTaskId())
                .taskName(task.getTaskName())
                .content(task.getContent())
                .dueDate(task.getDueDate())
                .createdBy(task.getCreatedBy())
                .creationDate(task.getCreationDate())
                .lastUpdatedBy(task.getLastUpdatedBy())
                .lastUpdateDate(task.getLastUpdateDate())
                .createdByFullname(task.getCreatedByFullname());

        if(task.getStatusType() != null) {
            builder.statusTypeId(task.getStatusType().getStatusTypeId())
                    .statusName(task.getStatusType().getStatusName());
        }

        if(task.getUser() != null) {
            builder.userId(task.getUser().getUserId())
                    .username(task.getUser().getUsername());
        }

        return builder.build();
    }

    public Task toEntity(CreateTaskDTO dto, StatusType statusType, User user) {
        return Task.builder()
                .taskName(dto.getTaskName())
                .content(dto.getContent())
                .dueDate(dto.getDueDate())
                .statusType(statusType)
                .user(user)
                .build();
    }
}
