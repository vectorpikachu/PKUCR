import axios from "../axios"

// definitions
export interface Task {
    id: number,
    priority: number,
    name: string,
    date: string,
    time: string,
    memo: string,
}

export interface Course {
    name: string,
    teacher: string,
    classroom: string,
    time: {
        week: string,
        time: string[],
    },
    link: string,
}

export interface CourseData {
    start: string,
    data: Course[],
}

// variables
const RELEASE = false

const AXIOS_ADDRESS = {
    COURSE: '/course',
    TASK: '/task',
}

const sendSignal = {
    fetchCourse: async () => {
        return await axios.get(AXIOS_ADDRESS.COURSE)
    },
    fetchTask: async () => {
        return await axios.get(AXIOS_ADDRESS.TASK)
    }
}

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
        name: 'Software Engineering Class (test)',
        date: '2024-12-17',
        time: '10:10:01',
        memo: '4.1st Presentation'
    },
    {
        id: 3,
        priority: 0,
        name: 'Software Engineering Class (ttest)',
        date: '2024-12-17',
        time: '10:10:02',
        memo: '4.2nd Presentation'
    },
    {
        id: 0,
        priority: 0,
        name: 'Software Engineering Class (tttest)',
        date: '2024-12-17',
        time: '10:10:03',
        memo: '4.3rd Presentation'
    }
]

let dataFetcher: Worker

export const weekZh2Num = {
    '星期一': 0,
    '星期二': 1,
    '星期三': 2,
    '星期四': 3,
    '星期五': 4,
    '星期六': 5,
    '星期日': 6,
}
export const timeStartZh2Num = {
    '第一节': '8:00',
    '第二节': '9:00',
    '第三节': '10:10',
    '第四节': '11:10',
    '第五节': '13:00',
    '第六节': '14:00',
    '第七节': '15:10',
    '第八节': '16:10',
    '第九节': '17:10',
    '第十节': '18:40',
    '第十一节': '19:40',
    '第十二节': '20:40',
}
export const timeEndZh2Num = {
    '第一节': '8:50',
    '第二节': '9:50',
    '第三节': '11:00',
    '第四节': '12:00',
    '第五节': '13:50',
    '第六节': '14:50',
    '第七节': '16:00',
    '第八节': '17:00',
    '第九节': '18:00',
    '第十节': '19:30',
    '第十一节': '20:30',
    '第十二节': '21:30',
}

export const storage = localStorage

// functions
function initializeStorage() {
    if (RELEASE) {
        let responseTask = sendSignal.fetchTask()
        responseTask.then((res) => {
            let taskData = JSON.parse(res.data) as Task[]
            storage.setItem('task', JSON.stringify(taskData))
        })

        let responseCourse = sendSignal.fetchCourse()
        responseCourse.then((res) => {
            let courseData = JSON.parse(res.data) as CourseData
            storage.setItem('course', JSON.stringify(courseData))
        })
    }
    else {
        storage.setItem('task', JSON.stringify(defaultTask))
        storage.setItem('course', JSON.stringify(defaultCourse))
    }
}

function clearStorage() {
    if (RELEASE) {
        storage.removeItem('task')
        storage.removeItem('course')
    }
}

const createDataFetcher = () => {
    const workerCode = `
        let intervalId
        const refreshSecs = 60
        onmessage = (e) => {
            if (e.data === 'start') {
                postMessage('initialize storage')
                intervalId = setInterval(() => {
                    postMessage('initialize storage')
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
            console.log('run initializeStorage')
            initializeStorage()
            console.log('finish initializeStorage')
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