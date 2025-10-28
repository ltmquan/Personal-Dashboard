package com.quanluu.dashboard.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quanluu.dashboard.model.Event;
import com.quanluu.dashboard.service.EventService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/events")
@Tag(name = "Events", description = "Calendar event management API - Create and manage calendar events with optional recurring patterns")
public class EventController {
    
    @Autowired
    private EventService eventService;
    
    @GetMapping
    @Operation(summary = "Get all tasks", description = "Returns a list of all events")
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get event by ID", description = "Returns a single event by its ID")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        return eventService.getEventById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    @Operation(summary = "Create new event", description = "Creates a new event and returns it with generated ID")
    public Event createEvent(@RequestBody Event event) {
        return eventService.createEvent(event);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update event", description = "Updates an existing event by ID")
    public Event updateEvent(@PathVariable Long id, @RequestBody Event eventDetails) {
        return eventService.updateEvent(id, eventDetails);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete event", description = "Deletes a event by ID")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/upcoming")
    @Operation(summary = "Get upcoming events", description = "Returns a list of events in the future")
    public List<Event> getUpcomingEvents() {
        return eventService.getUpcomingEvents();
    }
    
    @GetMapping("/range")
    @Operation(summary = "Get events between a range", description = "Returns a list of events within a date range")
    public List<Event> getEventsBetween(
            @RequestParam String start,
            @RequestParam String end) {
        LocalDateTime startTime = LocalDateTime.parse(start);
        LocalDateTime endTime = LocalDateTime.parse(end);
        return eventService.getEventsBetween(startTime, endTime);
    }
}