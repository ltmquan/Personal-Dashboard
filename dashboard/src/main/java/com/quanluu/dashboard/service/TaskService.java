package com.quanluu.dashboard.service;

import com.quanluu.dashboard.model.Task;
import java.util.List;
import java.util.Optional;

public interface TaskService {
    
    List<Task> getAllTasks();
    
    Optional<Task> getTaskById(Long id);
    
    Task createTask(Task task);
    
    Task updateTask(Long id, Task taskDetails);
    
    void deleteTask(Long id);
    
    List<Task> getCompletedTasks();
    
    List<Task> getIncompleteTasks();
    
    List<Task> getTasksWithDeadlines();
    
    List<Task> getOverdueTasks();
    
    List<Task> getTasksDueToday();
}