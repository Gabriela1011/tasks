package com.example.tasks.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class LoginCredentialsDTO {
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
