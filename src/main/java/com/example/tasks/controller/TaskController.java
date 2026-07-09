package com.example.tasks.controller;

import com.example.tasks.dto.request.UpdateTaskStatusDTO;
import com.example.tasks.dto.response.TaskDTO;
import com.example.tasks.dto.request.UpdateTaskContentDTO;
import com.example.tasks.dto.request.UpdateTaskDTO;
import com.example.tasks.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<TaskDTO> addTask(@RequestBody TaskDTO task) {
        TaskDTO createdTask = taskService.addTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @PostMapping("/batch")
    public ResponseEntity<List<TaskDTO>> addTasks(@RequestBody List<TaskDTO> tasksList) {
        List<TaskDTO> createdTasks =  taskService.addTasks(tasksList);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTasks);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@RequestBody UpdateTaskDTO task, @PathVariable Long id) {
        return ResponseEntity.ok(taskService.updateTask(task, id));
    }

    @PatchMapping("/{id}/content")
    public ResponseEntity<TaskDTO> updateTaskContent(@RequestBody UpdateTaskContentDTO task, @PathVariable Long id) {
        return ResponseEntity.ok(taskService.updateTaskContent(task, id));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskDTO> updateTaskStatus(@RequestBody UpdateTaskStatusDTO task, @PathVariable Long id) {
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
