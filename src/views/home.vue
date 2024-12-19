<template>
    <!-- Title -->
    <el-text class="dynamicGradient">Welcome to PKUCR, your Course & Resource helper!</el-text>

    <!-- Progress -->
    <el-card class="homeSection" :body-style="{ display: 'flex' }">
        <div class="progressLeft">
            <el-text class="progressOverTitle">[ Today Finished ]</el-text>
            <el-scrollbar style="margin-left: 15%; max-height: 80%;">
                <div v-for="schedule in schedulesData[today.format('YYYY-MM-DD')]">
                    <el-text v-if="dayjs(schedule.end, 'HH:mm:ss').diff(today) <= 0" class="progressText">
                        <strong>{{ schedule.name }}</strong>
                    </el-text>
                </div>
                <div v-if="progressData[ProgressType.TOTAL].percentage === 0">
                    <el-text class="progressText"><strong>(empty)</strong></el-text>
                </div>
            </el-scrollbar>
        </div>
        <div class="progressCenter">
            <el-text class="progressGraphTitle">Today's Progress</el-text>
            <div class="progressGraph">
                <el-progress v-for="(progress, index) in progressData" type="circle" class="progressCircle" :key="index"
                    :width="180" :stroke-width="15" :percentage="percentages[index]" :color="customColors">
                    <template #default="{ percentage }">
                        <div style="margin-bottom: 10px;">
                            <el-text :type="progress.style" class="progressLabel">
                                {{ progress.name }}
                            </el-text>
                        </div>
                        <div><span class="progressNumber">{{ percentage }}%</span></div>
                    </template>
                </el-progress>
            </div>
        </div>
        <div class="progressRight">
            <el-text class="progressReadyTitle">[ Today's TODO List ]</el-text>
            <el-scrollbar style="margin-right: 15%; max-height: 80%">
                <div v-for="schedule in schedulesData[today.format('YYYY-MM-DD')]">
                    <el-text v-if="dayjs(schedule.end, 'HH:mm:ss').diff(today) > 0" class="progressText">
                        <strong>{{ schedule.name }}</strong>
                    </el-text>
                </div>
                <div v-if="progressData[ProgressType.TOTAL].percentage === 100">
                    <el-text class="progressText"><strong>(empty)</strong></el-text>
                </div>
            </el-scrollbar>
        </div>
    </el-card>

    <!-- Timeline -->
    <el-card class="homeSection">
        <el-text class="timelineTitle">Today's Timeline</el-text>
        <el-scrollbar v-if="schedulesData[today.format('YYYY-MM-DD')]">
            <div class="timeline"
                :style="{ width: `${Math.max(100, schedulesData[today.format('YYYY-MM-DD')].length * 15)}%` }">
                <div v-for="(schedule, index) in schedulesData[today.format('YYYY-MM-DD')]" :key="index"
                    class="timelineItem"
                    :style="{ width: `${100 / schedulesData[today.format('YYYY-MM-DD')].length}%` }">
                    <div class="timelineTime">
                        <el-text v-if="schedule.type === ScheduleType.COURSE">
                            {{ schedule.start }} - {{ schedule.end }}
                        </el-text>
                        <el-text v-else>
                            {{ schedule.start }}
                        </el-text>
                    </div>
                    <el-card class="timelineItemDetail">
                        <el-text v-if="dayjs(schedule.end, 'HH:mm:ss').diff(today) <= 0" class="timelineItemTextOver">
                            {{ schedule.name }}
                        </el-text>
                        <el-text v-else class="timelineItemTextReady">
                            {{ schedule.name }}
                        </el-text>
                    </el-card>
                </div>
            </div>
        </el-scrollbar>
        <div v-else>
            <el-text class="timelineEmptyMessage">
                Today's schedule is empty. Enjoy your day!
            </el-text>
        </div>
    </el-card>

    <!-- Carousel -->
    <el-card class="homeSection">
        <el-text type="primary" class="carouselExplanation">
            Recent {{ displayDayNum }} days' schedules ({{ today.format('YYYY-MM-DD') }} ----
            {{ today.add(displayDayNum - 1, 'day').format('YYYY-MM-DD') }})
        </el-text>
        <el-carousel :interval="4000" type="card" class="customCarousel">
            <el-carousel-item v-for="item in displayDayNum" :key="item" class="carouselCard">
                <el-text class="carouselTimestamp">{{ today.add(item - 1, 'day').format('YYYY-MM-DD') }}</el-text>
                <el-scrollbar style="max-height: 45%;">
                    <h3 v-for="(schedule, index) in schedulesData[today.add(item - 1, 'day').format('YYYY-MM-DD')]"
                        text="2xl" justify="center" :key="index">{{ schedule.name }}</h3>
                </el-scrollbar>
            </el-carousel-item>
        </el-carousel>
    </el-card>
</template>

<script lang="ts" setup>
import { computed, onMounted, ref, reactive } from 'vue'
import dayjs, { Dayjs } from 'dayjs'
import { storage, Task, CourseData, weekZh2Num, timeEndZh2Num, timeStartZh2Num } from '@/store/storage'

enum ScheduleType { RESERVED, COURSE, TASK }

interface Schedule {
    name: string,
    type: ScheduleType,
    start: string,
    end: string,
}

enum ProgressType { COURSE, TOTAL, TASK }

interface Progress {
    name: string,
    percentage: number,
    style: string,
}

const customColors = [
    { color: '#f56c6c', percentage: 20 },
    { color: '#e6a23c', percentage: 40 },
    { color: '#6f7ad3', percentage: 60 },
    { color: '#1989fa', percentage: 80 },
    { color: '#5cb87a', percentage: 100 },
]

const displayDayNum = ref(4)
const today = ref(dayjs())

const schedulesData = reactive(getSchedules())
const progressData = computed(() => getProgressData(schedulesData[today.value.format('YYYY-MM-DD')]))
const percentages = reactive([0, 0, 0])

function getSchedules() {
    let schedules = {}
    let taskData: Task[] = JSON.parse(storage.getItem('task'))
    let courseData: CourseData = JSON.parse(storage.getItem('course'))

    if (taskData) {
        for (let task of taskData) {
            let schedule: Schedule = {
                name: task.name,
                type: ScheduleType.TASK,
                start: task.time,
                end: task.time,
            }
            let taskDay = dayjs(task.date, 'YYYY-MM-DD')
            if (taskDay.diff(today.value, 'day') < 0) {
                continue
            }
            if (taskDay.diff(today.value, 'day') >= displayDayNum.value) {
                continue
            }
            if (schedules[taskDay.format('YYYY-MM-DD')]) {
                schedules[taskDay.format('YYYY-MM-DD')].push(schedule)
            } else {
                schedules[taskDay.format('YYYY-MM-DD')] = [schedule]
            }
        }
    }

    if (courseData) {
        for (let course of courseData.data) {
            let teachWeek = course.time.week.split('-')
            for (let weekNum = +teachWeek[0] - 1; weekNum < +teachWeek[1]; weekNum++) {
                for (let teachTime of course.time.time) {
                    let teachTimeSplit = teachTime.split(')')[0].split('(')
                    let teachDay = dayjs(courseData.start).add(weekNum, 'week')
                    teachDay = teachDay.add(weekZh2Num[teachTimeSplit[0]], 'day')
                    teachTimeSplit = teachTimeSplit[1].split('-')
                    let schedule: Schedule = {
                        name: course.name,
                        type: ScheduleType.COURSE,
                        start: timeStartZh2Num[teachTimeSplit[0]] + ':00',
                        end: timeEndZh2Num[teachTimeSplit[1]] + ':00',
                    }
                    if (teachDay.diff(today.value, 'day') < 0) {
                        continue
                    }
                    if (teachDay.diff(today.value, 'day') >= displayDayNum.value) {
                        continue
                    }
                    if (schedules[teachDay.format('YYYY-MM-DD')]) {
                        schedules[teachDay.format('YYYY-MM-DD')].push(schedule)
                    } else {
                        schedules[teachDay.format('YYYY-MM-DD')] = [schedule]
                    }
                }
            }
        }
    }

    let schedule: Schedule[] = schedules[today.value.format('YYYY-MM-DD')]
    if (schedule) {
        schedule.sort((a, b) => {
            let timeA = dayjs(a.start, 'HH:mm:ss')
            let timeB = dayjs(b.start, 'HH:mm:ss')
            let diff = timeA.diff(timeB)
            if (diff) {
                return diff
            } else {
                return b.type - a.type
            }
        })
    }

    return schedules
}

function getProgressData(schedules: Schedule[]) {
    let data: Progress[] = []
    let total = [0, 0, 0]
    let count = [0, 0, 0]
    let percentage = [100, 100, 100]

    if (schedules) {
        for (let schedule of schedules) {
            if (schedule.type !== ScheduleType.RESERVED) {
                let time: Dayjs = dayjs(schedule.end, 'HH:mm:ss')
                total[ScheduleType.RESERVED]++
                total[schedule.type]++
                if (today.value.diff(time, 'second') >= 0) {
                    count[ScheduleType.RESERVED]++
                    count[schedule.type]++
                }
            }
        }
        if (total[ScheduleType.COURSE]) {
            percentage[ProgressType.COURSE] = count[ScheduleType.COURSE] * 100 / total[ScheduleType.COURSE]
        }
        if (total[ScheduleType.TASK]) {
            percentage[ProgressType.TASK] = count[ScheduleType.TASK] * 100 / total[ScheduleType.TASK]
        }
        if (total[ScheduleType.RESERVED]) {
            percentage[ProgressType.TOTAL] = count[ScheduleType.RESERVED] * 100 / total[ScheduleType.RESERVED]
        }
    }

    data[ProgressType.COURSE] = {
        name: 'Course',
        percentage: percentage[ProgressType.COURSE],
        style: 'primary'
    }
    data[ProgressType.TASK] = {
        name: 'Task',
        percentage: percentage[ProgressType.TASK],
        style: 'warning'
    }
    data[ProgressType.TOTAL] = {
        name: 'Total',
        percentage: percentage[ProgressType.TOTAL],
        style: 'success'
    }
    return data
}

onMounted(() => {
    let steps = 10
    let totalTime = 100
    let currentStep = 1
    let intervalTime = totalTime / steps
    const interval = setInterval(() => {
        if (currentStep <= steps) {
            for (let index of [0, 1, 2]) {
                percentages[index] = Math.round(progressData.value[index].percentage / 10 * currentStep)
            }
            currentStep++
        } else {
            clearInterval(interval)
        }
    }, intervalTime)
})
</script>

<style scoped>
.homeSection {
    margin-top: 5px;
    font-style: italic;
}

/* Title */
.dynamicGradient {
    font-size: xx-large;
    font-weight: bolder;
    background: linear-gradient(90deg, #ff0080, #8000ff, #00c0ff);
    background-clip: text;
    -webkit-background-clip: text;
    background-size: 300% 1000%;
    color: transparent;
    animation: gradientAnimation 1.5s infinite alternate;
}

@keyframes gradientAnimation {
    0% {
        background-position: 0% 50%;
    }

    100% {
        background-position: 100% 50%;
    }
}

/* Progress */
.progressLeft {
    flex: 1;
    text-align: left;
}

.progressRight {
    flex: 1;
    text-align: right;
}

.progressCenter {
    display: flex;
    flex-direction: column;
    justify-content: center;
    margin-left: 5%;
    margin-right: 5%;
}

.progressGraph {
    margin-top: 10px;
    flex-direction: row;
    display: flex;
    justify-content: center;
}

.progressCircle {
    align-items: center;
    display: flex;
    flex-direction: column;
    margin-left: 5%;
    margin-right: 5%;
}

.progressGraphTitle {
    font-size: xx-large;
    font-weight: bolder;
    color: #8000ff;
}

.progressOverTitle {
    font-size: x-large;
    font-weight: bolder;
    color: green;
}

.progressReadyTitle {
    font-size: x-large;
    font-weight: bolder;
    color: red;
}

.progressText {
    font-size: larger;
    font-weight: bolder;
}

.progressLabel {
    font-weight: bolder;
    font-size: x-large;
}

.progressNumber {
    font-size: large;
}

/* Timeline */
.timeline {
    margin-top: 5%;
    margin-bottom: 10%;
    height: 0;
    display: flex;
    border-top: 2px solid var(--el-border-color);
    border-bottom: 2px solid var(--el-border-color);
    justify-content: space-between;
    align-items: center;
}

.timelineItem {
    flex-shrink: 0;
    text-align: center;
    position: relative;
    height: 50px;
    transform: translateY(50%);
}

.timelineItem::before {
    content: "";
    border-radius: 50%;
    position: absolute;
    width: 16px;
    height: 16px;
    transform: translate(-8px, -8px);
    border: 2px solid #fff;
    background-color: #99a9bf;
}

.timelineTime {
    transform: translateY(-150%);
}

.timelineItemDetail {
    width: 90%;
    transform: translate(5%, 20%);
}

.timelineItemTextOver {
    font-size: large;
    font-weight: bold;
    color: green;
}

.timelineItemTextReady {
    font-size: large;
    font-weight: bold;
    color: red;
}

.timelineTitle {
    font-size: xx-large;
    font-weight: bolder;
    color: coral;
}

.timelineEmptyMessage {
    font-size: x-large;
    font-weight: bolder;
    background: linear-gradient(90deg, #ff0000, #ff7f00, #ffff00, #00ff00, #0000ff, #4b0082, #9400d3);
    background-clip: text;
    -webkit-background-clip: text;
    background-size: 300% 1000%;
    color: transparent;
    animation: emptyScheduleAnimation 3s infinite alternate;
}

@keyframes emptyScheduleAnimation {
    0% {
        background-position: 0% 50%;
    }

    100% {
        background-position: 100% 50%;
    }
}

/* Carousel */
.customCarousel {
    height: 200px;
}

.carouselExplanation {
    font-size: xx-large;
    font-weight: bolder;
}

.carouselCard {
    display: flex;
    flex-direction: column;
    overflow: hidden;
    height: 100%;
}

.carouselTimestamp {
    flex: 0 0 auto;
    color: orange;
    font-size: x-large;
    font-weight: bolder;
}

.el-carousel__item h3 {
    color: #475669;
    font-weight: bold;
    opacity: 0.75;
    margin: 0;
    text-align: center;
    line-height: normal;
    padding: 7px 0;
}

.el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
    background-color: #d3dce6;
}
</style>