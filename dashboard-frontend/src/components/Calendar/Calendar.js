import eventService from '../../services/eventService'
import taskService from '../../services/taskService'

export default {
  name: 'Calendar',
  data() {
    return {
      events: [],
      tasks: [],
      currentDate: new Date(),
      weekDays: [],
      loading: false,
      error: null
    }
  },
  created() {
    this.generateWeekDays()
    this.fetchData()
  },
  methods: {
    generateWeekDays() {
      this.weekDays = []
      const startOfWeek = this.getStartOfWeek(this.currentDate)
      
      for (let i = 0; i < 7; i++) {
        const date = new Date(startOfWeek)
        date.setDate(startOfWeek.getDate() + i)
        
        this.weekDays.push({
          date: date,
          name: date.toLocaleDateString('en-US', { weekday: 'short' })
        })
      }
    },
    
    getStartOfWeek(date) {
      const day = date.getDay()
      const diff = date.getDate() - day
      return new Date(date.setDate(diff))
    },
    
    async fetchData() {
      this.loading = true
      this.error = null
      
      try {
        const [eventsRes, tasksRes] = await Promise.all([
          eventService.getAllEvents(),
          taskService.getTasksWithDeadlines()
        ])
        
        this.events = eventsRes.data
        this.tasks = tasksRes.data
      } catch (err) {
        this.error = 'Failed to load calendar data'
        console.error(err)
      } finally {
        this.loading = false
      }
    },
    
    getEventsForDay(date) {
      return this.events.filter(event => {
        const eventDate = new Date(event.startTime)
        return this.isSameDay(eventDate, date)
      })
    },
    
    getTasksForDay(date) {
      return this.tasks.filter(task => {
        if (!task.dueDate) return false
        const taskDate = new Date(task.dueDate)
        return this.isSameDay(taskDate, date)
      })
    },
    
    isSameDay(date1, date2) {
      return date1.getFullYear() === date2.getFullYear() &&
             date1.getMonth() === date2.getMonth() &&
             date1.getDate() === date2.getDate()
    },
    
    isToday(date) {
      return this.isSameDay(date, new Date())
    },
    
    formatDate(date) {
      return date.toLocaleDateString('en-US', { month: 'short', day: 'numeric' })
    },
    
    formatTime(dateTimeStr) {
      const date = new Date(dateTimeStr)
      return date.toLocaleTimeString('en-US', { hour: 'numeric', minute: '2-digit' })
    },
    
    formatWeekRange() {
      const start = this.weekDays[0].date
      const end = this.weekDays[6].date
      
      return `${start.toLocaleDateString('en-US', { month: 'short', day: 'numeric' })} - ${end.toLocaleDateString('en-US', { month: 'short', day: 'numeric', year: 'numeric' })}`
    },
    
    previousWeek() {
      this.currentDate.setDate(this.currentDate.getDate() - 7)
      this.generateWeekDays()
      this.fetchData()
    },
    
    nextWeek() {
      this.currentDate.setDate(this.currentDate.getDate() + 7)
      this.generateWeekDays()
      this.fetchData()
    },
    
    goToToday() {
      this.currentDate = new Date()
      this.generateWeekDays()
      this.fetchData()
    },
    
    showAddEventModal() {
      alert('Add Event Modal - coming next!')
    },
    
    editEvent(event) {
      console.log('Edit event:', event)
      alert(`Edit Event: ${event.title} - coming soon!`)
    },
    
    viewTask(task) {
      console.log('View task:', task)
      alert(`Task: ${task.title}`)
    }
  }
}