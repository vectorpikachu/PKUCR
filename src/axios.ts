import axios from 'axios'
import { storage } from './store/storage'

const instance = axios.create({
	baseURL: 'http://localhost:38083', // 服务端基地址, 用https协议
})

instance.interceptors.request.use(config => {
	const token = storage.getItem('token')
	if (token) {
		config.headers.Authorization = token
		console.log(token)
	}
	return config
})

export default instance
