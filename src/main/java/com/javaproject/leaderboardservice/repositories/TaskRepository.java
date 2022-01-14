package com.javaproject.leaderboardservice.repositories;

import com.javaproject.leaderboardservice.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
