package com.example.tasks.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SaveUserDTO {

    @NotBlank
    @Size(max = 500)
    private String username;

    private LocalDate birthDate;

    private Boolean isInternal;
}
