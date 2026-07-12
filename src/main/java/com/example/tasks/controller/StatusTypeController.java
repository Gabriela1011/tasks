package com.example.tasks.controller;

import com.example.tasks.dto.request.CreateStatusTypeDTO;
import com.example.tasks.dto.response.StatusTypeDTO;
import com.example.tasks.service.StatusTypeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/statuses")
public class StatusTypeController {
    private final StatusTypeService statusTypeService;

    public StatusTypeController(StatusTypeService statusTypeService) {
        this.statusTypeService = statusTypeService;
    }

    @GetMapping
    public ResponseEntity<List<StatusTypeDTO>> getAllStatuses() {
        return ResponseEntity.ok(statusTypeService.getAllStatuses());
    }

    @PostMapping
    public ResponseEntity<StatusTypeDTO> createStatus(@Valid @RequestBody CreateStatusTypeDTO status) {
        StatusTypeDTO createdStatus =  statusTypeService.createStatus(status);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStatus);
    }
}
