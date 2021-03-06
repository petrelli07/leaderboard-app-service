package com.javaproject.leaderboardservice.repositories;

import com.javaproject.leaderboardservice.model.Task;
import com.javaproject.leaderboardservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByVerificationCode(long verification_code);
}
