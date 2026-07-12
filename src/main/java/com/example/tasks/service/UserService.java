package com.example.tasks.service;

import com.example.tasks.domain.User;
import com.example.tasks.dto.request.SaveUserDTO;
import com.example.tasks.dto.response.UserDTO;
import com.example.tasks.mapper.UserMapper;
import com.example.tasks.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Transactional
    public UserDTO createUser(SaveUserDTO dto){
        User user = userMapper.toEntity(dto);
        User savedUser = userRepository.save(user);

        log.info("User created!");
        return userMapper.toDto(savedUser);
    }
}
