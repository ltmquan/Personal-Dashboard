package com.quanluu.dashboard.repository;

import com.quanluu.dashboard.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    
    List<Event> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);
    
    List<Event> findByStartTimeAfterOrderByStartTimeAsc(LocalDateTime now);
    
    List<Event> findByLocationContaining(String location);
}