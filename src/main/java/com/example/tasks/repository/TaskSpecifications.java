package com.example.tasks.repository;

import com.example.tasks.domain.Task;
import com.example.tasks.exception.InvalidDateRangeException;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TaskSpecifications {

    private TaskSpecifications() {
    }

    public static Specification<Task> hasTaskNameLike(String taskName) {
        String pattern = "%" + taskName.toLowerCase() + "%";
        return (root, query, cb) -> cb.like(cb.lower(root.get("taskName")), pattern);
    }

    public static Specification<Task> hasUserIdIn(List<Long> userIds) {
        return (root, query, cb) -> root.get("user").get("userId").in(userIds);
    }

    public static Specification<Task> hasStatusIn(List<String> statuses) {
        return (root, query, cb) -> root.get("statusType").get("statusName").in(statuses);
    }

    public static Specification<Task> hasDueDateBefore(LocalDateTime dueBefore) {
        return (root, query, cb) -> cb.lessThan(root.get("dueDate"), dueBefore);
    }

    public static Specification<Task> hasDueDateBetween(LocalDate from, LocalDate to) {
        if (from != null && to != null && to.isBefore(from)) {
            throw new InvalidDateRangeException();
        }

        return (root, query, cb) -> {
            LocalDate effectiveFrom = (from != null) ? from : to;
            LocalDate effectiveTo = (to != null) ? to : from;

            return cb.and(
                    cb.greaterThanOrEqualTo(root.get("dueDate"), effectiveFrom.atStartOfDay()),
                    cb.lessThan(root.get("dueDate"), effectiveTo.plusDays(1).atStartOfDay())
            );
        };
    }

    // Mirrors the @EntityGraph(attributePaths = {"statusType", "user"})
    public static Specification<Task> fetchAssociations() {
        return (root, query, cb) -> {
            if (Long.class != query.getResultType() && long.class != query.getResultType()) {
                root.fetch("statusType", JoinType.LEFT);
                root.fetch("user", JoinType.LEFT);
            }
            return cb.conjunction();
        };
    }
}
