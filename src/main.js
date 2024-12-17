import './assets/main.css'

import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import router from '@/views'
import 'element-plus/dist/index.css'
// import MainContainer from './components/MainContainer.vue'
import App from './App.vue'
import { createPinia } from 'pinia'

// const app = createApp(App)
// const app = createApp(MainContainer).use(ElementPlus).use(router)

// app.mount('#app')

const app = createApp(App)
const pinia = createPinia()
app.use(ElementPlus).use(router)
app.use(pinia)
app.mount('#app')
