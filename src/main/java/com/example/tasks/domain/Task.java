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
@Table(name = "tasks")
public class Task {
    @Id
    @Column(name = "task_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    @Column(name = "task_name", length = 500, nullable = false)
    private String taskName;

    @Column(name = "content", length = 4000)
    private String content;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "status_type_id", nullable = false)
    private StatusType statusType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

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
