package com.quanluu.dashboard.service.impl;

import com.quanluu.dashboard.constants.EntityType;
import com.quanluu.dashboard.constants.ErrorCode;
import com.quanluu.dashboard.exception.ResourceNotFoundException;
import com.quanluu.dashboard.model.Event;
import com.quanluu.dashboard.repository.EventRepository;
import com.quanluu.dashboard.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {
    
    @Autowired
    private EventRepository eventRepository;
    
    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }
    
    @Override
    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }
    
    @Override
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }
    
    @Override
    public Event updateEvent(Long id, Event eventDetails) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id, EntityType.EVENT, ErrorCode.NOT_FOUND_WITH_ID));
        
        event.setTitle(eventDetails.getTitle());
        event.setDescription(eventDetails.getDescription());
        event.setStartTime(eventDetails.getStartTime());
        event.setEndTime(eventDetails.getEndTime());
        event.setLocation(eventDetails.getLocation());
        event.setColor(eventDetails.getColor());
        event.setRecurring(eventDetails.isRecurring());
        event.setRecurrencePattern(eventDetails.getRecurrencePattern());
        
        return eventRepository.save(event);
    }
    
    @Override
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
    
    @Override
    public List<Event> getEventsBetween(LocalDateTime start, LocalDateTime end) {
        return eventRepository.findByStartTimeBetween(start, end);
    }
    
    @Override
    public List<Event> getUpcomingEvents() {
        return eventRepository.findByStartTimeAfterOrderByStartTimeAsc(LocalDateTime.now());
    }
}