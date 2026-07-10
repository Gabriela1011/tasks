package com.example.tasks.service;

import com.example.tasks.domain.StatusType;
import com.example.tasks.dto.response.StatusTypeDTO;
import com.example.tasks.mapper.StatusTypeMapper;
import com.example.tasks.repository.StatusTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class StatusTypeService {
    private final StatusTypeRepository statusTypeRepository;
    private final StatusTypeMapper statusTypeMapper;

    public List<StatusTypeDTO> getAllStatuses() {
        log.info("Statuses retrived!");
        return statusTypeRepository.findAll()
                .stream()
                .map(statusTypeMapper::toDto)
                .toList();
    }

    @Transactional
    public StatusTypeDTO createStatus(StatusTypeDTO statusTypeDTO) {
        StatusType status = statusTypeMapper.toEntity(statusTypeDTO);
        StatusType savedStatus = statusTypeRepository.save(status);

        log.info("Status created!");
        return statusTypeMapper.toDto(savedStatus);
    }
}
