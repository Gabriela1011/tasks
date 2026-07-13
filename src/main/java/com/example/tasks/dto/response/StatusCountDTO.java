package com.example.tasks.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatusCountDTO {
    private String statusName;
    private Long count;
}