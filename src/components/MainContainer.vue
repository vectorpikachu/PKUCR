<script setup>
import SideBar from './SideBar.vue';
// import PageHeader from './PageHeader.vue';
</script>

<template>
    <div id="main_container">
        <el-container style="width: 100vw; height: 100vh;">
            <el-header style="width: 100vw; height: 8vh;">
                Header
                <!-- <el-container style="width: 100vw; height: 10vh;">
                    <PageHeader />
                </el-container> -->
            </el-header>
            <el-container style="width: 100vw; height: 92vh;">
                <SideBar />
                <el-main><router-view></router-view></el-main>
            </el-container>
        </el-container>
    </div>
</template>

<style>
#main_container {
    font-family: Arial, Helvetica, sans-serif;
    font-size: 14px;
    color: #2c3e50;
    text-align: center;
}
</style>