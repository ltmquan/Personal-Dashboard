package com.quanluu.dashboard.controller;

import com.quanluu.dashboard.model.Task;
import com.quanluu.dashboard.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@Tag(name = "Tasks", description = "Task management API - CRUD operations for tasks with optional due dates")
public class TaskController {
    
    @Autowired
    private TaskService taskService;
    
    @GetMapping
    @Operation(summary = "Get all tasks", description = "Returns a list of all tasks")
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get task by ID", description = "Returns a single task by its ID")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    @Operation(summary = "Create new task", description = "Creates a new task and returns it with generated ID")
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update task", description = "Updates an existing task by ID")
    public Task updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
        return taskService.updateTask(id, taskDetails);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete task", description = "Deletes a task by ID")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/completed")
    @Operation(summary = "Get completed tasks", description = "Returns all tasks marked as completed")
    public List<Task> getCompletedTasks() {
        return taskService.getCompletedTasks();
    }
    
    @GetMapping("/incomplete")
    @Operation(summary = "Get incomplete tasks", description = "Returns all tasks not yet completed")
    public List<Task> getIncompleteTasks() {
        return taskService.getIncompleteTasks();
    }
    
    @GetMapping("/with-deadlines")
    @Operation(summary = "Get tasks with deadlines", description = "Returns tasks that have a due date set, ordered by due date")
    public List<Task> getTasksWithDeadlines() {
        return taskService.getTasksWithDeadlines();
    }
    
    @GetMapping("/overdue")
    @Operation(summary = "Get overdue tasks", description = "Returns incomplete tasks past their due date")
    public List<Task> getOverdueTasks() {
        return taskService.getOverdueTasks();
    }
    
    @GetMapping("/due-today")
    @Operation(summary = "Get tasks due today", description = "Returns tasks with due date set to today")
    public List<Task> getTasksDueToday() {
        return taskService.getTasksDueToday();
    }
}