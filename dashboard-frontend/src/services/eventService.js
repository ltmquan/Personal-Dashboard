/**
 * Event Service
 * 
 * Handles all API communication for calendar events.
 * 
 * Backend API: http://localhost:8080/api/events
 * 
 * Available methods:
 * - getAllEvents() - Fetch all events
 * - getEventById(id) - Fetch single event
 * - createEvent(event) - Create new event
 * - updateEvent(id, event) - Update existing event
 * - deleteEvent(id) - Delete event
 * - getUpcomingEvents() - Get future events ordered by start time
 * - getEventsBetween(start, end) - Get events in date range
 * 
 * Event Object Structure:
 * {
 *   title: string (required)
 *   description: string
 *   startTime: ISO datetime string (required)
 *   endTime: ISO datetime string
 *   location: string
 *   color: hex color string (#RRGGBB)
 *   isRecurring: boolean
 *   recurrencePattern: string (DAILY, WEEKLY, etc.)
 * }
 */
import axios from 'axios'

const API_URL = 'http://localhost:8080/api/events'

export default {
  getAllEvents() {
    return axios.get(API_URL)
  },

  getEventById(id) {
    return axios.get(`${API_URL}/${id}`)
  },

  createEvent(event) {
    return axios.post(API_URL, event)
  },

  updateEvent(id, event) {
    return axios.put(`${API_URL}/${id}`, event)
  },

  deleteEvent(id) {
    return axios.delete(`${API_URL}/${id}`)
  },

  getUpcomingEvents() {
    return axios.get(`${API_URL}/upcoming`)
  },

  getEventsBetween(start, end) {
    return axios.get(`${API_URL}/range`, {
      params: { start, end }
    })
  }
}