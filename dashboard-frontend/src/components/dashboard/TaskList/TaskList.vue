<!--
  TaskList Component
  
  Manages user tasks with full CRUD operations.
  
  Features:
  - Add new tasks with title and optional description
  - Mark tasks as complete/incomplete via checkbox
  - Delete tasks with confirmation
  - Real-time sync with backend API
  
  Future enhancements:
  - Add due date picker
  - Filter by completion status
  - Search/sort functionality
  - Drag-and-drop reordering
  
  Dependencies:
  - taskService.js for API calls
  - Backend must be running on localhost:8080
-->
<template>
  <div class="task-list">
    <h2>My Tasks</h2>
    
    <div class="add-task">
      <input 
        v-model="newTask.title" 
        placeholder="Task title"
        @keyup.enter="addTask"
      />
      <input 
        v-model="newTask.description" 
        placeholder="Description (optional)"
        @keyup.enter="addTask"
      />
      <button @click="addTask">Add Task</button>
    </div>
    
    <div v-if="loading" class="loading">Loading tasks...</div>
    <div v-if="error" class="error">{{ error }}</div>
    
    <div v-if="!loading && tasks.length === 0" class="empty">
      No tasks yet. Add one above!
    </div>
    
    <div v-else class="tasks">
      <div 
        v-for="task in tasks" 
        :key="task.id" 
        class="task-item"
        :class="{ completed: task.completed }"
      >
        <input 
          type="checkbox" 
          :checked="task.completed"
          @change="toggleTask(task)"
        />
        <div class="task-content">
          <h3>{{ task.title }}</h3>
          <p v-if="task.description">{{ task.description }}</p>
          <small>Created: {{ formatDate(task.createdAt) }}</small>
        </div>
        <button @click="deleteTask(task.id)" class="delete-btn">🗑️</button>
      </div>
    </div>
  </div>
</template>

<script src="./TaskList.js"></script>
<style src="./TaskList.css" scoped></style>