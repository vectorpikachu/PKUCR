<template>
	<div class="login-page">
		<div class="logo"><img src="@/assets/PKUCR-logo.svg" alt="Logo" /></div>
		<h1>Login</h1>
		<el-form :model="form" ref="formRef" label-width="80px">
			<el-form-item label="Email" prop="email" :rules="emailRules">
				<el-input v-model="form.email" placeholder="Enter your email" />
			</el-form-item>
			<el-form-item label="Password" prop="password" :rules="passwordRules">
				<el-input v-model="form.password" type="password" placeholder="Enter your password" />
			</el-form-item>
			<el-form-item>
				<el-button type="primary" @click="handleSubmit">登录</el-button>
				<el-button type="warning" @click="handleExit" class="logout-button">退出</el-button>
			</el-form-item>
		</el-form>
	</div>
</template>

<script lang="ts" setup>
import { ref } from 'vue'
import { ElForm, ElMessage } from 'element-plus'
// import axios from 'axios'

import { useRouter } from 'vue-router'
import { useAuthStore } from '../store/auth'

const router = useRouter() // 获取路由实例
const authStore = useAuthStore()
const formRef = ref<InstanceType<typeof ElForm>>(null)

const form = ref({
	email: '',
	password: ''
})

const emailSuffix = 'pku.edu.cn'
const emailRules = [
	{ required: true, message: 'Please input your email', trigger: 'blur' },
	{ type: 'email', message: 'Please input a valid email', trigger: 'blur' },
	{
		validator: (rule, value, callback) => {
			if (value && !value.endsWith(emailSuffix)) {
				callback(new Error(`Only for pku students now`))
			} else {
				callback()
			}
		},
		trigger: 'blur'
	}
]

const passwordRules = [
	{ required: true, message: 'Please input your password', trigger: 'blur' },
	{ min: 6, message: 'Password length should be at least 6', trigger: 'blur' }
]

const handleSubmit = () => {
	formRef.value?.validate(async (valid) => {
		if (valid) {
			console.log('Form submitted:', form.value)
			try {
				// POST, 提交到后端
				// TODO: 后续用户鉴权和身份校验
				// const response = await axios.post('/api/login', {
				//   email: form.value.email,
				//   password: form.value.password
				// })

				await authStore.login(form.value.email, form.value.password)
				// console.log('Login successful:', response.data)
				// 跳转到首页
				router.push('/taskTable')
			} catch {
				form.value.password = ''
				ElMessage.error('Login failed, please check your email or password.')
			}
		} else {
			console.error('Error in form submission')
		}
	})
}

const handleExit = () => {
	router.push('/')
}
</script>

<style scoped>
.login-page {
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
	height: 85vh;
	width: 85vw;
	/* background-color: #f5f5f5; */
}

.logo {
	display: flex;
	justify-content: center;
	/* 居中显示 */
	margin-bottom: 20px;
	/* logo 和标题之间的间距 */
}

.logo img {
	height: 200px;
	/* 根据需要调整高度 */
	width: auto;
	/* 自适应宽度 */
}

h1 {
	margin-bottom: 20px;
}

.el-form {
	width: 300px;
}
</style>