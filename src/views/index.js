import { createRouter, createWebHashHistory } from 'vue-router'

const routes = [
  {
    path: '/login',
    component: () => import('./login.vue'),  // 登录页面不需要导航
  },
  {
    path: '/register',
    component: () => import('./register.vue'),  // 登录页面不需要导航
  },
  {
    path: '/home',
    component: () => import('./home.vue')
  },
  {
    path: '/login',
    component: () => import('./login.vue')
  },
  {
    path: '/register',
    component: () => import('./register.vue')
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
