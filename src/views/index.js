import { createRouter, createWebHashHistory } from 'vue-router'
import MainContainer from '../components/MainContainer.vue'

const routes = [
  {
    path: '/login',
    component: () => import('./login.vue')
  },
  {
    path: '/register',
    component: () => import('./register.vue')
  },
  {
    path: '/',
    component: MainContainer, // MainContainer 是父组件
    children: [
      {
        path: 'schedule',
        component: () => import('./schedule.vue')
      },
      {
        path: 'setting',
        component: () => import('./setting.vue')
      },
      {
        path: 'taskTable',
        component: () => import('./taskTable.vue')
      },
      {
        path: 'calendar',
        component: () => import('./calendar.vue')
      }
    ]
  },
  {
    path: '/',
    redirect: '/schedule'
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
