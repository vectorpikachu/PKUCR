<template>
  <el-row>
    <el-col>
      <el-scrollbar>
        <el-table :data="tableData" height="600">
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
              <el-button type="primary" :loading="config.taskFormVisible.value"
                @click="config.taskFormVisible.value = true">
                <template #loading>
                  <div class="custom-loading">
                    <svg class="circular" viewBox="-10, -10, 50, 50">
                      <path class="path" d="
                          M 30 15
                          L 28 17
                          M 25.61 25.61
                          A 15 15, 0, 0, 1, 15 30
                          A 15 15, 0, 1, 1, 27.99 7.5
                          L 15 15
                          " style="stroke-width: 4px; fill: rgba(0, 0, 0, 0)" />
                    </svg>
                  </div>
                </template>
                Add Task
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
      </el-scrollbar>
    </el-col>
  </el-row>

  <!-- Dialog of adding task -->
  <el-dialog v-model="config.taskFormVisible.value" title="Add New Task" width="500">
    <el-form :model="config.taskForm" label-width="auto" style="max-width: 600px">
      <el-form-item label="Task name">
        <el-input v-model="config.taskForm.name" />
      </el-form-item>
      <el-form-item label="Task date">
        <el-col :span="11">
          <el-date-picker v-model="config.taskForm.date" type="date" placeholder="Pick a date" style="width: 100%"
            value-format="YYYY-MM-DD" />
        </el-col>
        <el-col :span="2" class="text-center">
          <span class="text-gray-500">-</span>
        </el-col>
        <el-col :span="11">
          <el-time-picker v-model="config.taskForm.time" placeholder="Pick a time" style="width: 100%"
            value-format="HH:mm:ss" />
        </el-col>
      </el-form-item>
      <el-form-item label="Task Priority">
        <el-slider v-model="config.taskForm.priority" :step="10" :format-tooltip="formatTooltip" show-stops />
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
</template>

<script lang="ts" setup>
import { ref, reactive } from 'vue'
import { Sort, Loading, Timer, SortDown, SortUp } from '@element-plus/icons-vue'
import axios from '../axios'

// Backend axios address
const AXIOS_ADDRESS = {
  INSERT: '/task/insert',
  UPDATE: '/task/update',
  SELECT: '/task/select',
  DELETE: '/task/delete'
}

// Message exchanging functinos with server
const sendSignal = {
  put: (t) => {
    return axios
      .put(AXIOS_ADDRESS.UPDATE, {
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
    return await axios.get(id)
  },
  delete: (id) => {
    axios.delete(id).then((res) => {
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

// Status buffer for functions
let config = {
  taskFormVisible: ref(false),
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

function compTask(lhs, rhs) {
  for (const comp of config.compTask.value) {
    let res = comp[1](lhs, rhs)
    if (res !== 0)
      if (comp[0](lhs, rhs) >= 0) return res
      else return -res
  }
  return 0
}

interface Task {
  id: number
  priority: number
  name: string
  date: string
  time: string
  memo: string
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

// Update data into localstorage
function tableDataUpdateLocal() {
  let data = JSON.stringify(tableData.value)
  localStorage.setItem('task', data)
}

// Fetch initial data from server
function tableDataFetchLocal() {
  return JSON.parse(localStorage.getItem('task'))
}

// Submite task form to server and update local data
function taskFormSubmit() {
  config.taskFormVisible.value = false
  if (config.isEdit.value) {
    config.isEdit.value = false
    let id = tableData.value[config.recentTask.value].id
    config.taskForm.id = id
    let response = sendSignal.put(config.taskForm)
    response.then((res) => {
      if (res.data === 'update success') {
        tableData.value[config.recentTask.value] = config.taskForm
        tableDataUpdateLocal()
      }
    })
  } else {
    let response = sendSignal.post(config.taskForm)
    response.then((res) => {
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
}

function taskFormCancel() {
  config.taskFormVisible.value = false
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
let tableData = ref(taskTable)
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
