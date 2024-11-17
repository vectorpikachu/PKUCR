<template>
  <div>
    <el-table
      :data="processedSchedule"
      v-loading="loading"
      row-class-name="cellRow"
      class="schedule"
    >
      <el-table-column prop="time" label="时间" width="120"></el-table-column>

      <el-table-column cell-class-name="cell" label="星期一">
        <template #default="scope">
          <span @click="openDetailsDialog(scope.row, 'monday')">
            {{ scope.row.monday }}
          </span>
        </template>
      </el-table-column>

      <el-table-column cell-class-name="cell" label="星期二">
        <template #default="scope">
          <span @click="openDetailsDialog(scope.row, 'tuesday')">
            {{ scope.row.tuesday }}
          </span>
        </template>
      </el-table-column>

      <el-table-column cell-class-name="cell" label="星期三">
        <template #default="scope">
          <span @click="openDetailsDialog(scope.row, 'wednesday')">
            {{ scope.row.wednesday }}
          </span>
        </template>
      </el-table-column>

      <el-table-column cell-class-name="cell" label="星期四">
        <template #default="scope">
          <span @click="openDetailsDialog(scope.row, 'thursday')">
            {{ scope.row.thursday }}
          </span>
        </template>
      </el-table-column>

      <el-table-column cell-class-name="cell" label="星期五">
        <template #default="scope">
          <span @click="openDetailsDialog(scope.row, 'friday')">
            {{ scope.row.friday }}
          </span>
        </template>
      </el-table-column>

      <el-table-column cell-class-name="cell" label="星期六">
        <template #default="scope">
          <span @click="openDetailsDialog(scope.row, 'saturday')">
            {{ scope.row.saturday }}
          </span>
        </template>
      </el-table-column>

      <el-table-column cell-class-name="cell" label="星期日">
        <template #default="scope">
          <span @click="openDetailsDialog(scope.row, 'sunday')">
            {{ scope.row.sunday }}
          </span>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="课程详情" width="500" v-model="detailsDialogVisible">
      <el-form :model="editForm" style="max-width: 600px">
        <el-form-item label="课程名称">
          <el-input v-model="editForm.subject" readonly></el-input>
        </el-form-item>
        <el-form-item label="附加信息">
          <el-input v-model="editForm.details" type="textarea" readonly></el-input>
        </el-form-item>
      </el-form>
      <template v-slot:footer>
        <span class="dialog-footer">
          <el-button @click="detailsDialogVisible = false">关闭</el-button>
          <el-button type="primary" @click="editCourse">编辑课程</el-button>
        </span>
      </template>
    </el-dialog>

    <el-dialog title="修改课程" v-model="editDialogVisible">
      <el-form :model="editForm">
        <el-form-item label="课程名称">
          <el-input v-model="editForm.subject"></el-input>
        </el-form-item>
        <el-form-item label="附加信息">
          <el-input v-model="editForm.details"></el-input>
        </el-form-item>
      </el-form>
      <template v-slot:footer>
        <span class="dialog-footer">
          <el-button @click="editDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="saveCourse">保存</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import axios from 'axios'

const time_sects = [
  '08:00 - 08:50',
  '09:00 - 09:50',
  '10:10 - 11:00',
  '11:10 - 12:00',
  '13:00 - 13:50',
  '14:00 - 14:50',
  '15:10 - 16:00',
  '16:10 - 17:00',
  '17:10 - 18:00',
  '18:40 - 19:30',
  '19:40 - 20:30',
  '20:40 - 21:30'
]
const maxCacheTime = 3600000

export default {
  data() {
    return {
      // default schedule
      schedule: [{ day: 'monday', time: '08:00 - 08:50', subject: '高等数学', details: 'url' }],
      processedSchedule: [],
      rowCount: time_sects.length,
      loading: true,
      detailsDialogVisible: false,
      editDialogVisible: false,
      editForm: {
        subject: '',
        details: '',
        day: '',
        time: ''
      },
      currentEditRow: null
    }
  },
  mounted() {
    this.loadScheduleData()
  },
  methods: {
    async loadScheduleData() {
      const cachedData = localStorage.getItem('scheduleData')
      const cachedTime = localStorage.getItem('scheduleDataTime')

      if (cachedData && cachedTime && Date.now() - cachedTime < maxCacheTime) {
        this.schedule = JSON.parse(cachedData)
        this.processScheduleData()
        this.loading = false
      } else {
        this.processScheduleData()
        await this.fetchScheduleData()
      }
    },
    async fetchScheduleData() {
      try {
        // fill the database url here
        const response = await axios.get('https://database.url.to.be.finished')
        this.schedule = response.data
        this.processScheduleData()

        localStorage.setItem('scheduleData', JSON.stringify(this.schedule))
        localStorage.setItem('scheduleDataTime', Date.now().toString())
      } catch (error) {
        console.error('Failed to fetch the data:', error)
      } finally {
        this.loading = false
      }
    },
    processScheduleData() {
      const timeSlots = {}

      for (let time of time_sects) {
        timeSlots[time] = {
          time: time,
          monday: '',
          mondayDetails: '',
          tuesday: '',
          tuesdayDetails: '',
          wednesday: '',
          wednesdayDetails: '',
          thursday: '',
          thursdayDetails: '',
          friday: '',
          fridayDetails: '',
          saturday: '',
          saturdayDetails: '',
          sunday: '',
          sundayDetails: ''
        }
      }

      this.schedule.forEach((course) => {
        timeSlots[course.time][course.day] = course.subject
        timeSlots[course.time][`${course.day}Details`] = course.details
      })

      this.processedSchedule = Object.values(timeSlots)
    },
    openDetailsDialog(row, day) {
      this.editForm.subject = row[day]
      this.editForm.details = row[`${day}Details`]
      this.editForm.day = day
      this.editForm.time = row.time
      this.detailsDialogVisible = true
    },
    editCourse() {
      this.detailsDialogVisible = false
      this.editDialogVisible = true
    },
    saveCourse() {
      this.currentEditRow[this.editForm.day] = this.editForm.subject
      this.currentEditRow[`${this.editForm.day}Details`] = this.editForm.details

      this.editDialogVisible = false

      this.updateBackend()
    },
    async updateBackend() {
      try {
        const updatedCourse = {
          time: this.editForm.time,
          day: this.editForm.day,
          subject: this.editForm.subject,
          details: this.editForm.details
        }

        // cookie version to be implemented
        await axios.put('https://private.database.url.to.be.extracted.from.cookies', updatedCourse)

        localStorage.removeItem('scheduleData')
        localStorage.removeItem('scheduleDataTime')
      } catch (error) {
        console.error('Failed to update the information:', error)
      }
    }
  }
}
</script>
