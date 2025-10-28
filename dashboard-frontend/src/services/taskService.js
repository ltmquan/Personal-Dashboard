/**
 * Task Service
 * 
 * Handles all API communication for task management.
 * Centralizes HTTP requests to avoid duplication across components.
 * 
 * Backend API: http://localhost:8080/api/tasks
 * 
 * Available methods:
 * - getAllTasks() - Fetch all tasks
 * - getTaskById(id) - Fetch single task
 * - createTask(task) - Create new task
 * - updateTask(id, task) - Update existing task
 * - deleteTask(id) - Delete task
 * - getTasksWithDeadlines() - Get tasks with due dates (for calendar)
 * - getOverdueTasks() - Get incomplete tasks past due date
 * - getTasksDueToday() - Get tasks due today
 * 
 * Usage:
 * import taskService from '../../services/taskService'
 * const response = await taskService.getAllTasks()
 * const tasks = response.data
 */
import axios from 'axios'

const API_URL = 'http://localhost:8080/api/tasks'

export default {
  getAllTasks() {
    return axios.get(API_URL)
  },

  getTaskById(id) {
    return axios.get(`${API_URL}/${id}`)
  },

  createTask(task) {
    return axios.post(API_URL, task)
  },

  updateTask(id, task) {
    return axios.put(`${API_URL}/${id}`, task)
  },

  deleteTask(id) {
    return axios.delete(`${API_URL}/${id}`)
  },

  getCompletedTasks() {
    return axios.get(`${API_URL}/completed`)
  },

  getIncompleteTasks() {
    return axios.get(`${API_URL}/incomplete`)
  },

  getTasksWithDeadlines() {
    return axios.get(`${API_URL}/with-deadlines`)
  },

  getOverdueTasks() {
    return axios.get(`${API_URL}/overdue`)
  },

  getTasksDueToday() {
    return axios.get(`${API_URL}/due-today`)
  }
}