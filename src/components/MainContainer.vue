<script setup>
import SideBar from './SideBar.vue'
import PageHeader from './PageHeader.vue';

let header_height_vh = 10;
let menu_width_vw = 15;
let menu_height_vh = 100 - header_height_vh
let main_width_vw = 100 - menu_width_vw
</script>

<template>
  <div id="main_container">
    <el-container :style="{ width: `100vw`, height: `${header_height_vh}vh` }">
      <el-header style="width: 100%; height: 100%;" class="background">
        <PageHeader />
      </el-header>
    </el-container>
    <el-container :style="{ width: `100vw`, height: `${menu_height_vh}vh` }">
      <el-aside :style="{ width: `${menu_width_vw}vw`, height: `100%` }" class="background">
        <SideBar />
      </el-aside>
      <el-main :style="{ width: `${main_width_vw}vw`, height: `100%` }" class="background">
        <router-view></router-view>
      </el-main>
    </el-container>
  </div>
</template>

<style>
#main_container {
  font-family: Arial, Helvetica, sans-serif;
  font-size: 14px;
  color: #2c3e50;
  text-align: center;
  position: absolute;
  left: 0;
  top: 0;
}

.background {
  background-color: white;
  position: relative;
  /* border: 0.5px solid black; */
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}
</style>
