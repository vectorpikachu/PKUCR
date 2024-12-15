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
    path: '/resource',
    component: () => import('./resource.vue')
  },
  {
    path: '/',
    component: MainContainer, // MainContainer 是父组件
    redirect: '/home',
    children: [
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
      {
        path: '/calendar',
        component: () => import('./calendar.vue')
      }
    ]
  },
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
