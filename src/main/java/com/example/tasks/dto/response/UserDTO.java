package com.example.tasks.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long userId;
    private String username;
    private LocalDate birthDate;
    private Boolean isInternal;
    private String createdBy;
    private LocalDateTime creationDate;
    private String lastUpdatedBy;
    private LocalDateTime lastUpdateDate;
    private String createdByFullname;
}
