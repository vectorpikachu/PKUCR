<template>
    <div class="header-container">
        <!-- <div class="logo"><img :src="logoUrl" alt="Logo" /></div> -->
        <div class="blank"></div>
        <div class="input">
            <el-autocomplete v-model="state" :fetch-suggestions="querySearchAsync" placeholder="Search for resources"
                @select="handleSelect" style="width: 30vw;"/>
        </div>
        <div class="button-group">
            <el-button v-if="!authStore.isAuthenticated" type="primary" @click="goToLogin">登录</el-button>
            <el-button v-if="!authStore.isAuthenticated" type="success" @click="goToRegister">注册</el-button>
            <el-button v-if="authStore.isAuthenticated" type="danger" @click="goToLogout">登出</el-button>
        </div>
    </div>
</template>

<script lang="ts" setup>
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/store/auth'
//import {logo} from '../assets/PKUCR-logo2.svg'

//const logoUrl = logo
const router = useRouter()
const authStore = useAuthStore()

const state = ref('')

interface LinkItem {
    value: string
    link: string
}

const links = ref<LinkItem[]>([])

const loadAll = () => {
    return [
        { value: 'vue', link: 'https://github.com/vuejs/vue' },
        { value: 'element', link: 'https://github.com/ElemeFE/element' },
        { value: 'cooking', link: 'https://github.com/ElemeFE/cooking' },
        { value: 'mint-ui', link: 'https://github.com/ElemeFE/mint-ui' },
        { value: 'vuex', link: 'https://github.com/vuejs/vuex' },
        { value: 'vue-router', link: 'https://github.com/vuejs/vue-router' },
        { value: 'babel', link: 'https://github.com/babel/babel' },
    ]
}

let timeout: ReturnType<typeof setTimeout>
const querySearchAsync = (queryString: string, cb: (arg: any) => void) => {
    const results = queryString
        ? links.value.filter(createFilter(queryString))
        : links.value

    clearTimeout(timeout)
    timeout = setTimeout(() => {
        cb(results)
    }, 3000 * Math.random())
}
const createFilter = (queryString: string) => {
    return (restaurant: LinkItem) => {
        return (
            restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0
        )
    }
}

const handleSelect = (item: Record<string, any>) => {
    console.log(item)
}

onMounted(() => {
    links.value = loadAll()
})

const goToLogin = () => {
    router.push('/login') // 导航到登录页面
}

const goToRegister = () => {
    router.push('/register') // 导航到注册页面
}

const goToLogout = () => {
    authStore.logout()
    router.push('/taskTable')
}

</script>

<style scoped>
.header-container {
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 100%;
    height: 100%;
}

.button-group {
    display: flex;
    gap: 10px;
}

.logo img {
  height: 63px;
  width: auto;
}
</style>