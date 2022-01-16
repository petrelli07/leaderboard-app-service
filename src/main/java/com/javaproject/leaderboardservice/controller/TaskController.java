package com.javaproject.leaderboardservice.controller;

import com.javaproject.leaderboardservice.model.Task;
import com.javaproject.leaderboardservice.payload.response.MessageResponse;
import com.javaproject.leaderboardservice.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/task/create")
    public ResponseEntity<?> addTask(@RequestBody Task task){
        System.out.println("here");
        taskService.addTask(task);
        return ResponseEntity.ok(new MessageResponse("Task created successfully!"));
    }
}
