package com.example.tasks.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UpdateTaskDTO {
    @NotBlank
    private String content;

    @NotNull
    private LocalDateTime dueDate;

    @NotBlank
    private String status;
}
