package com.example.tasks.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CreateTaskDTO {

    @NotBlank
    @Size(max = 500)
    private String taskName;

    @Size(max = 4000)
    private String content;

    private LocalDateTime dueDate;

    @NotBlank
    private String statusTypeId;

    private Long userId;
}
