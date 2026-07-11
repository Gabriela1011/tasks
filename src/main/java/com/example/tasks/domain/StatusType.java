package com.example.tasks.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "status_types")
public class StatusType {

    @Id
    @Column(name = "status_type_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String statusTypeId;

    @Column(name = "status_name", length = 255, nullable = false)
    private String statusName;

    @Column(name = "created_by", length = 50)
    private String createdBy;

    @Column(name = "creation_date")
    @Builder.Default
    private LocalDateTime creationDate = LocalDateTime.now();
}
