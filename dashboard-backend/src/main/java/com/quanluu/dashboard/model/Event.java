package com.quanluu.dashboard.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

/**
 * Event entity representing a calendar event.
 * 
 * Events always have a start time and are displayed on the calendar.
 * They can optionally have:
 * - End time for duration
 * - Location for physical/virtual meetings
 * - Color for visual categorization
 * - Recurring pattern (e.g., WEEKLY for classes)
 * 
 * Future enhancement: Implement proper recurring event expansion
 */
@Entity
@Table(name = "events")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Event extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(length = 1000)
    private String description;
    
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;
    
    @Column(name = "end_time")
    private LocalDateTime endTime;
    
    @Column(length = 200)
    private String location;
    
    @Column(length = 7)
    private String color;
    
    @Column(name = "is_recurring")
    private boolean isRecurring = false;
    
    @Column(name = "recurrence_pattern")
    private String recurrencePattern;

}