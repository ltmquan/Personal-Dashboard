import taskService from '../../../services/taskService'

export default {
  name: 'TaskList',
  data() {
    return {
      tasks: [],
      newTask: {
        title: '',
        description: ''
      },
      loading: false,
      error: null
    }
  },
  mounted() {
    this.fetchTasks()
  },
  methods: {
    async fetchTasks() {
      this.loading = true
      this.error = null
      try {
        const response = await taskService.getAllTasks()
        this.tasks = response.data
      } catch (err) {
        this.error = 'Failed to load tasks. Is the backend running?'
        console.error(err)
      } finally {
        this.loading = false
      }
    },
    
    async addTask() {
      if (!this.newTask.title.trim()) return
      
      try {
        const response = await taskService.createTask({
          title: this.newTask.title,
          description: this.newTask.description
        })
        this.tasks.push(response.data)
        this.newTask.title = ''
        this.newTask.description = ''
      } catch (err) {
        this.error = 'Failed to add task'
        console.error(err)
      }
    },
    
    async toggleTask(task) {
      try {
        const response = await taskService.updateTask(task.id, {
          ...task,
          completed: !task.completed
        })
        const index = this.tasks.findIndex(t => t.id === task.id)
        this.tasks[index] = response.data
      } catch (err) {
        this.error = 'Failed to update task'
        console.error(err)
      }
    },
    
    async deleteTask(id) {
      if (!confirm('Delete this task?')) return
      
      try {
        await taskService.deleteTask(id)
        this.tasks = this.tasks.filter(t => t.id !== id)
      } catch (err) {
        this.error = 'Failed to delete task'
        console.error(err)
      }
    },
    
    formatDate(dateString) {
      return new Date(dateString).toLocaleDateString('en-US', {
        month: 'short',
        day: 'numeric',
        year: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
      })
    }
  }
}