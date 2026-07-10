package com.example.tasks.repository;

import com.example.tasks.domain.StatusType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusTypeRepository extends JpaRepository<StatusType, String> {}
