package com.example.tasks.controller;

import com.example.tasks.dto.response.StatusTypeDTO;
import com.example.tasks.service.StatusTypeService;
import jakarta.validation.Valid;
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
    public List<StatusTypeDTO> getAllStatuses() {
        return statusTypeService.getAllStatuses();
    }

    @PostMapping
    public StatusTypeDTO createStatus(@Valid @RequestBody StatusTypeDTO statusTypeDTO) {
        return statusTypeService.createStatus(statusTypeDTO);
    }
}
