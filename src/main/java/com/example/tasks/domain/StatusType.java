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

    @Column(name = "created_by", length = 50, nullable = false, updatable = false)
    private String createdBy;

    @Column(name = "creation_date", nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @Column(name = "last_updated_by", length = 50, nullable = false)
    private String lastUpdatedBy;

    @Column(name = "last_update_date", nullable = false)
    private LocalDateTime lastUpdateDate;

    @Column(name = "created_by_fullname", length = 300, updatable = false)
    private String createdByFullname;


    @PrePersist
    protected void onCreate() {
        this.createdBy = "SYSTEM"; //TODO: replace once auth exists
        this.creationDate = LocalDateTime.now();
        this.createdByFullname = "SUMMER_SCHOOL";
        this.lastUpdatedBy = "SYSTEM";
        this.lastUpdateDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastUpdatedBy = "SYSTEM"; //TODO: replace once auth exists
        this.lastUpdateDate = LocalDateTime.now();
    }
}
