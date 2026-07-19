package com.example.tasks.dto.request;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UpdateUserDTO {

    @Size(max = 500)
    private String username;

    @Past
    private LocalDate birthDate;

    //TODO: add another endpoint PATCH /users/{id}/promote once auth exists
    //private Boolean isInternal;
}
