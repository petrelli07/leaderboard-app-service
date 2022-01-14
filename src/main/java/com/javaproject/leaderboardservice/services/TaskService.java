package com.javaproject.leaderboardservice.services;

import com.javaproject.leaderboardservice.exception.ResourceNotFoundException;
import com.javaproject.leaderboardservice.model.Task;
import com.javaproject.leaderboardservice.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public Task addTask(Task task){
        return taskRepository.save(task);
    }

    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    public Task getTask(long task_id){
        Optional<Task> optionalTask =  taskRepository.findById(task_id);
            return optionalTask.get();
    }

//    public List<Task> getTaskForCurrentMonth(){
//        return taskRepository.getTaskForCurrentMonth();
//    }

}
