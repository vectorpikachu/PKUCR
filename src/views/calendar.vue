<template>
	<el-calendar ref="calendar" class="customCalendar" v-if="calendarMode">
		<template #header="{ date }">
			<el-text size="large" style="font-weight: bold;">Private Helper</el-text>
			<el-text size="large" style="font-weight: bold;">{{ date }}</el-text>
			<el-button-group>
				<el-button size="small" @click="selectDate('prev-year')">
					Previous Year
				</el-button>
				<el-button size="small" @click="selectDate('prev-month')">
					Previous Month
				</el-button>
				<el-button size="small" @click="selectDate('today')">Today</el-button>
				<el-button size="small" @click="selectDate('next-month')">
					Next Month
				</el-button>
				<el-button size="small" @click="selectDate('next-year')">
					Next Year
				</el-button>
			</el-button-group>
		</template>
		<template #date-cell="{ data }">
			<div class="cellTime"><span>{{ data.day.slice(-2) }}</span></div>
			<div v-if="schedules[data.day]">
				<div v-for="(schedule, index) in schedules[data.day]">
					<div v-if="index < MAX_SCHEDULE_NUM" @click="handleScheduleClick(schedule)">
						<el-text line-clamp="1"
							:class="schedule.type === ScheduleType.COURSE ? 'cellCourse' : 'cellTask'">
							{{ schedule.name }}
						</el-text>
					</div>
				</div>
				<div @click="handleCellClick(data.day)">
					<el-text class="cellTrunc" line-clamp="1">(show more)</el-text>
				</div>
			</div>
		</template>
	</el-calendar>

	<el-dialog v-model="scheduleCourseVisible" title="Course Detail" class="customDialog">
		<el-form :model="scheduleBuffer" class="customForm">
			<el-form-item class="detailItem">
				<el-text type="primary" class="detailLabel" size="large"><strong>Course Name</strong></el-text>
				<el-text type="success" class="detailText" size="large"><strong>{{ scheduleBuffer.name
						}}</strong></el-text>
			</el-form-item>
			<el-form-item class="detailItem">
				<el-text type="primary" class="detailLabel" size="large"><strong>Course Teacher</strong></el-text>
				<el-text type="warning" class="detailText" size="large"><strong>{{ scheduleBuffer.teacher
						}}</strong></el-text>
			</el-form-item>
			<el-form-item class="detailItem">
				<el-text type="primary" class="detailLabel" size="large"><strong>Course Time</strong></el-text>
				<el-text type="danger" class="detailText" size="large"><strong>{{ scheduleBuffer.time
						}}</strong></el-text>
			</el-form-item>
			<el-form-item class="detailItem">
				<el-text type="primary" class="detailLabel" size="large"><strong>Course Classroom</strong></el-text>
				<el-text type="danger" class="detailText" size="large"><strong>{{ scheduleBuffer.classroom
						}}</strong></el-text>
			</el-form-item>
			<el-form-item class="detailItem">
				<el-text type="primary" class="detailLabel" size="large"><strong>Resource Link</strong></el-text>
				<el-link :href="scheduleBuffer.appendix" :disabled="scheduleBuffer.appendix === 'None'">
					<el-text type="info" class="detailText" size="large"><strong>{{ scheduleBuffer.appendix
							}}</strong></el-text>
				</el-link>
			</el-form-item>
			<el-form-item>
				<el-col>
					<el-button type="primary" @click="handleScheduleCourseClose">
						Back
					</el-button>
				</el-col>
			</el-form-item>
		</el-form>
	</el-dialog>

	<el-dialog v-model="scheduleTaskVisible" title="Task Detail" class="customDialog">
		<el-form :model="scheduleBuffer" class="customForm">
			<el-form-item class="detailItem">
				<el-text type="primary" class="detailLabel" size="large"><strong>Task Name</strong></el-text>
				<el-text type="success" class="detailText" size="large"><strong>{{ scheduleBuffer.name
						}}</strong></el-text>
			</el-form-item>
			<el-form-item class="detailItem">
				<el-text type="primary" class="detailLabel" size="large"><strong>Task Deadline</strong></el-text>
				<el-text type="danger" class="detailText" size="large"><strong>{{ scheduleBuffer.deadline
						}}</strong></el-text>
			</el-form-item>
			<el-form-item class="detailItem">
				<el-text type="primary" class="detailLabel" size="large"><strong>Task Memo</strong></el-text>
				<el-text type="info" class="detailText" size="large"><strong>{{ scheduleBuffer.appendix
						}}</strong></el-text>
			</el-form-item>
			<el-form-item>
				<el-col>
					<el-button type="primary" @click="handleScheduleTaskClose">
						Back
					</el-button>
				</el-col>
			</el-form-item>
		</el-form>
	</el-dialog>

	<el-dialog v-model="schedulesVisible" title="Schedules Detail" class="customDialog">
		<el-timeline style="max-width: 600px;">
			<el-timeline-item v-for="schedule in schedules[currentSchedules]" placement="top"
				:timestamp="getScheduleTime(schedule)" :color="getScheduleColor(schedule)" style="text-align: left;">
				<el-card>
					<div :class="schedule.type === ScheduleType.COURSE ? 'cellCourse' : 'cellTask'"
						@click=" handleScheduleClick(schedule)">
						<h3>{{ schedule.name }}</h3>
					</div>
					<p class="detailAppendix" v-if="schedule.type === ScheduleType.COURSE">{{ schedule.teacher }} {{
						schedule.classroom }}</p>
					<p class="detailAppendix" v-else>{{ schedule.appendix }}</p>
				</el-card>
			</el-timeline-item>
		</el-timeline>
		<el-col>
			<el-button type="primary" @click="handleSchedulesClose">
				Back
			</el-button>
		</el-col>
	</el-dialog>
</template>

<script lang="ts" setup>
import { ref } from 'vue'
import { type CalendarDateType, type CalendarInstance } from 'element-plus'
import dayjs, { Dayjs } from 'dayjs'
import { Task, Course, storage } from '@/store/storage'

// definitions
enum ScheduleType { RESERVED, COURSE, TASK }

interface Schedule {
	type: ScheduleType,
	name: string,
	time: string,
	teacher: string,
	classroom: string,
	deadline: string,
	appendix: string,
}

// variables
let scheduleBuffer = {
	type: ScheduleType.RESERVED,
	name: '',
	time: '',
	teacher: '',
	classroom: '',
	deadline: '',
	appendix: '',
}

const MAX_SCHEDULE_NUM = ref(3)

let scheduleCourseVisible = ref(false)
let scheduleTaskVisible = ref(false)
let schedulesVisible = ref(false)
let currentSchedules = ref('')

const calendar = ref<CalendarInstance>()
const calendarMode = ref(true)

let schedulesData = parseSchedules()
let schedules = ref(schedulesData)

// functions
function selectDate(val: CalendarDateType) {
	if (!calendar.value) return
	calendar.value.selectDate(val)
}

function parseSchedules() {
	let schedulesRecord = {}
	let taskData: Task[] = JSON.parse(storage.getItem('task'))
	let courseData: Course[] = JSON.parse(storage.getItem('course'))

	if (taskData) {
		for (let task of taskData) {
			let scheduleFromTask: Schedule = {
				type: ScheduleType.TASK,
				name: task.name,
				time: '',
				teacher: '',
				classroom: '',
				deadline: task.time,
				appendix: task.memo
			}
			if (schedulesRecord[task.date]) {
				schedulesRecord[task.date].push(scheduleFromTask)
			} else {
				schedulesRecord[task.date] = [scheduleFromTask]
			}
		}
	}

	if (courseData) {
		for (let course of courseData) {
			for (let teachTime of course.time) {
				let endDay: Dayjs = dayjs(teachTime.endDate, 'YYYY-MM-DD')
				for (let teachDay = dayjs(teachTime.startDate, 'YYYY-MM-DD');
					teachDay.diff(endDay) <= 0;
					teachDay = teachDay.add(teachTime.frequency, 'day')) {
					let scheduleFromCourse: Schedule = {
						type: ScheduleType.COURSE,
						name: course.name,
						time: teachTime.startTime,
						teacher: course.teacher,
						classroom: course.classroom,
						deadline: '',
						appendix: course.link,
					}
					if (schedulesRecord[teachDay.format('YYYY-MM-DD')]) {
						schedulesRecord[teachDay.format('YYYY-MM-DD')].push(scheduleFromCourse)
					} else {
						schedulesRecord[teachDay.format('YYYY-MM-DD')] = [scheduleFromCourse]
					}
				}
			}
		}
	}

	for (let key in schedulesRecord) {
		let schedulesRaw: Schedule[] = schedulesRecord[key]
		schedulesRaw.sort((a, b) => {
			let timeA: Dayjs, timeB: Dayjs

			if (getScheduleTime(a) === '') {
				return 1;
			} else {
				timeA = dayjs(getScheduleTime(a), 'HH:mm')
			}

			if (getScheduleTime(b) === '') {
				return -1;
			} else {
				timeB = dayjs(getScheduleTime(b), 'HH:mm')
			}

			let diff = timeA.diff(timeB)
			if (diff) {
				return diff
			} else {
				return b.type - a.type
			}
		})
	}

	return schedulesRecord
}

function handleCellClick(date: string) {
	schedulesVisible.value = true
	currentSchedules.value = date
}

function handleScheduleClick(schedule: Schedule) {
	if (schedule.type === ScheduleType.COURSE) {
		scheduleCourseVisible.value = true
		scheduleBuffer = schedule
	}
	else if (schedule.type === ScheduleType.TASK) {
		scheduleTaskVisible.value = true
		scheduleBuffer = schedule
	}
}

function handleScheduleCourseClose() {
	scheduleCourseVisible.value = false
}

function handleScheduleTaskClose() {
	scheduleTaskVisible.value = false
}

function handleSchedulesClose() {
	schedulesVisible.value = false
}

function getScheduleTime(schedule: Schedule) {
	if (schedule.time !== '') {
		return schedule.time.split('-')[0] + ':00'
	} else if (schedule.deadline !== '') {
		return schedule.deadline
	} else {
		return ''
	}
}

function getScheduleColor(schedule: Schedule) {
	if (schedule.type === ScheduleType.COURSE) {
		return 'purple';
	} else if (schedule.type == ScheduleType.TASK) {
		return 'red';
	} else {
		return 'green';
	}
}
</script>

<style scoped>
.customCalendar :deep() .el-calendar-table .el-calendar-day {
	width: 100%;
	height: 150px;
}

.customDialog {
	width: 500;
	font-family: Georgia, 'Times New Roman', Times, serif;
}

.customForm {
	max-width: 600px;
}

.cellTime {
	font-style: normal;
	text-align: right;
}

.cellCourse {
	font-style: italic;
	color: purple;
	text-align: left;
}

.cellCourse:hover {
	color: orange;
}

.cellTask {
	font-style: italic;
	color: red;
	text-align: left;
}

.cellTask:hover {
	color: orange;
}

.cellTrunc {
	font-style: italic;
	color: silver;
	text-align: left;
}

.cellTrunc:hover {
	color: orange;
}

.detailLabel {
	margin: 8px;
	font-weight: bolder;
	font-style: italic;
	width: 30%;
	text-align: left
}

.detailItem {
	display: flex;
	justify-content: space-between;
}

.detailText {
	font-weight: bolder;
	font-style: italic;
}

.detailAppendix {
	color: silver;
	font-style: italic;
	padding-left: 5%;
}
</style>