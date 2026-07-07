package com.example.tasks.service;

import com.example.tasks.dto.TaskDTO;
import com.example.tasks.dto.UpdateTaskContentDTO;
import com.example.tasks.dto.UpdateTaskDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TaskService {
    private List<TaskDTO> tasks = new ArrayList<>();

    public List<TaskDTO> getTasks() {
        log.info("Getting tasks: ");
        return tasks;
    }

    public List<TaskDTO> addTasks(List<TaskDTO> tasksList) {
        List<TaskDTO> createdTasks = new ArrayList<>();

        for(TaskDTO task : tasksList) {
            TaskDTO builtTask = buildTask(task);
            tasks.add(builtTask);
            createdTasks.add(builtTask);
            log.info("Added task: {}", builtTask);
        }

        return createdTasks;
    }

    public TaskDTO updateTaskContent(UpdateTaskContentDTO task, Long id) {
        for (TaskDTO existingTask : tasks) {
            if (existingTask.getId().equals(id)) {
                existingTask.setContent(task.getContent());
                log.info("Updated task content with id {}: {}", id, existingTask);
                return existingTask;
            }
        }
        log.warn("Task with id {} not found for update its content", id);
        return null; //exception
    }

    public TaskDTO updateTask(UpdateTaskDTO task, Long id) {
        for (TaskDTO existingTask : tasks) {
            if (existingTask.getId().equals(id)) {
                existingTask.setContent(task.getContent());
                existingTask.setDueDate(task.getDueDate());
                existingTask.setStatus(task.getStatus());
                log.info("Updated task with id {}: {}", id, existingTask);
                return existingTask;
            }
        }
        log.warn("Task with id {} not found for update", id);
        return null; //exception
    }

    public void deleteTask(Long id) {
        boolean removed = tasks.removeIf(task -> task.getId().equals(id));

        if(!removed){
            log.warn("Task with id {} not found for deletion", id);
            return; //exception
        }

        log.info("Deleted task with id {}", id);
    }

    private TaskDTO buildTask(TaskDTO task) {
        return TaskDTO.builder()
                .id(task.getId())
                .content(task.getContent())
                .dueDate(task.getDueDate())
                .status(task.getStatus())
                .build();
    }



}
