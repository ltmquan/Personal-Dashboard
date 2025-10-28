package com.quanluu.dashboard.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

/**
 * Task entity representing a todo item.
 * 
 * Tasks can optionally have a due date and are displayed in both
 * the task list and calendar views. Tasks without due dates appear
 * only in the task list.
 * 
 * Fields:
 * - title: Required task name
 * - description: Optional details
 * - completed: Boolean flag for completion status
 * - dueDate: Optional deadline for the task
 */
@Entity
@Table(name = "tasks")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Task extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(length = 1000)
    private String description;
    
    @Column(nullable = false)
    private boolean completed = false;
    
    @Column(name = "due_date")
    private LocalDateTime dueDate;

}