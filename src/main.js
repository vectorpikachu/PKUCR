import './assets/main.css'

import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import router from '@/views';
import 'element-plus/dist/index.css'
import MainContainer from './components/MainContainer.vue'
// import App from './App.vue'

// const app = createApp(App)
const app = createApp(MainContainer).use(ElementPlus).use(router)

app.mount('#app')
