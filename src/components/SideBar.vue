<template>
  <el-menu class="el-menu-vertical" :default-active="this.$route.path" router>

    <router-link to="/home">
      <el-menu-item index="/home">
        <el-icon><icon-menu /></el-icon>
        Home
      </el-menu-item>
    </router-link>

    <router-link to="/taskTable">
      <el-menu-item index="/taskTable">
        <el-icon><icon-menu /></el-icon>
        Task
      </el-menu-item>
    </router-link>

    <router-link to="/calendar">
      <el-menu-item index="/calendar">
        <el-icon><icon-menu /></el-icon>
        Calendar
      </el-menu-item>
    </router-link>

    <router-link to="/resource">
      <el-menu-item index="/resource">
        <el-icon><icon-menu /></el-icon>
        Resources
      </el-menu-item>
    </router-link>

    <router-link to="/setting">
      <el-menu-item index="/setting">
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
