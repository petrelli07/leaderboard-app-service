package com.javaproject.leaderboardservice.repositories;

import com.javaproject.leaderboardservice.model.Task;
import com.javaproject.leaderboardservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
// @Query(nativeQuery = true, value = "SELECT u.username , sum(t.point) AS points FROM user u INNER JOIN task t ON u.id = t.user_id GROUP BY u.username")
 @Query(nativeQuery = true, value = "SELECT u.username as username, sum(t.point) AS points FROM user u INNER JOIN task as  t ON u.id = t.user_id GROUP BY u.username")
   List<Object> find();


//    SELECT u.username as username, sum(t.points) AS points FROM users u  INNER JOIN tasks as  t ON u.id = t.user_id WHERE (t.status = :status) AND YEAR (t.created_at) = :year  AND MONTH (t.created_at) = :month  GROUP BY u.username

   // @Query(nativeQuery = true, value = "select user.*, sum(task.point) from user join task on task.user_id = user.id GROUP BY user.id")
//@Query(nativeQuery = true, value = "SELECT e FROM user e LEFT JOIN task a ON e.id = a.user_id")
}
