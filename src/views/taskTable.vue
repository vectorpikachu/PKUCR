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
                  <div>task: {{ scope.row.task }}</div>
                  <div>memo: {{ scope.row.memo }}</div>
                </template>
                <template #reference>
                  <el-tag>{{ scope.row.task }}</el-tag>
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
                :loading="createTaskFormVisible"
                @click="createTaskFormVisible = true"
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

  <el-dialog v-model="createTaskFormVisible" title="Add New Task" width="500">
    <el-form :model="form" label-width="auto" style="max-width: 600px">
      <el-form-item label="Task name">
        <el-input v-model="form.task" />
      </el-form-item>
      <el-form-item label="Task date">
        <el-col :span="11">
          <el-date-picker
            v-model="form.date1"
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
            v-model="form.date2"
            placeholder="Pick a time"
            style="width: 100%"
            value-format="HH:mm:ss"
          />
        </el-col>
      </el-form-item>
      <el-form-item label="Task memo">
        <el-input v-model="form.memo" type="textarea" />
      </el-form-item>
      <el-form-item>
        <el-button @click="createTaskFormVisible = false">Cancel</el-button>
        <el-button type="primary" @click="onSubmit"> Confirm </el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script lang="ts" setup>
import { ref, reactive } from 'vue'
import { Timer } from '@element-plus/icons-vue'

let taskId = ref(0)
let createTaskFormVisible = ref(false)

function new_task(date, task, time, memo) {
  return {
    id: taskId.value++,
    date: date,
    task: task,
    time: time,
    memo: memo
  }
}

let form = reactive({
  task: '',
  date1: '',
  date2: '',
  memo: ''
})

function onSubmit() {
  createTaskFormVisible.value = false
  tableData.value.push(new_task(form.date1, form.task, form.date2, form.memo))
  form = reactive({
    task: '',
    date1: '',
    date2: '',
    memo: ''
  })
}

function handleEdit(index, row) {}

function handleDelete(index, row) {
  tableData.value.splice(index, 1)
}

let default_task = new_task(
  '2024-10-10',
  'Software Engineering Class',
  '13:00:00',
  '1st Presentation'
)
let task_table = ref(Array.from({ length: 1 }))
task_table.value[0] = default_task
let tableData = task_table
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
