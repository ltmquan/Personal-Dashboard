package com.quanluu.dashboard.service;

import com.quanluu.dashboard.model.Event;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventService {
    
    List<Event> getAllEvents();
    
    Optional<Event> getEventById(Long id);
    
    Event createEvent(Event event);
    
    Event updateEvent(Long id, Event eventDetails);
    
    void deleteEvent(Long id);
    
    List<Event> getEventsBetween(LocalDateTime start, LocalDateTime end);
    
    List<Event> getUpcomingEvents();
}