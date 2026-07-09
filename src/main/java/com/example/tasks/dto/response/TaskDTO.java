package com.example.tasks.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TaskDTO {
    @NotNull
    private Long id;

    @NotBlank
    private String content;

    @NotNull
    private LocalDateTime dueDate;

    @NotBlank
    private String status;
}
