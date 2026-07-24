package com.example.tasks.controller;

import com.example.tasks.dto.request.LoginCredentialsDTO;
import com.example.tasks.dto.request.RegisterUserDTO;
import com.example.tasks.dto.response.AuthResponseDTO;
import com.example.tasks.service.AuthService;
import jakarta.validation.Valid;
import org.jose4j.lang.JoseException;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginCredentialsDTO loginCredentials) throws JoseException {
        return ResponseEntity.ok(authService.login(loginCredentials));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody RegisterUserDTO user) throws JoseException {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(user));
    }
}
