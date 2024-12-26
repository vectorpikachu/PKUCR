import axios from '../axios'

// definitions
export interface Task {
  id: number
  priority: number
  name: string
  date: string
  time: string
  memo: string
}

export interface Course {
  id: number
  name: string
  teacher: string
  classroom: string
  time: {
    startDate: string
    endDate: string
    startTime: string
    endTime: string
    frequency: number
  }[]
  link: string
}

// variables
const RELEASE = true

const AXIOS_ADDRESS = {
  COURSE: '/course',
  TASK: '/task'
}

const sendSignal = {
  fetchCourse: async () => {
    return await axios.get(AXIOS_ADDRESS.COURSE)
  },
  fetchTask: async () => {
    return await axios.get(AXIOS_ADDRESS.TASK)
  }
}

const defaultCourse: Course[] = [
  {
    id: 0,
    name: '软件工程',
    teacher: '孙艳春',
    classroom: '二教',
    time: [
      {
        startDate: '2024-09-10',
        endDate: '2024-12-31',
        startTime: '10:10',
        endTime: '12:00',
        frequency: 7
      },
      {
        startDate: '2024-09-12',
        endDate: '2024-12-31',
        startTime: '13:00',
        endTime: '14:50',
        frequency: 7
      }
    ],
    link: 'None'
  },
  {
    id: 1,
    name: '计算机网络',
    teacher: '黄群',
    classroom: '理教',
    time: [
      {
        startDate: '2024-09-09',
        endDate: '2024-12-31',
        startTime: '13:00',
        endTime: '14:50',
        frequency: 7
      },
      {
        startDate: '2024-09-11',
        endDate: '2024-12-31',
        startTime: '08:00',
        endTime: '09:50',
        frequency: 7
      }
    ],
    link: 'None'
  }
]

const defaultTask: Task[] = [
  {
    id: 0,
    priority: 0,
    name: 'Software Engineering Class',
    date: '2024-12-17',
    time: '10:10',
    memo: '4th Presentation'
  },
  {
    id: 1,
    priority: 100,
    name: 'SE DDDDDDDDDDDDDL',
    date: '2024-12-15',
    time: '23:59',
    memo: 'help me...'
  },
  {
    id: 2,
    priority: 0,
    name: 'Software Engineering Class (test)',
    date: '2024-12-17',
    time: '10:10',
    memo: '4.1st Presentation'
  },
  {
    id: 3,
    priority: 0,
    name: 'Software Engineering Class (ttest)',
    date: '2024-12-17',
    time: '10:10',
    memo: '4.2nd Presentation'
  },
  {
    id: 4,
    priority: 0,
    name: 'Software Engineering Class (tttest)',
    date: '2024-12-17',
    time: '10:10',
    memo: '4.3rd Presentation'
  },
  {
    id: 5,
    priority: 0,
    name: 'get up',
    date: '2024-12-19',
    time: '07:30',
    memo: ''
  },
  {
    id: 6,
    priority: 0,
    name: 'breakfast',
    date: '2024-12-19',
    time: '08:30',
    memo: ''
  },
  {
    id: 7,
    priority: 0,
    name: 'sleep',
    date: '2024-12-19',
    time: '00:00',
    memo: ''
  },
  {
    id: 9,
    priority: 0,
    name: 'walk',
    date: '2024-12-19',
    time: '08:00',
    memo: ''
  },
  {
    id: 10,
    priority: 0,
    name: 'meeting',
    date: '2024-12-19',
    time: '20:30',
    memo: ''
  },
  {
    id: 11,
    priority: 0,
    name: 'lunch',
    date: '2024-12-19',
    time: '12:30',
    memo: ''
  }
]

let dataFetcher: Worker

export const storage = sessionStorage

// functions
function initializeStorage() {
  console.log('run initializeStorage')
  if (RELEASE) {
    let responseTask = sendSignal.fetchTask()
    responseTask.then((res) => {
      let taskData = JSON.parse(res.data) as Task[]
      storage.setItem('task', JSON.stringify(taskData))
    })

    let responseCourse = sendSignal.fetchCourse()
    responseCourse.then((res) => {
      let courseData = JSON.parse(res.data) as Course[]
      storage.setItem('course', JSON.stringify(courseData))
    })
  } else {
    if (storage.getItem('task') === null) {
      storage.setItem('task', JSON.stringify(defaultTask))
    }
    if (storage.getItem('course') === null) {
      storage.setItem('course', JSON.stringify(defaultCourse))
    }
  }
  console.log('finish initializeStorage')
}

function updateStorage() {
  console.log('run updateStorage')
  if (RELEASE) {
    let responseTask = sendSignal.fetchTask()
    responseTask.then((res) => {
      let taskData = JSON.parse(res.data) as Task[]
      storage.setItem('task', JSON.stringify(taskData))
    })

    let responseCourse = sendSignal.fetchCourse()
    responseCourse.then((res) => {
      let courseData = JSON.parse(res.data) as Course[]
      storage.setItem('course', JSON.stringify(courseData))
    })
  }
  console.log('finish updateStorage')
}

function clearStorage() {
  console.log('run clearStorage')
  if (RELEASE) {
    storage.removeItem('task')
    storage.removeItem('course')
  }
  console.log('finish clearStorage')
}

const createDataFetcher = () => {
  const workerCode = `
        let intervalId
        const refreshSecs = 10
        onmessage = (e) => {
            if (e.data === 'start') {
                postMessage('initialize storage')
                intervalId = setInterval(() => {
                    postMessage('update storage')
                }, refreshSecs * 1000)
            } else if (e.data === 'stop') {
                clearInterval(intervalId)
            }
        }
    `

  const blob = new Blob([workerCode], { type: 'application/javascript' })

  const workerUrl = URL.createObjectURL(blob)

  let worker = new Worker(workerUrl)

  worker.onmessage = (e) => {
    if (e.data === 'initialize storage') {
      initializeStorage()
    } else if (e.data === 'update storage') {
      updateStorage()
    }
  }

  return worker
}

export const startDataFetcher = () => {
  console.log('start dataFetcher')
  if (!dataFetcher) {
    dataFetcher = createDataFetcher()
  }
  dataFetcher.postMessage('start')
}

export const stopDataFetcher = () => {
  console.log('stop dataFetcher')
  if (dataFetcher) {
    dataFetcher.postMessage('stop')
    dataFetcher.terminate()
    dataFetcher = null
    clearStorage()
  }
}
