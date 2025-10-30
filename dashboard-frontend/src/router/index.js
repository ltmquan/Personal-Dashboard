import { createRouter, createWebHistory } from 'vue-router'
import DashboardView from '../views/DashboardView/DashboardView.vue'
import LibraryView from '../views/LibraryView/LibraryView.vue'

const routes = [
  {
    path: '/',
    name: 'Dashboard',
    component: DashboardView
  },
  {
    path: '/library',
    name: 'Library',
    component: LibraryView
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
