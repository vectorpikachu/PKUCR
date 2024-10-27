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

        <!-- 编辑对话框 -->
        <el-dialog v-model="editDialogVisible" title="编辑课程" width="500">
            <el-form :model="editableObject">
                <el-form-item label="课程号" prop="course_id">
                    <el-input v-model="editableObject.course_id" />
                </el-form-item>
                <el-form-item label="课程名" prop="name">
                    <el-input v-model="editableObject.name" />
                </el-form-item>
                <el-form-item label="类别" prop="category">
                    <el-input v-model="editableObject.category" />
                </el-form-item>
                <el-form-item label="评价" prop="rating">
                    <el-input v-model="editableObject.rating" />
                </el-form-item>
                <el-form-item label="试题" prop="test">
                    <el-input v-model="editableObject.test" />
                </el-form-item>
                <el-form-item label="资料" prop="resource">
                    <el-input v-model="editableObject.resource" />
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editDialogVisible = false">取消</el-button>
                <el-button type="primary" @click="saveEdit">保存</el-button>
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
const editDialogVisible = ref(false)
const selectedObject = ref(null)
const editableObject = ref(null)


// 查看详情的处理函数
const viewDetails = (row) => {
    selectedObject.value = row
    dialogVisible.value = true
}

// 重置对话框状态
const resetDialog = () => {
    selectedObject.value = null
}

// 编辑按钮的处理函数
const editItem = (row) => {
  editableObject.value = { ...row } // 创建一个可编辑的副本
  editDialogVisible.value = true
}

const saveEdit = () => {
  // 在这里可以实现保存逻辑，比如更新数据
  const index = objects.value.findIndex(obj => obj.course_id === editableObject.value.id)
  if (index !== -1) {
    objects.value[index] = editableObject.value // 更新原始数据
  }
  editDialogVisible.value = false
}

// 页面初始化数据
onMounted(() => {
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