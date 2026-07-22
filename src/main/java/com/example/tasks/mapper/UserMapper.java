package com.example.tasks.mapper;

import com.example.tasks.domain.User;
import com.example.tasks.dto.response.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toDto(User user) {
        return UserDTO.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .birthDate(user.getBirthDate())
                .isInternal(user.getIsInternal())
                .createdBy(user.getCreatedBy())
                .creationDate(user.getCreationDate())
                .lastUpdatedBy(user.getLastUpdatedBy())
                .lastUpdateDate(user.getLastUpdateDate())
                .createdByFullname(user.getCreatedByFullname())
                .build();
    }

//TODO:REVIEW
//    public UserDetailsDTO toUserDetailsDTO(User user) {
//        return UserDetailsDTO.builder()
//                .userId(user.getUserId())
//                .username(user.getUsername())
//                .email(user.getEmail())
//                .build();
//    }
}
