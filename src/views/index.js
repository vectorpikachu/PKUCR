import { createRouter, createWebHashHistory } from 'vue-router'

const routes = [
  {
    path: '/home',
    component: () => import('./home.vue')
  },
  {
    path: '/setting',
    component: () => import('./setting.vue')
  },
  {
    path: '/taskTable',
    component: () => import('./taskTable.vue')
  },
  // Add other routes here
  {
    path: '/',
    redirect: '/home'
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
