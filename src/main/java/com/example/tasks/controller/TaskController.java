package com.example.tasks.controller;

import com.example.tasks.dto.request.CreateTaskDTO;
import com.example.tasks.dto.request.UpdateTaskDTO;
import com.example.tasks.dto.request.UpdateTaskStatusDTO;
import com.example.tasks.dto.response.StatusCountDTO;
import com.example.tasks.dto.response.TaskDTO;
import com.example.tasks.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getTasks() {
        return ResponseEntity.ok(taskService.getTasks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<TaskDTO>> searchTasks(
            @RequestParam(required = false) LocalDateTime dueBefore,
            @RequestParam(required = false) String status) {

        return ResponseEntity.ok(taskService.searchTasks(dueBefore, status));
    }

    @GetMapping("/users/{userId}/status-counts")
    public ResponseEntity<List<StatusCountDTO>> countTasksByStatusForUser(@PathVariable Long userId) {
        return ResponseEntity.ok(taskService.countTasksByStatusForUser(userId));
    }

    @PostMapping
    public ResponseEntity<TaskDTO> addTask(@Valid @RequestBody CreateTaskDTO task) {
        TaskDTO createdTask = taskService.addTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @PostMapping("/batch")
    public ResponseEntity<List<TaskDTO>> addTasks( @RequestBody List<@Valid CreateTaskDTO> tasksList) {
        List<TaskDTO> createdTasks =  taskService.addTasks(tasksList);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTasks);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@Valid @RequestBody UpdateTaskDTO task, @PathVariable Long id) {
        return ResponseEntity.ok(taskService.updateTask(task, id));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskDTO> updateTaskStatus(@Valid @RequestBody UpdateTaskStatusDTO task, @PathVariable Long id) {
        return ResponseEntity.ok(taskService.updateTaskStatus(task, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
         taskService.deleteTask(id);
         return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllTasks() {
        taskService.deleteAllTasks();
        return ResponseEntity.noContent().build();
    }
}
