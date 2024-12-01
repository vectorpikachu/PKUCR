import { defineStore } from 'pinia';
import { ref, computed } from 'vue';
import axios from '../axios'

export const useAuthStore = defineStore('auth', () => {
  const token = ref<string | null>(localStorage.getItem('token'));
  const username = ref<string | null>(localStorage.getItem('username'));

  const isAuthenticated = computed(() => !!token.value);

  const login = async (email: string, password: string) => {
    try {
      // 此处是后端api
      const response = await axios.post('/api/auth/login', { email, password });
      token.value = response.data.token;
      username.value = response.data.username;  // 获取并保存用户名
      localStorage.setItem('token', token.value || '');
      localStorage.setItem('username', username.value || '');
    } catch (error) {
      throw new Error(`Login failed! ${error}`);
    }
  };

  const register = async (email: string, password: string, name: string) => {
    try {
      // 此处是后端api
      const response = await axios.post('/api/auth/register', { email, password, name});
      token.value = response.data.token;
      username.value = response.data.username;  // 获取并保存用户名
      localStorage.setItem('token', token.value || '');
      localStorage.setItem('username', username.value || '');
    } catch (error) {
      throw new Error(`Register failed! ${error}`);
    }
  };

  const logout = () => {
    token.value = null;
    username.value = null;
    localStorage.removeItem('token');
    localStorage.removeItem('username');
  };

  return {
    token,
    isAuthenticated,
    login,
    register,
    logout
  };
});
