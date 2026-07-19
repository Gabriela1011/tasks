package com.example.tasks.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.type.NumericBooleanConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "username", length = 500, nullable = false, unique = true)
    private String username;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Convert(converter = NumericBooleanConverter.class)
    @Column(name = "is_internal", nullable = false)
    @Builder.Default
    private Boolean isInternal = false;

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
