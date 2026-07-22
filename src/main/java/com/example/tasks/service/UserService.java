package com.example.tasks.service;

import com.example.tasks.domain.User;
import com.example.tasks.dto.request.UpdateUserDTO;
import com.example.tasks.dto.response.UserDTO;
import com.example.tasks.dto.response.UserSummaryDTO;
import com.example.tasks.exception.DuplicateFieldException;
import com.example.tasks.exception.NoFieldsProvidedException;
import com.example.tasks.exception.ResourceNotFoundException;
import com.example.tasks.mapper.UserMapper;
import com.example.tasks.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDTO> getAllUsers() {
        log.info("Users retrieved!");
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    public List<UserSummaryDTO> getUserSummaries() {
        log.info("User summaries retrieved!");
        return userRepository.findAllAsUserSummary();
    }

    @Transactional
    public UserDTO updateUser(UpdateUserDTO dto, Long id) {
        if (dto.getUsername() == null && dto.getBirthDate() == null) {
            throw new NoFieldsProvidedException("At least one field must be provided for user update");
        }

        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(User.class, id));

        Optional.ofNullable(dto.getUsername()).ifPresent(newUsername -> {
            if (!newUsername.equals(user.getUsername()) && userRepository.existsByUsername(newUsername)) {
                throw new DuplicateFieldException(User.class, "username", newUsername);
            }
            user.setUsername(newUsername);
        });
        Optional.ofNullable(dto.getBirthDate()).ifPresent(user::setBirthDate);

        log.info("Updated user with id {}: {}", id, user);
        return userMapper.toDto(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        if(!userRepository.existsById(id)) {
            throw new ResourceNotFoundException(User.class, id);
        }

        userRepository.deleteById(id);
        log.info("Deleted user with id {}", id);
    }
}
