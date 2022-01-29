package com.javaproject.leaderboardservice.controller;

import com.javaproject.leaderboardservice.model.Task;
import com.javaproject.leaderboardservice.payload.request.TaskRequest;
import com.javaproject.leaderboardservice.payload.response.MessageResponse;
import com.javaproject.leaderboardservice.repositories.TaskRepository;
import com.javaproject.leaderboardservice.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskRepository taskRepository;

    @PostMapping("/task/create")
    public ResponseEntity<?> addTask(@RequestBody TaskRequest taskRequest){
        long user_id = taskRequest.getUser_id();
        String description = taskRequest.getDescription();
        String title = taskRequest.getTitle();
        Task taskDetails = taskService.addTask(user_id, description, title);
        return ResponseEntity.ok(taskDetails);
    }

    @PutMapping("/task/point/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity updateTaskPoint(@RequestBody TaskRequest taskRequest){
        long task_id = taskRequest.getTask_id();

        if (taskRepository.findById(task_id).isEmpty()){
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Task Id does not exist!"));
        }

        int point = taskRequest.getPoint();
        Task taskDetails = taskService.updateTaskPoint(task_id, point);
        return ResponseEntity.ok(taskDetails);
    }

    @GetMapping("/tasks/all")
    public ResponseEntity<?> getAllTasks(){
        return ResponseEntity.ok(taskService.getAllTimeTasks());
    }

    @GetMapping("/tasks/all/current-month")
    public ResponseEntity<?> getCurrentMonthTasks(){
        return ResponseEntity.ok(taskService.getTaskForCurrentMonth());
    }

    @GetMapping(value = "/tasks/user/all/{userId}")
    public ResponseEntity<?> getAllUserTask(@PathVariable("userId") long userId){
        return ResponseEntity.ok(taskService.getAllUserTasks(userId));
    }

    @GetMapping(value = "/tasks/user/current-month/{userId}")
    public ResponseEntity<?> getUserCurrentMonthTask(@PathVariable("userId") long userId){
        return ResponseEntity.ok(taskService.getUserTaskForCurrentMonth(userId));
    }

    @GetMapping(value = "/tasks/{taskId}")
    public ResponseEntity<?> getTaskById(@PathVariable("taskId") long taskId){
        return ResponseEntity.ok(taskService.getTaskById(taskId));
    }


}
