package com.example.tasks.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserDetailsDTO {
    private Long userId;
    private String username;
    private String email;
}
