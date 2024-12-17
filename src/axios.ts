import axios from 'axios';

const instance = axios.create({
  baseURL: 'http://10.129.242.68', // 服务端基地址, 用https协议
});

instance.interceptors.request.use(config => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = token;
    console.log(token)
  }
  return config;
});

export default instance;
