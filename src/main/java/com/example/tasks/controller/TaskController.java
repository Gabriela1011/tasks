package com.example.tasks.controller;

import com.example.tasks.dto.TaskDTO;
import com.example.tasks.dto.UpdateTaskContentDTO;
import com.example.tasks.dto.UpdateTaskDTO;
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

    @PostMapping
    public ResponseEntity<List<TaskDTO>> addTasks(@RequestBody List<TaskDTO> tasksList) {
        List<TaskDTO> createdTasks =  taskService.addTasks(tasksList);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTasks);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTaskContent(@RequestBody UpdateTaskContentDTO task, @PathVariable Long id) {
        return ResponseEntity.ok(taskService.updateTaskContent(task, id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@RequestBody UpdateTaskDTO task, @PathVariable Long id) {
        return ResponseEntity.ok(taskService.updateTask(task, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
         taskService.deleteTask(id);
         return ResponseEntity.noContent().build();
    }
}
