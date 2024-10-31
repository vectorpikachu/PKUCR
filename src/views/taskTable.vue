<template>
  <el-row>
    <el-col>
      <el-scrollbar>
        <el-table :data="tableData" height="600">
          <el-table-column label="Date" width="180">
            <template #default="scope">
              <div style="display: flex; align-items: center">
                <el-icon><timer /></el-icon>
                <span style="margin-left: 10px">{{ scope.row.date }}</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="Task" width="180">
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
          <el-table-column prop="time" label="Time" width="140" />
          <el-table-column prop="memo" label="Memo" />
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
        <el-button @click="config.taskFormVisible.value = false">Cancel</el-button>
        <el-button type="primary" @click="taskFormSubmit"> Confirm </el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script lang="ts" setup>
import { ref, reactive } from 'vue'
import { Timer } from '@element-plus/icons-vue'

const comp_task_priority = (lhs, rhs) => {
  return rhs.priority - lhs.priority
}

const comp_task_date = (lhs, rhs) => {
  if (lhs.date > rhs.date) return 1
  else if (lhs.date < rhs.date) return -1
  else return 0
}

const comp_task_time = (lhs, rhs) => {
  if (lhs.time > rhs.time) return 1
  else if (lhs.time < rhs.time) return -1
  else return 0
}

const comp_task_name = (lhs, rhs) => {
  if (lhs.name > rhs.name) return 1
  else if (lhs.name < rhs.name) return -1
  else return 0
}

const comp_task_none = (lhs, rhs) => {
  return 0
}

let config = {
  taskFormVisible: ref(false),
  isEdit: ref(false),
  recentTask: ref(0),
  taskForm: reactive({
    priority: 0,
    name: '',
    date: '',
    time: '',
    memo: ''
  }),
  compTask: [comp_task_priority, comp_task_date, comp_task_time]
}

const formatTooltip = (val: number) => {
  return val / 10
}

function new_task(priority, date, name, time, memo) {
  return {
    priority: priority,
    date: date,
    name: name,
    time: time,
    memo: memo
  }
}

function task_from(form) {
  return {
    priority: form.priority,
    date: form.date,
    name: form.name,
    time: form.time,
    memo: form.memo
  }
}

function comp_task(lhs, rhs) {
  for (const comp of config.compTask) {
    let res = comp(lhs, rhs)
    if (res != 0) return res
  }
  return 0
}

interface Task {
  priority: number
  name: string
  date: string
  time: string
  memo: string
}

function taskFormSubmit() {
  config.taskFormVisible.value = false
  if (config.isEdit.value) {
    config.isEdit.value = false
    tableData.value[config.recentTask.value] = config.taskForm
  } else {
    tableData.value.push(task_from(config.taskForm))
  }
  tableData.value.sort(comp_task)
  config.taskForm = reactive({
    priority: 0,
    name: '',
    date: '',
    time: '',
    memo: ''
  })
}

function handleEdit(index, row) {
  config.isEdit.value = true
  config.recentTask.value = index
  config.taskForm = tableData.value.at(index)
  config.taskFormVisible.value = true
}

function handleDelete(index, row) {
  tableData.value.splice(index, 1)
}

let default_task = new_task(
  0,
  '2024-10-10',
  'Software Engineering Class',
  '13:00:00',
  '1st Presentation'
)
let task_table: Task[] = [default_task]
let tableData = ref(task_table)
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
