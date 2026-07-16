package com.example.tasks.controller;

import com.example.tasks.dto.request.LoginCredentialsDTO;
import com.example.tasks.dto.response.UserDetailsDTO;
import com.example.tasks.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDetailsDTO> login(@Valid @RequestBody LoginCredentialsDTO loginCredentials) {
        UserDetailsDTO loggedInUser = authService.login(loginCredentials);
        return ResponseEntity.ok(loggedInUser);
    }
}
