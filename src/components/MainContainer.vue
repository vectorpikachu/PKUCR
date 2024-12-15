<script lang="ts" setup>
import SideBar from './SideBar.vue'
import PageHeader from './PageHeader.vue'
import { onUnmounted } from 'vue'
import axios from '../axios'

let headerHeightVh = 10
let menuWidthVw = 15
let menuHeightVh = 100 - headerHeightVh
let mainWidthVw = 100 - menuWidthVw

const RELEASE = false

interface Task {
  id: number,
  priority: number,
  name: string,
  date: string,
  time: string,
  memo: string,
}

interface Course {
  name: string,
  teacher: string,
  classroom: string,
  time: {
    week: string,
    time: string[],
  },
  link: string,
}

interface TaskData {
  data: Task[],
}

interface CourseData {
  start: string,
  data: Course[],
}

const AXIOS_ADDRESS = {
  COURSE: '/course/get',
  TASK: '/task/get',
}
const workerCode = `
  let intervalId
  const refreshSecs = 60
  onmessage = function (e) {
    if (e.data === 'start') {
      postMessage('initialize data')
    }
  }
`

const defaultCourse: CourseData = {
  start: '2024-09-09',
  data: [
    {
      name: '软件工程',
      teacher: '孙艳春',
      classroom: '二教',
      time:
      {
        week: '1-16',
        time: ['星期二(第三节-第四节)', '星期四(第五节-第六节)']
      },
      link: 'None'
    },
    {
      name: '计算机网络',
      teacher: '黄群',
      classroom: '理教',
      time:
      {
        week: '1-16',
        time: ['星期一(第五节-第六节)', '星期三(第一节-第二节)']
      },
      link: 'None'
    },
    {
      name: '自然语言处理',
      teacher: '孙栩',
      classroom: '理教',
      time:
      {
        week: '1-16',
        time: ['星期二(第五节-第六节)', '星期四(第三节-第四节)']
      },
      link: 'None'
    }
  ]
}

const defaultTask: Task[] = [
  {
    id: 0,
    priority: 0,
    name: 'Software Engineering Class',
    date: '2024-12-17',
    time: '10:10:00',
    memo: '4th Presentation'
  }, 
  {
    id: 1,
    priority: 100,
    name: 'SE DDDDDDDDDDDDDL',
    date: '2024-12-15',
    time: '23:59:59',
    memo: 'help me...'
  },
  {
    id: 2,
    priority: 0,
    name: 'Software Engineering (test)',
    date: '2024-12-17',
    time: '10:10:01',
    memo: '4.1st Presentation'
  }, 
  {
    id: 3,
    priority: 0,
    name: 'Software Class (ttest)',
    date: '2024-12-17',
    time: '10:10:02',
    memo: '4.2nd Presentation'
  },
  {
    id: 0,
    priority: 0,
    name: 'Engineering Class (tttest)',
    date: '2024-12-17',
    time: '10:10:03',
    memo: '4.3rd Presentation'
  }
]

const blob = new Blob([workerCode], { type: 'application/javascript' })
const workerUrl = URL.createObjectURL(blob)

let dataFetcher = new Worker(workerUrl)

const sendSignal = {
  fetchCourse: async () => {
    return await axios.get(AXIOS_ADDRESS.COURSE)
  },
  fetchTask: async () => {
    return await axios.get(AXIOS_ADDRESS.TASK)
  }
}

function initializeStorage() {
  if (RELEASE) {
    let responseTask = sendSignal.fetchTask()
    responseTask.then((res) => {
      let taskData = JSON.parse(res.data) as TaskData
      localStorage.setItem('task', JSON.stringify(taskData.data))
    })

    let responseCourse = sendSignal.fetchCourse()
    responseCourse.then((res) => {
      let courseData = JSON.parse(res.data) as CourseData
      localStorage.setItem('course', JSON.stringify(courseData))
    })
  }
  else {
    localStorage.setItem('task', JSON.stringify(defaultTask))
    localStorage.setItem('course', JSON.stringify(defaultCourse))
  }
}

dataFetcher.onmessage = (e) => {
  if (e.data === 'initialize data') {
    console.log('run initializeStorage')
    initializeStorage()
    console.log('finish initializeStorage')
  }
}

dataFetcher.postMessage('start')

onUnmounted(() => {
  if (dataFetcher) {
    dataFetcher.terminate()
    dataFetcher = null
  }
})

</script>

<template>
  <div id="main_container">
    <el-container :style="{ width: `100vw`, height: `${headerHeightVh}vh` }">
      <el-header style="width: 100%; height: 100%;" class="background">
        <PageHeader />
      </el-header>
    </el-container>
    <el-container :style="{ width: `100vw`, height: `${menuHeightVh}vh` }">
      <el-aside :style="{ width: `${menuWidthVw}vw`, height: `100%` }" class="background">
        <SideBar />
      </el-aside>
      <el-main :style="{ width: `${mainWidthVw}vw`, height: `100%` }" class="background">
        <router-view></router-view>
      </el-main>
    </el-container>
  </div>
</template>

<style>
#main_container {
  font-family: Arial, Helvetica, sans-serif;
  font-size: 14px;
  color: #2c3e50;
  text-align: center;
  position: absolute;
  left: 0;
  top: 0;
}

.background {
  background-color: white;
  position: relative;
  /* border: 0.5px solid black; */
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}
</style>
