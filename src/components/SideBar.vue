<template>
    <el-aside width="175px" style="height: 100%; position: fixed; left: 0;">
        <el-menu default-active="2" class="el-menu-vertical">
            <router-link to="/">
                <el-menu-item index="1">
                    <el-icon><icon-menu /></el-icon>
                    Navigator One
                </el-menu-item>
            </router-link>
            <router-link to="/">
                <el-menu-item index="2">
                    <el-icon><icon-menu /></el-icon>
                    Navigator Two
                </el-menu-item>
            </router-link>
            <router-link to="/">
                <el-menu-item index="3">
                    <el-icon><icon-menu /></el-icon>
                    Navigator Three
                </el-menu-item>
            </router-link>
            <router-link to="/">
                <el-menu-item index="4">
                    <el-icon><icon-menu /></el-icon>
                    Navigator Four
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
    </el-aside>
</template>

<script lang="ts" setup>
import {
    Document,
    Menu as IconMenu,
    Location,
    Setting,
} from '@element-plus/icons-vue'
</script>

<style>
.el-menu-vertical {
    width: 100%;
    min-height: 400px;
}
</style>