package com.javaproject.leaderboardservice.repositories;

import com.javaproject.leaderboardservice.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
 List<Object> findAllByUserId(Long user_id);

 @Query(nativeQuery = true, value = "SELECT u.username as username, sum(t.point) AS points FROM user u INNER JOIN task as  t ON u.id = t.user_id GROUP BY u.username")
   List<Object> find();


 @Query(nativeQuery = true, value = "SELECT u.username as username, u.id, sum(t.point) AS points FROM user u INNER JOIN task as  t ON u.id = t.user_id WHERE YEAR (t.created_at) = :year AND MONTH (t.created_at) = :month GROUP BY u.username")
  List<Object> findCurrentMonthTask(
          @Param("month") int month,
          @Param("year") int year
  );

 @Query(nativeQuery = true, value = "SELECT * FROM task WHERE (user_id = :userId) AND YEAR (created_at) = :year AND MONTH (created_at) = :month")
 List<Object> findUserCurrentMonthTask(
         @Param("month") int month,
         @Param("year") int year,
         @Param("userId") long userId
 );

 //Page<Object> findByLastname(String lastname, Pageable pageable);
// List<Object> findCurrentMonthTask(
//         @Param("month") int month,
//         @Param("year") int year
// );


//    SELECT u.username as username, sum(t.points) AS points FROM users u  INNER JOIN tasks as  t ON u.id = t.user_id WHERE (t.status = :status) AND YEAR (t.created_at) = :year  AND MONTH (t.created_at) = :month  GROUP BY u.username

   // @Query(nativeQuery = true, value = "select user.*, sum(task.point) from user join task on task.user_id = user.id GROUP BY user.id")
}
