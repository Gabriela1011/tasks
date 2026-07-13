package com.example.tasks.repository;

import com.example.tasks.domain.Task;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Override
    @NonNull
    @EntityGraph(attributePaths = {"statusType", "user"})
    List<Task> findAll();

    @Override
    @NonNull
    @EntityGraph(attributePaths = {"statusType", "user"})
    Optional<Task> findById(@NonNull Long id);

    @EntityGraph(attributePaths = {"statusType", "user"})
    List<Task> findByDueDateBefore(LocalDateTime dueDate);

    @EntityGraph(attributePaths = {"statusType", "user"})
    List<Task> findByStatusType_StatusName(String statusName);

    @EntityGraph(attributePaths = {"statusType", "user"})
    List<Task> findByDueDateBeforeAndStatusType_StatusName(LocalDateTime dueDate, String statusName);
}
