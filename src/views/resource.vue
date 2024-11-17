<template>
    <div class="list-page">
        <h1>课程列表</h1>
        <el-table :data="objects" style="width: 100%">
            <el-table-column prop="course_id" label="课程号" width="180" />
            <el-table-column prop="name" label="课程名" width="200" />
            <el-table-column prop="category" label="类别" width="120" />
            <el-table-column label="操作" width="180">
                <template v-slot="scope">
                    <el-button size="small" @click="viewDetails(scope.row)">详情</el-button>
                </template>
            </el-table-column>
        </el-table>

        <!-- 详情对话框 -->
        <el-dialog v-model="dialogVisible" title="课程详情" @close="resetDialog">
            <div v-if="selectedObject">
                <p><strong>课程号:</strong> {{ selectedObject.course_id }}</p>
                <p><strong>课程名:</strong> {{ selectedObject.name }}</p>
                <p><strong>类别:</strong> {{ selectedObject.category }}</p>

                <!-- Tab组件 -->
                <el-tabs v-model="activeTab" @tab-click="handleTabClick">
                    <el-tab-pane label="评价" name="comments">
                        <!-- 评价展示与添加 -->
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
                        <!-- 资料展示、上传与下载 -->
                        <el-table :data="selectedObject.resources" style="width: 100%">
                            <el-table-column prop="name" label="文件名" />
                            <el-table-column label="操作">
                                <template v-slot="scope">
                                    <el-button type="primary" @click="downloadResource(scope.row)">下载</el-button>
                                </template>
                            </el-table-column>
                        </el-table>
                        <el-upload
                            action="/api/upload"
                            :on-success="handleUploadSuccess"
                            :on-error="handleUploadError"
                            show-file-list="false"
                        >
                            <el-button type="primary">上传资料</el-button>
                        </el-upload>
                    </el-tab-pane>
                </el-tabs>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">关闭</el-button>
            </span>
        </el-dialog>
    </div>
</template>





<script lang="ts" setup>
import { ref, reactive } from 'vue';

// 模拟数据对象数组
const objects = ref([
    {
        course_id: 1,
        name: 'Class 1',
        category: 'Math',
        comments: [{ user: 'Alice', content: 'Great course!' }],
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
const selectedObject = ref(null);
const activeTab = ref('comments'); // 用来控制默认打开的 tab
const newComment = ref(''); // 用于存储新评论内容

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
</script>



<style scoped>
.list-page {
    padding: 20px;
}

h1 {
    margin-bottom: 20px;
}


.el-dialog__body {
    max-height: 400px;
    overflow-y: auto;
}

.el-tabs__header {
    background-color: #f5f5f5;
}

</style>


