<template>
    <div class="list-page">
        <h1>课程列表</h1>
        <el-table :data="objects" style="width: 100%">
            <el-table-column prop="course_id" label="课程号" width="180" /> <!-- 选课网的课程号 -->
            <el-table-column prop="name" label="课程名" width="200" />
            <el-table-column prop="category" label="类别" width="120" />
            <!-- <el-table-column prop="rating" label="评价" width="100" />
            <el-table-column prop="test" label="试题" />
            <el-table-column prop="resource" label="资料" /> -->

            <!-- 操作列 -->
            <el-table-column label="操作" width="180">
                <template v-slot="scope">
                    <el-button size="small" @click="viewDetails(scope.row)">详情</el-button>
                    <el-button size="small" type="primary" @click="editItem(scope.row)">编辑</el-button>
                </template>
            </el-table-column>
        </el-table>

        <!-- 详情对话框 -->
        <el-dialog v-model="dialogVisible" title="课程详情" @close="resetDialog">
            <div v-if="selectedObject">
                <p><strong>课程号:</strong> {{ selectedObject.course_id }}</p>
                <p><strong>课程名:</strong> {{ selectedObject.name }}</p>
                <p><strong>类别:</strong> {{ selectedObject.category }}</p>
                <p><strong>评价:</strong> {{ selectedObject.rating }}</p>
                <p><strong>试题:</strong> {{ selectedObject.test }}</p>
                <p><strong>资料:</strong> {{ selectedObject.resource }}</p>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">关闭</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue'

// 模拟数据对象数组
const objects = ref([
    { course_id: 1, name: 'class 1' },
    { course_id: 2, name: 'class 2' },
    { course_id: 3, name: 'class 3' },
])

const dialogVisible = ref(false)
const selectedObject = ref(null)

// 查看详情的处理函数
const viewDetails = (row) => {
  selectedObject.value = row
  dialogVisible.value = true
}

// 重置对话框状态
const resetDialog = () => {
  selectedObject.value = null
}

// 编辑项的处理函数
const editItem = (row) => {
    console.log('Edit item:', row)
    // 处理编辑逻辑
}

// 当页面加载时可以在这里调用获取数据的API
onMounted(() => {
    // 调用 API 获取数据后将结果赋值给 objects
})
</script>

<style scoped>
.list-page {
    padding: 20px;
}

h1 {
    margin-bottom: 20px;
}
</style>