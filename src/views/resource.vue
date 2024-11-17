<template>
    <div class="list-page">
        <!-- 固定在页面顶部的部分 -->
        <div class="header">
            <h1>课程列表</h1>
            <div class="actions">
                <el-input v-model="searchQuery" placeholder="搜索课程" prefix-icon="el-icon-search" style="width: 300px;"
                    @input="searchCourses" />
                <el-button type="primary" @click="openAddCourseDialog">添加新课程</el-button>
            </div>
        </div>

        <!-- 可滚动的课程表 -->
        <div class="table-container">
            <el-table :data="filteredObjects" v-loading="loading">
                <el-table-column prop="course_id" label="课程号" width="180" />
                <el-table-column prop="name" label="课程名" width="200" />
                <el-table-column prop="category" label="类别" width="120" />
                <el-table-column label="操作" width="180">
                    <template v-slot="scope">
                        <el-button size="small" @click="viewDetails(scope.row)">详情</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>

        <!-- 详情对话框 -->
        <el-dialog v-model="dialogVisible" title="课程详情" @close="resetDialog">
            <div v-if="selectedObject">
                <p><strong>课程号:</strong> {{ selectedObject.course_id }}</p>
                <p><strong>课程名:</strong> {{ selectedObject.name }}</p>
                <p><strong>类别:</strong> {{ selectedObject.category }}</p>

                <!-- Tab组件 -->
                <el-tabs v-model="activeTab" @tab-click="handleTabClick">
                    <el-tab-pane label="评价" name="comments">
                        <el-table :data="selectedObject.comments" style="width: 100%">
                            <el-table-column prop="user" label="用户" width="120" />
                            <el-table-column prop="content" label="评价内容" />
                        </el-table>
                        <el-form @submit.prevent="addComment">
                            <el-form-item>
                                <el-input v-model="newComment" placeholder="添加评论" />
                            </el-form-item>
                            <el-button type="primary" @click="addComment">提交评论</el-button>
                        </el-form>
                    </el-tab-pane>

                    <el-tab-pane label="资料" name="resources">
                        <el-table :data="selectedObject.resources" style="width: 100%">
                            <el-table-column prop="name" label="文件名" />
                            <el-table-column label="操作">
                                <template v-slot="scope">
                                    <el-button type="primary" @click="downloadResource(scope.row)">下载</el-button>
                                </template>
                            </el-table-column>
                        </el-table>
                        <!--这里后端接口-->
                        <el-upload action="`/api/upload/${selectedObject.course_id}`" :on-success="handleUploadSuccess" :on-error="handleUploadError"
                            show-file-list="false">
                            <el-button type="primary">上传资料</el-button>
                        </el-upload>
                    </el-tab-pane>
                </el-tabs>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">关闭</el-button>
            </span>
        </el-dialog>

        <!-- 添加新课程对话框 -->
        <el-dialog v-model="addCourseDialogVisible" title="添加新课程" @close="resetAddCourseDialog">
            <el-form :model="newCourse" ref="form" label-width="100px">
                <el-form-item label="课程号">
                    <el-input v-model="newCourse.course_id" type="number" placeholder="请输入课程号" />
                </el-form-item>
                <el-form-item label="课程名">
                    <el-input v-model="newCourse.name" placeholder="请输入课程名" />
                </el-form-item>
                <el-form-item label="类别">
                    <el-input v-model="newCourse.category" placeholder="请输入课程类别" />
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="addCourseDialogVisible = false">取消</el-button>
                <el-button type="primary" @click="addCourse">添加课程</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script lang="ts" setup>
import { ref, reactive, onMounted } from 'vue';
import axios from 'axios';

// 数据对象数组
const objects = ref([
    {
        course_id: 1,
        name: 'Class 1',
        category: 'Math',
        comments: [{ user: 'Alice', content: 'Great course!' }],
        // 资料形式是: name 和 url
        resources: [{ name: 'Lecture1.pdf', url: '/files/lecture1.pdf' }],
    },
    {
        course_id: 2,
        name: 'Class 2',
        category: 'Science',
        comments: [],
        resources: [],
    },
]);

const dialogVisible = ref(false);
const addCourseDialogVisible = ref(false);
const selectedObject = ref(null);
const activeTab = ref('comments'); // 用来控制默认打开的 tab
const newComment = ref(''); // 用于存储新评论内容
const searchQuery = ref(''); // 用于存储搜索关键词
const filteredObjects = ref(objects.value); // 用于存储筛选后的课程数据
const newCourse = reactive({ course_id: '', name: '', category: '' }); // 用于存储新课程数据

const loading = ref(false);

// 页面初始化时拿全部数据
const fetchCourses = async () => {
    loading.value = true;
    try {
        const response = await axios.get('/courses'); // 后端api
        objects.value = response.data;
    } catch (error) {
        console.error('获取数据失败:', error);
    } finally {
        loading.value = false;
    }
};

// 页面加载前会调用
onMounted(() => {
    fetchCourses();
});

// 查看详情的处理函数
const viewDetails = (row) => {
    selectedObject.value = row; // 加载选中的课程数据
    dialogVisible.value = true;
};

// 重置对话框状态
const resetDialog = () => {
    selectedObject.value = null;
    newComment.value = '';
};

// 添加评论的处理函数
const addComment = () => {
    if (newComment.value.trim()) {
        selectedObject.value.comments.push({
            user: 'Current User', // 替换为实际用户信息
            content: newComment.value,
        });
        newComment.value = ''; // 清空输入框
    }
};

// 下载资料
const downloadResource = (resource) => {
    const link = document.createElement('a');
    link.href = resource.url; // 文件 URL
    link.download = resource.name; // 文件名
    link.click();
};

// 上传资料成功的回调
const handleUploadSuccess = (response, file) => {
    selectedObject.value.resources.push({ name: file.name, url: response.url });
};

// 上传资料失败的回调
const handleUploadError = (error) => {
    console.error('Upload failed:', error);
};

// Tab点击事件
const handleTabClick = (tab) => {
    console.log(tab.name); // 打印当前 tab 的名称
};

// 搜索课程的处理函数
const searchCourses = () => {
    if (searchQuery.value.trim()) {
        filteredObjects.value = objects.value.filter(course =>
            course.name.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
            course.course_id.toString().includes(searchQuery.value)
        );
    } else {
        filteredObjects.value = objects.value;
    }
};

// 打开添加新课程对话框
const openAddCourseDialog = () => {
    addCourseDialogVisible.value = true;
};

// 重置添加新课程对话框
const resetAddCourseDialog = () => {
    newCourse.course_id = '';
    newCourse.name = '';
    newCourse.category = '';
};

// 添加新课程的处理函数
const addCourse = () => {
    if (newCourse.course_id && newCourse.name && newCourse.category) {
        objects.value.push({
            course_id: Number(newCourse.course_id), // 转换为数字
            name: newCourse.name,
            category: newCourse.category,
            comments: [],
            resources: [],
        });
        addCourseDialogVisible.value = false; // 关闭对话框
        resetAddCourseDialog(); // 重置输入框
    }
};
</script>

<style scoped>
.list-page {
    display: flex;
    flex-direction: column;
    /* 子元素按垂直方向排列 */
    height: 100vh;
    /* 占满整个视口高度 */
    width: 90vw;
}

.header {
    flex: 1 0 25%;
    /* 占据1/4高度 */
    background-color: #f7f7f7;
    display: flex;
    flex-direction: column;
    justify-content: center;
    /* 垂直居中内容 */
    padding: 20px;
}

.actions {
    display: flex;
    gap: 10px;
    /* 子元素之间的间距 */
    align-items: center;
    /* 垂直方向居中 */
}

.table-container {
    flex: 1 0 75%;
    /* 占据3/4高度 */
    overflow: auto;
    /* 内容超出时允许滚动 */
    padding: 20px;
}


.el-dialog__body {
    max-height: 400px;
    overflow-y: auto;
}

.el-tabs__header {
    background-color: #f5f5f5;
}

.el-dialog {
    z-index: 9999;
    /* 确保弹出框不干扰页面其他布局 */
}

/* 为了确保内容不被对话框覆盖，设置适当的定位 */
.el-dialog__wrapper {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
}
</style>