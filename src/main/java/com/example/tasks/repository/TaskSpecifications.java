package com.example.tasks.repository;

import com.example.tasks.domain.Task;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TaskSpecifications {

    private TaskSpecifications() {
    }

    public static Specification<Task> hasTaskNameLike(String taskName) {
        String pattern = "%" + taskName.toLowerCase() + "%";
        return (root, query, cb) -> cb.like(cb.lower(root.get("taskName")), pattern);
    }

    public static Specification<Task> hasUserId(Long userId) {
        return (root, query, cb) -> cb.equal(root.get("user").get("userId"), userId);
    }

    public static Specification<Task> hasStatus(String statusName) {
        return (root, query, cb) -> cb.equal(root.get("statusType").get("statusName"), statusName);
    }

    public static Specification<Task> hasDueDateBefore(LocalDateTime dueBefore) {
        return (root, query, cb) -> cb.lessThan(root.get("dueDate"), dueBefore);
    }

    public static Specification<Task> hasDueDateOn(LocalDate dueDate) {
        LocalDateTime startOfDay = dueDate.atStartOfDay();
        LocalDateTime startOfNextDay = startOfDay.plusDays(1);

        return (root, query, cb) -> cb.and(
                cb.greaterThanOrEqualTo(root.get("dueDate"), startOfDay),
                cb.lessThan(root.get("dueDate"), startOfNextDay)
        );
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
