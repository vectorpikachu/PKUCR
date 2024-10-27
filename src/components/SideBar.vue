<template>
    <el-menu default-active="2" class="el-menu-vertical">

      <router-link to="/schedule">
        <el-menu-item index="1">
          <el-icon><icon-menu /></el-icon>
          Schedule
        </el-menu-item>
      </router-link>

      <router-link to="/taskTable">
        <el-menu-item index="2">
          <el-icon><icon-menu /></el-icon>
          Task
        </el-menu-item>
      </router-link>

      <router-link to="/calendar">
        <el-menu-item index="3">
          <el-icon><icon-menu /></el-icon>
          Calendar
        </el-menu-item>
      </router-link>

      <router-link to="/resource">
        <el-menu-item index="4">
          <el-icon><icon-menu /></el-icon>
          Resources
        </el-menu-item>
      </router-link>

      <router-link to="/setting">
        <el-menu-item index="5">
          <el-icon>
            <setting />
          </el-icon>
          Setting
        </el-menu-item>
      </router-link>

    </el-menu>
</template>

<script lang="ts" setup>
import { Document, Menu as IconMenu, Location, Setting } from '@element-plus/icons-vue'
</script>

<style>
.el-menu-vertical {
  width: 100%;
  height: 100%;
}
</style>
