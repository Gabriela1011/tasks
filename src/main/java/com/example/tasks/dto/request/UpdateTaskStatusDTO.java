package com.example.tasks.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UpdateTaskStatusDTO {
    @NotBlank private String status;
}
