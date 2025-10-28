package com.quanluu.dashboard.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quanluu.dashboard.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    
    List<Task> findByCompleted(boolean completed);
    
    List<Task> findByTitleContaining(String title);
    
    List<Task> findByDueDateBetween(LocalDateTime start, LocalDateTime end);
    
    List<Task> findByDueDateIsNotNullOrderByDueDateAsc();
    
    List<Task> findByDueDateIsNull();
    
    List<Task> findByDueDateBeforeAndCompletedFalse(LocalDateTime date);
}
