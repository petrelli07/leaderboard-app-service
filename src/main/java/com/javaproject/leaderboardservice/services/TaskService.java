package com.javaproject.leaderboardservice.services;

import com.javaproject.leaderboardservice.exception.ResourceNotFoundException;
import com.javaproject.leaderboardservice.model.Task;
import com.javaproject.leaderboardservice.model.User;
import com.javaproject.leaderboardservice.payload.request.TaskRequest;
import com.javaproject.leaderboardservice.repositories.TaskRepository;
import com.javaproject.leaderboardservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    public Task addTask(long userId, String description, String title){
            Task task = new Task();
            task.setDescription(description);
            task.setTitle(title);
            task.setUserById(userId);
            return taskRepository.save(task);
    }

    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    public Task getTask(long task_id){
        Optional<Task> optionalTask =  taskRepository.findById(task_id);
            return optionalTask.get();
    }

    public Task updateTask(long taskId, String taskDescription){
        Task taskToUpdate = taskRepository.getById(taskId);
        taskToUpdate.setDescription(taskDescription);
        return taskRepository.save(taskToUpdate);
    }

    public Task updateTaskPoint(long taskId, int taskPoint){
        Task taskToUpdate = taskRepository.getById(taskId);
        taskToUpdate.setPoint(taskPoint);
        return taskRepository.save(taskToUpdate);
    }

    public List<Object> getAllTimeTasks(){
        List <Object> task = taskRepository.find();
        return task;
    }

//    public List<Task> getTaskForCurrentMonth(){
//        return taskRepository.getTaskForCurrentMonth();
//    }

}
