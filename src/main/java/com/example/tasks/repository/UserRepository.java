package com.example.tasks.repository;

import com.example.tasks.domain.User;
import com.example.tasks.dto.response.UserSummaryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Optional<User> findByEmail(String email);

    @Query("SELECT new com.example.tasks.dto.response.UserSummaryDTO(u.userId, u.username) FROM User u")
    List<UserSummaryDTO> findAllAsUserSummary();
}
