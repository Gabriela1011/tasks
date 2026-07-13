package com.example.tasks.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TaskDTO {
    private Long taskId;
    private String taskName;
    private String content;
    private LocalDateTime dueDate;
    private String statusTypeId;
    private String statusName;
    private Long userId;
    private String username;
    private String createdBy;
    private LocalDateTime creationDate;
    private String lastUpdatedBy;
    private LocalDateTime lastUpdateDate;
    private String createdByFullname;
}
