<!--
  Calendar Component
  
  Weekly calendar view displaying events and tasks with due dates.
  
  Features:
  - 7-day week view (Sunday-Saturday)
  - Navigate between weeks (previous/next/today buttons)
  - Displays events as colored time blocks
  - Displays tasks with due dates as badges
  - Highlights current day with green border
  - Click items to view/edit (coming soon)
  
  Display Logic:
  - Events: Full blocks with time, title, location
  - Tasks: Small indicators with title only
  - Today: Green border highlight
  
  Future enhancements:
  - Month view toggle
  - Add event modal
  - Drag-and-drop to reschedule
  - Recurring event expansion
  - Filter by event type/color
  
  Dependencies:
  - eventService.js and taskService.js for API calls
-->
<template>
  <div class="calendar-container">
    <h2>Calendar</h2>
    
    <div class="calendar-header">
      <button @click="previousWeek">◀</button>
      <h3>{{ formatWeekRange() }}</h3>
      <button @click="nextWeek">▶</button>
      <button @click="goToToday" class="today-btn">Today</button>
    </div>
    
    <div v-if="loading" class="loading">Loading...</div>
    <div v-if="error" class="error">{{ error }}</div>
    
    <div class="calendar-grid">
      <div 
        v-for="day in weekDays" 
        :key="day.date"
        class="day-column"
        :class="{ today: isToday(day.date) }"
      >
        <div class="day-header">
          <div class="day-name">{{ day.name }}</div>
          <div class="day-date">{{ formatDate(day.date) }}</div>
        </div>
        
        <div class="day-content">
          <!-- Events for this day -->
          <div 
            v-for="event in getEventsForDay(day.date)" 
            :key="'event-' + event.id"
            class="calendar-item event-item"
            :style="{ backgroundColor: event.color || '#4CAF50' }"
            @click="editEvent(event)"
          >
            <div class="item-time">{{ formatTime(event.startTime) }}</div>
            <div class="item-title">{{ event.title }}</div>
            <div v-if="event.location" class="item-location">📍 {{ event.location }}</div>
          </div>
          
          <!-- Tasks with due dates for this day -->
          <div 
            v-for="task in getTasksForDay(day.date)" 
            :key="'task-' + task.id"
            class="calendar-item task-item"
            @click="viewTask(task)"
          >
            <div class="item-title">✓ {{ task.title }}</div>
          </div>
          
          <div v-if="getEventsForDay(day.date).length === 0 && getTasksForDay(day.date).length === 0" 
               class="no-items">
            No events or tasks
          </div>
        </div>
      </div>
    </div>
    
    <button @click="showAddEventModal" class="add-event-btn">+ Add Event</button>
  </div>
</template>

<script src="./Calendar.js"></script>
<style src="./Calendar.css" scoped></style>