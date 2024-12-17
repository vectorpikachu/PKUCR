<template>
    <el-text class="dynamicGradient">Welcome to PKUCR, your private time management helper!</el-text>
    <div><span>To be filled</span></div>
    <div><span>To be filled</span></div>
    <div><span>To be filled</span></div>
    <div><span>To be filled</span></div>
    <div><span>To be filled</span></div>
    <div><span>To be filled</span></div>
    <div><span>To be filled</span></div>
    <div><span>To be filled</span></div>
    <div><span>To be filled</span></div>
    <div><span>To be filled</span></div>
    <div><span>To be filled</span></div>
    <div><span>To be filled</span></div>
    <div><span>To be filled</span></div>
    <div><span>To be filled</span></div>
    <div><span>To be filled</span></div>
    <div><span>To be filled</span></div>
    <div><span>To be filled</span></div>
    <div><span>To be filled</span></div>
    <div><span>To be filled</span></div>
    <div><span>To be filled</span></div>
    <div><span>To be filled</span></div>
    <div><span>To be filled</span></div>
    <div><span>To be filled</span></div>
    <div><span>To be filled</span></div>
    <div>
        <el-carousel :interval="4000" type="card" class="customCarousel">
            <el-carousel-item v-for="item in displayDayNum" :key="item" class="carouselCard">
                <el-text class="carouselTimestamp">{{ today.add(item - 1, 'day').format('YYYY-MM-DD') }}</el-text>
                <el-scrollbar style="max-height: 45%;">
                    <h3 v-for="schedule in displaySchedules[today.add(item - 1, 'day').format('YYYY-MM-DD')]" text="2xl"
                        justify="center" :key="schedule">{{ schedule }}</h3>
                </el-scrollbar>
            </el-carousel-item>
        </el-carousel>
        <el-text type="primary" class="carouselExplanation">[ Recent {{ displayDayNum }} days' schedules ({{
            today.format('YYYY-MM-DD') }} ---- {{ today.add(displayDayNum, 'day').format('YYYY-MM-DD') }}) ]</el-text>
    </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue'
import dayjs from 'dayjs'
import { storage, Task, CourseData, weekZh2Num } from '@/store/storage'

const displayDayNum = ref(4)
const today = ref(dayjs())

const displayData = getSchedules()
const displaySchedules = ref(displayData)

function getSchedules() {
    let schedules = {}
    let taskData: Task[] = JSON.parse(storage.getItem('task'))
    let courseData: CourseData = JSON.parse(storage.getItem('course'))

    if (taskData) {
        for (let task of taskData) {
            let taskDay = dayjs(task.date, 'YYYY-MM-DD')
            if (taskDay.diff(today.value, 'day') < 0) {
                continue
            }
            if (taskDay.diff(today.value, 'day') >= displayDayNum.value) {
                continue
            }
            if (schedules[taskDay.format('YYYY-MM-DD')]) {
                schedules[taskDay.format('YYYY-MM-DD')].push(task.name)
            } else {
                schedules[taskDay.format('YYYY-MM-DD')] = [task.name]
            }
        }
    }

    if (courseData) {
        for (let course of courseData.data) {
            let teachWeek = course.time.week.split('-')
            for (let weekNum = +teachWeek[0] - 1; weekNum < +teachWeek[1]; weekNum++) {
                for (let teachTime of course.time.time) {
                    let teachTimeSplit = teachTime.split('(')[0]
                    let teachDay = dayjs(courseData.start).add(weekNum, 'week')
                    teachDay = teachDay.add(weekZh2Num[teachTimeSplit], 'day')
                    if (teachDay.diff(today.value, 'day') < 0) {
                        continue
                    }
                    if (teachDay.diff(today.value, 'day') >= displayDayNum.value) {
                        continue
                    }
                    if (schedules[teachDay.format('YYYY-MM-DD')]) {
                        schedules[teachDay.format('YYYY-MM-DD')].push(course.name)
                    } else {
                        schedules[teachDay.format('YYYY-MM-DD')] = [course.name]
                    }
                }
            }
        }
    }

    return schedules
}
</script>

<style scoped>
.customCarousel {
    height: 200px;
}

.carouselExplanation {
    font-size: x-large;
    font-weight: bold;
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
    font-style: italic;
    font-size: x-large;
    font-weight: bolder;
}

.el-carousel__item h3 {
    color: #475669;
    font-style: italic;
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

.dynamicGradient {
    font-size: xx-large;
    font-weight: bolder;
    background: linear-gradient(90deg, #ff0080, #8000ff, #00c0ff);
    background-clip: text;
    -webkit-background-clip: text;
    background-size: 300% 1000%;
    color: transparent;
    animation: gradientAnimation 2s infinite alternate;
}

@keyframes gradientAnimation {
    0% {
        background-position: 0% 50%;
    }

    100% {
        background-position: 100% 50%;
    }
}
</style>