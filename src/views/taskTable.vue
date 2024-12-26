<template>
  <el-row>
    <el-col>
      <el-scrollbar>
        <el-table
          v-if="config.outputStatus.value === OUTPUT_STATUS.TASK"
          :data="tableData"
          height="600"
        >
          <el-table-column>
            <template #header>
              <el-button type="info" text @click="handleSort.date">
                Date
                <el-icon v-if="config.sortStatus.date.value === SORT_STATUS.NONE">
                  <Sort />
                </el-icon>
                <el-icon v-else-if="config.sortStatus.date.value === SORT_STATUS.SEQ">
                  <SortDown />
                </el-icon>
                <el-icon v-else>
                  <SortUp />
                </el-icon>
              </el-button>
            </template>
            <template #default="scope">
              <div style="display: flex; align-items: center">
                <el-icon>
                  <timer />
                </el-icon>
                <span style="margin-left: 10px">{{ scope.row.date }}</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column>
            <template #header>
              <el-button type="info" text @click="handleSort.name">
                Task
                <el-icon v-if="config.sortStatus.name.value === SORT_STATUS.NONE">
                  <Sort />
                </el-icon>
                <el-icon v-else-if="config.sortStatus.name.value === SORT_STATUS.SEQ">
                  <SortDown />
                </el-icon>
                <el-icon v-else>
                  <SortUp />
                </el-icon>
              </el-button>
            </template>
            <template #default="scope">
              <el-popover effect="light" trigger="hover" placement="top" width="auto">
                <template #default>
                  <div>task: {{ scope.row.name }}</div>
                  <div>memo: {{ scope.row.memo }}</div>
                </template>
                <template #reference>
                  <el-tag>{{ scope.row.name }}</el-tag>
                </template>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column>
            <template #header>
              <el-button type="info" text @click="handleSort.time">
                Time
                <el-icon v-if="config.sortStatus.time.value === SORT_STATUS.NONE">
                  <Sort />
                </el-icon>
                <el-icon v-else-if="config.sortStatus.time.value === SORT_STATUS.SEQ">
                  <SortDown />
                </el-icon>
                <el-icon v-else>
                  <SortUp />
                </el-icon>
              </el-button>
            </template>
            <template #default="scope">
              <el-text>
                {{ scope.row.time }}
              </el-text>
            </template>
          </el-table-column>
          <el-table-column label="Memo" prop="memo" />
          <el-table-column>
            <template #header>
              <el-button
                type="primary"
                :loading="config.taskFormVisible.value"
                @click="config.taskFormVisible.value = true"
              >
                <template #loading>
                  <div class="custom-loading">
                    <svg class="circular" viewBox="-10, -10, 50, 50">
                      <path
                        class="path"
                        d="
                          M 30 15
                          L 28 17
                          M 25.61 25.61
                          A 15 15, 0, 0, 1, 15 30
                          A 15 15, 0, 1, 1, 27.99 7.5
                          L 15 15
                          "
                        style="stroke-width: 4px; fill: rgba(0, 0, 0, 0)"
                      />
                    </svg>
                  </div>
                </template>
                Add Task
              </el-button>
              <el-button type="primary" @click="config.outputStatus.value = OUTPUT_STATUS.COURSE">
                Swtich to Course
              </el-button>
            </template>
            <template #default="scope">
              <el-button size="small" @click="handleEdit(scope.$index, scope.row)">
                Edit
              </el-button>
              <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">
                Delete
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-table
          v-if="config.outputStatus.value === OUTPUT_STATUS.COURSE"
          :data="courseData"
          height="600"
        >
          <el-table-column>
            <template #header>
              <el-button type="info" text>
                Course
                <el-icon v-if="config.sortStatus.name.value === SORT_STATUS.NONE">
                  <Sort />
                </el-icon>
                <el-icon v-else-if="config.sortStatus.name.value === SORT_STATUS.SEQ">
                  <SortDown />
                </el-icon>
                <el-icon v-else>
                  <SortUp />
                </el-icon>
              </el-button>
            </template>
            <template #default="scope">
              <el-button type="info" plain text @click="presentInfo(scope.$index, scope.row)">
                {{ scope.row.name }}
              </el-button>
              <el-popover effect="light" trigger="hover" placement="top" width="auto">
                <template #default>
                  <div>course: {{ scope.row.name }}</div>
                  <div>teacher: {{ scope.row.teacher }}</div>
                  <div>classroom: {{ scope.row.classroom }}</div>
                  <div>start at {{ scope.row.time.startDate }}</div>
                  <div>happens per {{ scope.row.time.frequency }} days</div>
                  <div>until {{ scope.row.time.endDate }}</div>
                </template>
                <template #reference> </template>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column>
            <template #header>
              <el-button type="info" text @click="handleSort.time">
                Classroom
                <el-icon v-if="config.sortStatus.time.value === SORT_STATUS.NONE">
                  <Sort />
                </el-icon>
                <el-icon v-else-if="config.sortStatus.time.value === SORT_STATUS.SEQ">
                  <SortDown />
                </el-icon>
                <el-icon v-else>
                  <SortUp />
                </el-icon>
              </el-button>
            </template>
            <template #default="scope">
              <el-text>
                {{ scope.row.classroom }}
              </el-text>
            </template>
          </el-table-column>
          <el-table-column>
            <template #header>
              <el-button type="info" text @click="handleSort.time">
                Teacher
                <el-icon v-if="config.sortStatus.time.value === SORT_STATUS.NONE">
                  <Sort />
                </el-icon>
                <el-icon v-else-if="config.sortStatus.time.value === SORT_STATUS.SEQ">
                  <SortDown />
                </el-icon>
                <el-icon v-else>
                  <SortUp />
                </el-icon>
              </el-button>
            </template>
            <template #default="scope">
              <el-text>
                {{ scope.row.teacher }}
              </el-text>
            </template>
          </el-table-column>
          <el-table-column>
            <template #header>
              <el-button
                type="primary"
                :loading="config.courseFormVisible.value"
                @click="handleAddCourse"
              >
                <template #loading>
                  <div class="custom-loading">
                    <svg class="circular" viewBox="-10, -10, 50, 50">
                      <path
                        class="path"
                        d="
                          M 30 15
                          L 28 17
                          M 25.61 25.61
                          A 15 15, 0, 0, 1, 15 30
                          A 15 15, 0, 1, 1, 27.99 7.5
                          L 15 15
                          "
                        style="stroke-width: 4px; fill: rgba(0, 0, 0, 0)"
                      />
                    </svg>
                  </div>
                </template>
                Add Course
              </el-button>
              <el-button type="primary" @click="config.outputStatus.value = OUTPUT_STATUS.TASK">
                Swtich to Task
              </el-button>
            </template>
            <template #default="scope">
              <el-button size="small"> Edit </el-button>
              <el-button
                size="small"
                type="danger"
                @click="handleCourseDelete(scope.$index, scope.row)"
              >
                Delete
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-scrollbar>
    </el-col>
  </el-row>
  <el-dialog v-model="config.courseInfoVisible.value" title="Course Info" width="500">
    <span> {{ config.courseForm.name }} </span>
    <div>
      {{ 'Teacher: ' + config.courseForm.teacher }}
    </div>
    <div>
      {{ 'Classroom: ' + config.courseForm.classroom }}
    </div>
    <div>
      {{ 'Time: ' }}
      <div v-for="time in config.courseForm.time">
        {{
          'From: ' +
          time.startDate +
          ' to: ' +
          time.endDate +
          ' ' +
          time.startTime +
          ' - ' +
          time.endTime +
          ' per ' +
          time.frequency +
          'days'
        }}
      </div>
    </div>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="courseInfoClose">Close</el-button>
      </div>
    </template>
  </el-dialog>
  <!-- Dialog of adding task -->
  <el-dialog v-model="config.taskFormVisible.value" title="Add New Task" width="500">
    <el-form :model="config.taskForm" label-width="auto" style="max-width: 600px">
      <el-form-item label="Task name">
        <el-input v-model="config.taskForm.name" />
      </el-form-item>
      <el-form-item label="Task date">
        <el-col :span="11">
          <el-date-picker
            v-model="config.taskForm.date"
            type="date"
            placeholder="Pick a date"
            style="width: 100%"
            value-format="YYYY-MM-DD"
          />
        </el-col>
        <el-col :span="2" class="text-center">
          <span class="text-gray-500">-</span>
        </el-col>
        <el-col :span="11">
          <el-time-picker
            v-model="config.taskForm.time"
            placeholder="Pick a time"
            style="width: 100%"
            value-format="HH:mm:ss"
          />
        </el-col>
      </el-form-item>
      <el-form-item label="Task Priority">
        <el-slider
          v-model="config.taskForm.priority"
          :step="10"
          :format-tooltip="formatTooltip"
          show-stops
        />
      </el-form-item>
      <el-form-item label="Task memo">
        <el-input v-model="config.taskForm.memo" type="textarea" />
      </el-form-item>
      <el-form-item>
        <el-button @click="taskFormCancel">Cancel</el-button>
        <el-button type="primary" @click="taskFormSubmit"> Confirm </el-button>
      </el-form-item>
    </el-form>
  </el-dialog>

  <el-dialog v-model="config.courseFormVisible.value" title="Add New Course" width="500">
    <el-form :model="config.courseForm" label-width="auto" style="max-width: 600px">
      <el-form-item label="Course name">
        <el-input v-model="config.courseForm.name" />
      </el-form-item>
      <el-form-item label="Course Start-End Date">
        <el-col :span="11">
          <el-date-picker
            v-model="config.courseForm.time[0].startDate"
            type="date"
            placeholder="Pick a date"
            style="width: 100%"
            value-format="YYYY-MM-DD"
          />
        </el-col>
        <el-col :span="2" class="text-center">
          <span class="text-gray-500">-</span>
        </el-col>
        <el-col :span="11">
          <el-date-picker
            v-model="config.courseForm.time[0].endDate"
            type="date"
            placeholder="Pick a date"
            style="width: 100%"
            value-format="YYYY-MM-DD"
          />
        </el-col>
      </el-form-item>
      <el-form-item label="Course Start-End Time">
        <el-col :span="11">
          <el-time-picker
            v-model="config.courseForm.time[0].startTime"
            placeholder="Pick a time"
            style="width: 100%"
            value-format="HH:mm:ss"
          />
        </el-col>
        <el-col :span="2" class="text-center">
          <span class="text-gray-500">-</span>
        </el-col>
        <el-col :span="11">
          <el-time-picker
            v-model="config.courseForm.time[0].endTime"
            placeholder="Pick a time"
            style="width: 100%"
            value-format="HH:mm:ss"
          />
        </el-col>
      </el-form-item>
      <el-form-item label="Classroom">
        <el-input v-model="config.courseForm.classroom" />
      </el-form-item>
      <el-form-item label="Teacher">
        <el-input v-model="config.courseForm.teacher" />
      </el-form-item>
      <el-form-item>
        <el-button @click="courseFormCancel">Cancel</el-button>
        <el-button type="primary" @click="courseFormSubmit"> Confirm </el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script lang="ts" setup>
import { ref, reactive } from 'vue'
import { Sort, Loading, Timer, SortDown, SortUp } from '@element-plus/icons-vue'
import axios from '../axios'
import { Task, Course, storage } from '@/store/storage'

// Backend axios address
const AXIOS_ADDRESS = {
  INSERT: '/task/insert',
  UPDATE: '/task/update',
  SELECT: '/task/select',
  DELETE: '/task/delete'
}

const COURSE_AXIOS_ADDRESS = {
  INSERT: '/course/insert',
  DELETE: '/course/delete'
}

// Message exchanging functinos with server
const sendSignal = {
  put: (t) => {
    return axios.put(AXIOS_ADDRESS.UPDATE, {
      id: 42,
      name: t.name,
      date: t.date + ' ' + t.time,
      priority: t.priority,
      description: t.memo
    })
    // .then((res) => {
    //   console.log(res)
    // })
  },
  post: async (t) => {
    return await axios.post(AXIOS_ADDRESS.INSERT, {
      name: t.name,
      date: t.date + ' ' + t.time,
      priority: t.priority,
      description: t.memo
    })
  },
  get: async (id) => {
    return await axios.get(AXIOS_ADDRESS.SELECT, id)
  },
  delete: (id) => {
    axios.delete(AXIOS_ADDRESS.DELETE, id).then((res) => {
      console.log(res)
    })
  },
  coursePost: async (t) => {
    return await axios.post(COURSE_AXIOS_ADDRESS.INSERT, courseFrom(t))
  },
  courseDelete: (id) => {
    axios.delete(COURSE_AXIOS_ADDRESS.DELETE, id).then((res) => {
      console.log(res)
    })
  }
}

const COMP_ID = {
  PRIORITY: (lhs, rhs) => {
    return 0
  },
  DATE: (lhs, rhs) => {
    return 1
  },
  TIME: (lhs, rhs) => {
    return 2
  },
  NAME: (lhs, rhs) => {
    return 3
  },
  NEG: (ID) => {
    return (lhs, rhs) => {
      return -ID(lhs, rhs)
    }
  }
}

const COMP_TASK = {
  PRIORITY: (lhs, rhs) => {
    return rhs.priority - lhs.priority
  },
  DATE: (lhs, rhs) => {
    if (lhs.date > rhs.date) return 1
    else if (lhs.date < rhs.date) return -1
    else return 0
  },
  TIME: (lhs, rhs) => {
    if (lhs.time > rhs.time) return 1
    else if (lhs.time < rhs.time) return -1
    else return 0
  },
  NAME: (lhs, rhs) => {
    if (lhs.name > rhs.name) return 1
    else if (lhs.name < rhs.name) return -1
    else return 0
  },
  NONE: (lhs, rhs) => {
    return 0
  }
}

const SORT_STATUS = {
  NONE: 0,
  SEQ: 1,
  REV: 2
}

const OUTPUT_STATUS = {
  TASK: 0,
  COURSE: 1
}

// Status buffer for functions
let config = {
  outputStatus: ref(OUTPUT_STATUS.TASK),
  taskFormVisible: ref(false),
  courseFormVisible: ref(false),
  courseInfoVisible: ref(false),
  isEdit: ref(false),
  recentTask: ref(0),
  taskForm: reactive({
    id: 0,
    priority: 0,
    name: '',
    date: '',
    time: '',
    memo: ''
  }),
  courseForm: reactive({
    id: 0,
    name: '',
    teacher: '',
    classroom: '',
    time: [
      {
        startDate: '',
        endDate: '',
        startTime: '',
        endTime: '',
        frequency: 7
      }
    ],
    link: ''
  }),
  compTask: ref([[COMP_ID.PRIORITY, COMP_TASK.PRIORITY]]),
  sortStatus: {
    name: ref(SORT_STATUS.NONE),
    date: ref(SORT_STATUS.NONE),
    time: ref(SORT_STATUS.NONE),
    memo: ref(SORT_STATUS.NONE)
  }
}

const formatTooltip = (val: number) => {
  return val / 10
}

function newTask(id, priority, date, name, time, memo) {
  return {
    id: 0,
    priority: priority,
    date: date,
    name: name,
    time: time,
    memo: memo
  }
}

// Data sttucture of task
function taskFrom(form) {
  return {
    id: form.id,
    priority: form.priority,
    date: form.date,
    name: form.name,
    time: form.time,
    memo: form.memo
  }
}

function courseFrom(course) {
  return {
    id: course.id,
    name: course.name,
    teacher: course.teacher,
    classroom: course.classroom,
    time: course.time,
    link: course.link
  }
}

function compTask(lhs, rhs) {
  for (const comp of config.compTask.value) {
    let res = comp[1](lhs, rhs)
    if (res !== 0)
      if (comp[0](lhs, rhs) >= 0) return res
      else return -res
  }
  return 0
}

// Different types of sort function
const handleSort = {
  name: () => {
    config.sortStatus.name.value = (config.sortStatus.name.value + 1) % 3
    let val = config.sortStatus.name.value
    let index = -1
    switch (val) {
      case SORT_STATUS.NONE:
        for (let i = 0; ; ++i) {
          if (config.compTask.value[i][0](0, 0) === -COMP_ID.NAME(0, 0)) {
            index = i
            break
          }
        }
        if (index !== -1) {
          config.compTask.value.splice(index, 1)
        }
        break
      case SORT_STATUS.SEQ:
        let len = config.compTask.value.length
        config.compTask.value.splice(len - 1, 0, [COMP_ID.NAME, COMP_TASK.NAME])
        break
      case SORT_STATUS.REV:
        for (let i = 0; ; ++i) {
          if (config.compTask.value[i][0](0, 0) === COMP_ID.NAME(0, 0)) {
            index = i
            break
          }
        }
        if (index !== -1) {
          config.compTask.value.splice(index, 1, [COMP_ID.NEG(COMP_ID.NAME), COMP_TASK.NAME])
        }
        break
    }
    tableData.value.sort(compTask)
  },

  date: () => {
    config.sortStatus.date.value = (config.sortStatus.date.value + 1) % 3
    let val = config.sortStatus.date.value
    let index = -1
    switch (val) {
      case SORT_STATUS.NONE:
        for (let i = 0; ; ++i) {
          if (config.compTask.value[i][0](0, 0) === -COMP_ID.DATE(0, 0)) {
            index = i
            break
          }
        }
        if (index !== -1) {
          config.compTask.value.splice(index, 1)
        }
        break
      case SORT_STATUS.SEQ:
        let len = config.compTask.value.length
        config.compTask.value.splice(len - 1, 0, [COMP_ID.DATE, COMP_TASK.DATE])
        break
      case SORT_STATUS.REV:
        for (let i = 0; ; ++i) {
          if (config.compTask.value[i][0](0, 0) === COMP_ID.DATE(0, 0)) {
            index = i
            break
          }
        }
        if (index !== -1) {
          config.compTask.value.splice(index, 1, [COMP_ID.NEG(COMP_ID.DATE), COMP_TASK.DATE])
        }
        break
    }
    tableData.value.sort(compTask)
  },

  time: () => {
    config.sortStatus.time.value = (config.sortStatus.time.value + 1) % 3
    let val = config.sortStatus.time.value
    let index = -1
    switch (val) {
      case SORT_STATUS.NONE:
        for (let i = 0; ; ++i) {
          if (config.compTask.value[i][0](0, 0) === -COMP_ID.TIME(0, 0)) {
            index = i
            break
          }
        }
        if (index !== -1) {
          config.compTask.value.splice(index, 1)
        }
        break
      case SORT_STATUS.SEQ:
        let len = config.compTask.value.length
        config.compTask.value.splice(len - 1, 0, [COMP_ID.TIME, COMP_TASK.TIME])
        break
      case SORT_STATUS.REV:
        for (let i = 0; ; ++i) {
          if (config.compTask.value[i][0](0, 0) === COMP_ID.TIME(0, 0)) {
            index = i
            break
          }
        }
        if (index !== -1) {
          config.compTask.value.splice(index, 1, [COMP_ID.NEG(COMP_ID.TIME), COMP_TASK.TIME])
        }
        break
    }
    tableData.value.sort(compTask)
  }
}

// Update data into storage
function tableDataUpdateLocal() {
  let data = JSON.stringify(tableData.value)
  storage.setItem('task', data)
}

function courseDataUpdateLocal() {
  let data = JSON.stringify(courseData.value)
  storage.setItem('course', data)
}

// Fetch initial data from server
function tableDataFetchLocal() {
  let taskData: Task[] = JSON.parse(storage.getItem('task'))
  if (taskData) {
    return taskData
  }
  return []
}

function courseDataFetchLocal() {
  let courseData: Course[] = JSON.parse(storage.getItem('course'))
  if (courseData) {
    return courseData
  }
  return []
}

// Submite task form to server and update local data
async function taskFormSubmit() {
  if (config.isEdit.value) {
    config.isEdit.value = false
    let id = tableData.value[config.recentTask.value].id
    config.taskForm.id = id
    let response = sendSignal.put(config.taskForm)
    await response.then((res) => {
      if (res.data === 'update success') {
        tableData.value[config.recentTask.value] = config.taskForm
        tableDataUpdateLocal()
      }
    })
  } else {
    let response = sendSignal.post(config.taskForm)
    await response.then((res) => {
      config.taskForm.id = res.data.id
    })
    tableData.value.push(taskFrom(config.taskForm))
    tableDataUpdateLocal()
  }
  tableData.value.sort(compTask)
  config.taskForm = reactive({
    id: 0,
    priority: 0,
    name: '',
    date: '',
    time: '',
    memo: ''
  })
  config.taskFormVisible.value = false
}

function courseInfoClose() {
  config.courseForm = reactive({
    id: 0,
    name: '',
    teacher: '',
    classroom: '',
    time: [
      {
        startDate: '',
        endDate: '',
        startTime: '',
        endTime: '',
        frequency: 7
      }
    ],
    link: ''
  })
  config.courseInfoVisible.value = false
}

async function courseFormSubmit() {
  let response = sendSignal.coursePost(config.courseForm)
  await response.then((res) => {
    config.courseForm.id = res.data.id
  })
  courseData.value.push(courseFrom(config.courseForm))
  courseDataUpdateLocal()
  config.courseForm = reactive({
    id: 0,
    name: '',
    teacher: '',
    classroom: '',
    time: [
      {
        startDate: '',
        endDate: '',
        startTime: '',
        endTime: '',
        frequency: 7
      }
    ],
    link: ''
  })
  config.courseFormVisible.value = false
}

function taskFormCancel() {
  if (config.isEdit.value) {
    config.isEdit.value = false
    config.taskForm = reactive({
      id: 0,
      priority: 0,
      name: '',
      date: '',
      time: '',
      memo: ''
    })
  }
  config.taskFormVisible.value = false
}

function courseFormCancel() {
  config.courseForm = reactive({
    id: 0,
    name: '',
    teacher: '',
    classroom: '',
    time: [
      {
        startDate: '',
        endDate: '',
        startTime: '',
        endTime: '',
        frequency: 7
      }
    ],
    link: ''
  })
  config.courseFormVisible.value = false
}

function handleAddCourse() {
  config.courseFormVisible.value = true
}

function handleEdit(index, row) {
  config.isEdit.value = true
  config.recentTask.value = index
  config.taskForm = tableData.value.at(index)
  config.taskFormVisible.value = true
}

function handleDelete(index, row) {
  let task = tableData.value.splice(index, 1)[0]
  sendSignal.delete(task.id)
  tableDataUpdateLocal()
}

function handleCourseDelete(index, row) {
  let course = courseData.value.splice(index, 1)[0]
  sendSignal.courseDelete(course.id)
  courseDataUpdateLocal()
}

function presentInfo(index, row) {
  let course = courseData.value[index]
  config.courseForm = course
  config.courseInfoVisible.value = true
}

let defaultTask = newTask(
  0,
  0,
  '2024-10-10',
  'Software Engineering Class',
  '13:00:00',
  '1st Presentation'
)

// Initialize local data
let taskTable = tableDataFetchLocal()
let courseTable = courseDataFetchLocal()
let tableData = ref(taskTable)
let courseData = ref(courseTable)
</script>

<style scoped>
.el-button .custom-loading .circular {
  margin-right: 6px;
  width: 18px;
  height: 18px;
  animation: loading-rotate 2s linear infinite;
}

.el-button .custom-loading .circular .path {
  animation: loading-dash 1.5s ease-in-out infinite;
  stroke-dasharray: 90, 150;
  stroke-dashoffset: 0;
  stroke-width: 2;
  stroke: var(--el-button-text-color);
  stroke-linecap: round;
}
</style>
