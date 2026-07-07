package com.example.tasks.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UpdateTaskContentDTO {
    private String content;
}
