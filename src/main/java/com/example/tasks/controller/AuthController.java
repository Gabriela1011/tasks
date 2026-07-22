package com.example.tasks.controller;

import com.example.tasks.dto.request.LoginCredentialsDTO;
import com.example.tasks.dto.request.RegisterUserDTO;
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
    public ResponseEntity<String> login(@Valid @RequestBody LoginCredentialsDTO loginCredentials) throws JoseException {
        String token = authService.login(loginCredentials);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterUserDTO user) throws JoseException {
        String token = authService.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(token);
    }
}
