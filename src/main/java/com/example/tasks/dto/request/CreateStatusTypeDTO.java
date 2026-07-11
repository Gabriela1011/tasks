package com.example.tasks.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CreateStatusTypeDTO {

    @NotBlank
    @Size(max = 255)
    private String statusName;

    @NotBlank
    @Size(max = 50)
    private String createdBy;
}
