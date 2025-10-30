import TaskList from '../../components/dashboard/TaskList/TaskList.vue'
import Weather from '../../components/dashboard/Weather/Weather.vue'
import Calendar from '../../components/dashboard/Calendar/Calendar.vue'

export default {
  name: 'DashboardView',
  components: {
    Weather,
    Calendar,
    TaskList
  }
}