package com.example.tasks.service;

import com.example.tasks.domain.User;
import com.example.tasks.dto.request.LoginCredentialsDTO;
import com.example.tasks.dto.response.UserDetailsDTO;
import com.example.tasks.exception.InvalidPasswordException;
import com.example.tasks.exception.ResourceNotFoundException;
import com.example.tasks.mapper.UserMapper;
import com.example.tasks.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDetailsDTO login(LoginCredentialsDTO loginCredentials) {
        User user = userRepository.findByEmail(loginCredentials.getEmail())
                .orElseThrow(() -> {
                    log.warn("Login attempt failed for email: {}", loginCredentials.getEmail());
                    return new ResourceNotFoundException(User.class, "email", loginCredentials.getEmail());
                });

        if (!user.getPassword().equals(loginCredentials.getPassword())) {
            log.warn("Invalid password attempt for user: {}", user.getEmail());
            throw new InvalidPasswordException();
        }

        log.info("User {} logged in successfully", user.getEmail());
        return userMapper.toUserDetailsDTO(user);
    }
}
