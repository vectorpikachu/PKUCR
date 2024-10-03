<template>
  <el-scrollbar>
    <el-table :data="tableData" height="600">
      <el-table-column prop="date" label="Date" width="140" />
      <el-table-column prop="task" label="Task" width="120" />
      <el-table-column prop="memo" label="Memo" />
    </el-table>
  </el-scrollbar>
</template>

<script lang="ts" setup>
import { ref } from 'vue'

function new_task(date, task, memo) {
  return {
    date: date,
    task: task,
    memo: memo
  }
}

let default_task = new_task('2024-10-10', 'Software Engineering Class', '1st Presentation')
let task_table = ref(Array.from({ length: 20 }))
task_table.value[0] = default_task
let tableData = task_table
</script>
