package com.javaproject.leaderboardservice.services;

import com.javaproject.leaderboardservice.model.Task;
import com.javaproject.leaderboardservice.repositories.TaskRepository;
import com.javaproject.leaderboardservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

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

    public int getCurrentYear(){
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year  = localDate.getYear();
        return year;
    }

    public int getCurrentMonth(){
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = localDate.getMonthValue();
        return month;
    }


    public List<Object> getTaskForCurrentMonth(){
        List <Object> task = taskRepository.findCurrentMonthTask(this.getCurrentMonth(), this.getCurrentYear());
        return task;
    }

    public Optional<Object> getAllUserTasks(long userId){
        List <Object> task = taskRepository.findAllByUserId(userId);
        return Optional.ofNullable(task);
    }

    public Optional<Object> getUserTaskForCurrentMonth(long userId){
        List<Object> task = taskRepository.findUserCurrentMonthTask(this.getCurrentMonth(), this.getCurrentYear(), userId);
        return Optional.ofNullable(task);
    }


    public Optional<Object> getTaskById(long taskId) {
        Optional<Object> task = Optional.of(taskRepository.findById(taskId));
        return task;
    }
}
