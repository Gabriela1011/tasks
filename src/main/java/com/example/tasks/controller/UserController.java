package com.example.tasks.controller;

import com.example.tasks.dto.request.UpdateUserDTO;
import com.example.tasks.dto.response.UserDTO;
import com.example.tasks.dto.response.UserSummaryDTO;
import com.example.tasks.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/summary")
    public ResponseEntity<List<UserSummaryDTO>> getUserSummaries() {
        return ResponseEntity.ok(userService.getUserSummaries());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UpdateUserDTO user, @PathVariable Long id) {
        return ResponseEntity.ok(userService.updateUser(user, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
