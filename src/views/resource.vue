<template>
    <div class="list-page">
        <!-- 固定在页面顶部的部分 -->
        <div class="header">
            <h1>课程列表</h1>
            <div class="actions">
                <el-input v-model="searchQuery" placeholder="搜索课程" prefix-icon="el-icon-search" style="width: 300px"/>
                <el-button type="primary" @click="openAddCourseDialog">添加新课程</el-button>
                <el-button type="warning" @click="handleExit" class="logout-button">返回主页</el-button>
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
        <el-dialog v-model="dialogVisible" title="课程详情" @close="resetDialog" width="80%"
            style="max-height: 70vh; overflow-y: auto;">
            <div v-if="selectedObject">
                <p><strong>课程号:</strong> {{ selectedObject.course_id }}</p>
                <p><strong>课程名:</strong> {{ selectedObject.name }}</p>
                <p><strong>类别:</strong> {{ selectedObject.category }}</p>

                <!-- Tab组件 -->
                <el-tabs v-model="activeTab" @tab-click="handleTabClick">
                    <el-tab-pane label="评价" name="comments">
                        <el-table :data="selectedObject.comments" style="width: 100%">
                            <el-table-column prop="user" label="用户" width="120" />
                            <el-table-column prop="comment" label="评价内容" />
                            <el-table-column label="操作">
                                <template v-slot="scope">
                                    <el-button type="danger" @click="deleteComment(scope.row)">删除</el-button>
                                </template>
                            </el-table-column>
                        </el-table>
                        <el-form @submit.prevent="addComment">
                            <el-form-item>
                                <el-input v-model="newComment" placeholder="添加评论" />
                            </el-form-item>
                            <el-button type="primary" @click="addComment">提交评论</el-button>
                        </el-form>
                    </el-tab-pane>

                    <el-tab-pane label="资料" name="resources">
                        <el-table :data="selectedObject.materials" style="width: 100%">
                            <el-table-column prop="filename" label="文件名" />
                            <el-table-column label="操作">
                                <template v-slot="scope">
                                    <el-button type="primary" @click="downloadResource(scope.row)">下载</el-button>
                                    <el-button type="danger" @click="deleteResource(scope.row)">删除</el-button>
                                </template>
                            </el-table-column>
                        </el-table>
                        <!--这里后端接口-->
                        <!-- 上传文件按钮 -->
                        <input
                          type="file"
                          ref="fileInput"
                          style="display: none"
                          @change="handleFileChange"
                        />
                        <el-button type="primary" icon="el-icon-upload2" @click="triggerFileInput">
                            上传文件
                        </el-button>
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
import { ref, reactive, onMounted, computed } from 'vue'
import axios from '../axios'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { storage } from '@/store/storage'

const router = useRouter()

// 课程元信息
const objects = ref()

// 课程详情
const courseDetails = new Map()
courseDetails.set('1', {
    comments: [
        { id: 1, user: 'Alice', comment: 'Great course!' },
        { id: 2, user: 'Bob', comment: 'Very informative.' }
    ],
    materials: [
        { id: 1, filename: 'Lecture1.pdf' },
        { id: 2, filename: 'Lecture2.pdf' }
    ]
})

const courseMetaInfo = ref([])

const dialogVisible = ref(false)
const addCourseDialogVisible = ref(false)
const selectedObject = ref(null)
const activeTab = ref('comments') // 用来控制默认打开的 tab
const newComment = ref('') // 用于存储新评论内容
const searchQuery = ref('') // 用于存储搜索关键词
// const filteredObjects = ref(objects.value) // 用于存储筛选后的课程数据
const filteredObjects = computed(() => {
      if (searchQuery.value.trim()) {
        return objects.value.filter(course =>
          course.name.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
          course.course_id.toString().includes(searchQuery.value)
        );
      } else {
        return objects.value;
      }
    });
const newCourse = reactive({ course_id: '', name: '', category: '' }) // 用于存储新课程数据

const loading = ref(false)

// 页面初始化时拿全部数据
const fetchCourses = async () => {
    loading.value = true
    try {
        const response = await axios.get('/api/resource') // 后端api
        const data = response.data
        const formattedData = Object.keys(data).map(key => {
            return {
                course_id: key,
                ...data[key]
            }
        })

        // 将格式化后的数据赋值给 objects
        courseMetaInfo.value = formattedData
        objects.value = []
        courseDetails.clear()
        objects.value.push(...formattedData)
        // searchCourses()
    } catch (error) {
        console.error('获取数据失败:', error)
        objects.value = [
            {
                course_id: '1',
                name: 'Class 1',
                category: '专业课',
            },
            {
                course_id: '2',
                name: 'Class 2',
                category: '政治课',
            },
        ]
        console.log(objects.value)
        // searchCourses()
        alert("获取数据失败: " + error.message)
    } finally {
        loading.value = false
    }
}

// 页面加载前会调用
onMounted(() => {
    fetchCourses()
})

// 查看详情的处理函数
const viewDetails = async (row) => {
    const courseInfo = courseDetails.get(row.course_id)

    if (courseInfo) {
        selectedObject.value = {
            ...row,
            comments: courseInfo.comments,
            materials: courseInfo.materials
        }
        dialogVisible.value = true
    } else {
        selectedObject.value = row
        console.log('Course not found!')
        // 本地没有, 请求后端
        try {
            const response = await axios.get(`/api/resource/${row.course_id}`)
            const data = response.data

            courseDetails.set(row.course_id, {
                comments: data.comments,
                materials: data.materials
            })

            selectedObject.value = {
                ...row,
                comments: data.comments,
                materials: data.materials
            }
            dialogVisible.value = true
        }
        catch (error) {
            console.error('Error fetching course details:', error)
            alert("获取课程详情失败: " + error.message)
        }
    }

}

// 重置对话框状态
const resetDialog = () => {
    selectedObject.value = null
    newComment.value = ''
}

// 上传文件：触发文件选择框
const triggerFileInput = () => {
    // 触发隐藏的文件选择框
    const input = document.querySelector('input[type="file"]') as HTMLInputElement
    if (input) {
        input.click()
    }
}

// 处理文件选择
const handleFileChange = async (event) => {
    const file = event.target.files[0]
    if (file && selectedObject.value) {
        const formData = new FormData()
        formData.append('file', file)
        formData.append('fileName', file.name)

        try {
            // 发送上传请求
            const courseID = selectedObject.value.course_id
            const response = await axios.post(`/api/resource/material/${courseID}`, formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                    // 'Authorization': `Bearer ${storage.get('user').token}`
                }
            })

            const data = response.data
            if (true) {
                ElMessage.success('文件上传成功！')
                // 更新文件列表
                selectedObject.value.materials.push({ filename: data.filename, id: data.id })
            } else {
                ElMessage.error('文件上传失败')
            }
        } catch (error) {
            ElMessage.error('上传失败，请重试')
        }
    }
}

// 添加评论的处理函数
const addComment = async () => {
    if (newComment.value.trim()) {
        // 上传后端
        const commentData = {
            user: storage.getItem('username'),  // TODO: 用户名, 阶段三先不管了
            comment: newComment.value.trim()
        }
        try {
            // 发起 POST 请求
            const response = await axios.post(`/api/resource/comment/${selectedObject.value.course_id}`, commentData)

            // 如果请求成功，更新本地评论数据
            if (response.status === 200) {
                selectedObject.value.comments.push({
                    id: response.data.id,
                    user: commentData.user,
                    comment: commentData.comment
                })

                const courseId = selectedObject.value.course_id
                if (courseDetails.has(courseId)) {
                    courseDetails.get(courseId).comments.push({
                        user: commentData.user,
                        comment: commentData.comment
                    })
                }

                // 清空输入框
                newComment.value = ''
            } else {
                console.error('Failed to post comment:', response.status)
                alert('上传评论失败, 服务端错误: ' + response.status)
            }
        } catch (error) {
            console.error('Error posting comment:', error)
            alert('上传评论失败: ' + error.message)
        }
    }
}

const deleteComment = async (comment) => {
    try {
        const response = await axios.delete(`/api/resource/comment/${comment.id}`)

        if (response.status === 200) {
            const course = courseDetails.get(selectedObject.value.course_id)
            if (course) {
                const index = course.comments.findIndex(c => c.id === comment.id)
                if (index !== -1) {
                    course.comments.splice(index, 1)
                }
            }
            ElMessage.info('删除成功')
        } else {
            alert('删除评论失败, 服务端错误: ' + response.status)
        }
    } catch (error) {
        alert('删除评论失败 ' + error.message)
    }
}

// 下载资料
const downloadResource = async (resource) => {
    try {
        const courseID = selectedObject.value.course_id; // 获取课程 ID
        const resourceID = resource.id; // 获取文件的 ID
        const response = await axios.get(`/api/resource/material/${courseID}/${resourceID}`, {
            responseType: 'blob', // 重要，返回二进制数据
        })

        const link = document.createElement('a')
        const blob = new Blob([response.data], { type: response.headers['content-type'] })
        const url = window.URL.createObjectURL(blob)
        link.href = url
        link.download = resource.name + "/" + resource.id
        link.click()
        window.URL.revokeObjectURL(url)
        ElMessage.success('文件下载成功！');
    } catch (error) {
        if (error.response) {
            const { status, data } = error.response;
            if (status === 400) {
                ElMessage.error('文件未在数据库中找到');
            } else if (status === 404) {
                ElMessage.error('文件未在服务器目录中找到');
            } else {
                ElMessage.error('下载失败，请重试');
            }
        } else {
            ElMessage.error(response.headers['content-disposition'])
            ElMessage.error('下载失败，请检查网络连接');
        }
        console.error('Error downloading resource:', error);
    }
}

// 删除资料
const deleteResource = async (resource) => {
    try {
        const response = await axios.delete(`/api/resource/material/${resource.id}`)

        if (response.status === 200) {
            const course = courseDetails.get(selectedObject.value.course_id)
            if (course) {
                // 找到 materials 数组并删除相应的 material
                const index = course.materials.findIndex(material => material.id === resource.id)
                if (index !== -1) {
                    course.materials.splice(index, 1)  // 删除指定的 material
                }
            }
            ElMessage.info('删除成功')
        } else {
            alert('删除资料失败, 服务端错误: ' + response.status)
        }
    } catch (error) {
        if (error.response) {
            const { status, data } = error.response;

            // 根据不同的错误状态码进行处理
            if (status === 400) {
                if (data === "Permission denied, users can only delete files they uploaded themselves.") {
                    ElMessage.error('无权限删除该文件');
                } else if (data === "Material not found in the database") {
                    ElMessage.error('文件未在数据库中找到');
                }
            } else if (status === 404) {
                ElMessage.error('文件未在服务器目录中找到');
            } else if (status === 500) {
                ElMessage.error('文件删除失败，请稍后重试');
            } else {
                ElMessage.error('删除失败，请重试');
            }
        } else {
            ElMessage.error('删除失败，请检查网络连接');
        }

        console.error('Error deleting resource:', error);
    }
}

// 上传资料成功的回调
const handleUploadSuccess = (response, file) => {
    selectedObject.value.materials.push({ name: response.filename, url: response.url })
    const courseId = selectedObject.value.course_id
    if (courseDetails.has(courseId)) {
        courseDetails.get(courseId).materials.push({
            filename: response.filename,
            url: response.url
        })
    }
    console.log('Upload succeed: ', response)
    alert('Upload succeed: ' + response.filename)
}

// 上传资料失败的回调
const handleUploadError = (error) => {
    console.error('Upload failed:', error)
    alert('Upload failed: ' + error.message)
}

// Tab点击事件
const handleTabClick = (tab) => {
    console.log(tab.name) // 打印当前 tab 的名称
}

// 搜索课程的处理函数
// const searchCourses = () => {
//     if (searchQuery.value.trim()) {
//         filteredObjects.value = objects.value.filter(course =>
//             course.name.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
//             course.course_id.toString().includes(searchQuery.value)
//         )
//     } else {
//         filteredObjects.value = objects.value
//     }
// }

// 打开添加新课程对话框
const openAddCourseDialog = () => {
    addCourseDialogVisible.value = true
}

// 重置添加新课程对话框
const resetAddCourseDialog = () => {
    newCourse.course_id = ''
    newCourse.name = ''
    newCourse.category = ''
}

// 添加新课程的处理函数
const addCourse = async () => {
    if (newCourse.course_id && newCourse.name && newCourse.category) {
        try {
            // 发起 POST 请求
            const courseData = {
                course_id: newCourse.course_id,
                name: newCourse.name,
                category: newCourse.category
            }
            const response = await axios.post(`/api/resource/${newCourse.course_id}`, courseData)

            // 如果请求成功，更新本地评论数据
            if (response.status === 200) {
                objects.value.push(courseData)
                addCourseDialogVisible.value = false
                resetAddCourseDialog() // 重置输入框
            } else {
                console.error('Failed to post course:', response.status)
                alert('添加课程失败, 服务端错误: ' + response.status)
            }
        } catch (error) {
            console.error('Error posting course:', error)
            alert('添加课程失败: ' + error.message)
        }
    }
}

const handleExit = () => {
    router.push('/')
}
</script>

<style scoped>
.list-page {
    background-color: white;
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