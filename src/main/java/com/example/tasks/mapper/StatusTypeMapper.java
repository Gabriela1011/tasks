package com.example.tasks.mapper;

import com.example.tasks.domain.StatusType;
import com.example.tasks.dto.request.CreateStatusTypeDTO;
import com.example.tasks.dto.response.StatusTypeDTO;
import org.springframework.stereotype.Component;

@Component
public class StatusTypeMapper {

    public StatusTypeDTO toDto(StatusType statusType) {
        return StatusTypeDTO.builder()
                .statusTypeId(statusType.getStatusTypeId())
                .statusName(statusType.getStatusName())
                .createdBy(statusType.getCreatedBy())
                .creationDate(statusType.getCreationDate())
                .lastUpdatedBy(statusType.getLastUpdatedBy())
                .lastUpdateDate(statusType.getLastUpdateDate())
                .build();
    }

    public StatusType toEntity(CreateStatusTypeDTO dto) {
        return StatusType.builder()
                .statusName(dto.getStatusName())
                .build();
    }
}
