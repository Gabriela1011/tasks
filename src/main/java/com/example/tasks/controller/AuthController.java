package com.example.tasks.controller;

import com.example.tasks.dto.request.LoginCredentialsDTO;
import com.example.tasks.service.AuthService;
import com.example.tasks.service.UserService;
import jakarta.validation.Valid;
import org.jose4j.lang.JoseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    //private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        //this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginCredentialsDTO loginCredentials) throws JoseException {
        String token = authService.login(loginCredentials);
        return ResponseEntity.ok(token);
    }

//    @PostMapping("/register")
//    public ResponseEntity<UserDetailsDTO> register(@Valid @RequestBody RegisterUserDTO user) {
//        UserDetailsDTO createdUser = userService.createUser(user);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
//    }
}
