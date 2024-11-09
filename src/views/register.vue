<template>
  <div class="register-page">
    <div class="logo"><img src="@/assets/PKUCR-logo.svg" alt="Logo" /></div>
    <h1>Register</h1>
    <el-form :model="form" ref="formRef" label-width="80px">
      <el-form-item label="Email" prop="email" :rules="emailRules">
        <el-input v-model="form.email" placeholder="Enter your email" />
      </el-form-item>
      <el-form-item label="Password" prop="password" :rules="passwordRules">
        <el-input v-model="form.password" type="password" placeholder="Enter your password" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleSubmit">Register</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue'
import { ElForm, ElMessage } from 'element-plus'
// import axios from 'axios'

import { useRouter } from 'vue-router';
import { useAuthStore } from '../store/auth';

const router = useRouter(); // 获取路由实例
const authStore = useAuthStore();
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

const handleSubmit = async () => {
  formRef.value?.validate(async (valid) => {
    if (valid) {
      console.log('Form submitted:', form.value)
      try {
        // POST, 提交到后端
        // TODO: 后续用户鉴权和身份校验
        // const response = await axios.post('/api/register', {
        //   email: form.value.email,
        //   password: form.value.password
        // });
        await authStore.register(form.value.email, form.value.password);
        // console.log('Register successful:', response.data);
        // 跳转到首页
        router.push('/taskTable');
      } catch {
        ElMessage.error('Register failed, try again.');
      }
    } else {
      console.log('Error in form submission')
    }
  })
}
</script>

<style scoped>
.register-page {
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
  justify-content: center; /* 居中显示 */
  margin-bottom: 20px; /* logo 和标题之间的间距 */
}

.logo img {
  height: 200px; /* 根据需要调整高度 */
  width: auto; /* 自适应宽度 */
}

h1 {
  margin-bottom: 20px;
}

.el-form {
  width: 300px;
}
</style>