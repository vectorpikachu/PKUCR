<template>
	<el-menu class="sidebar" :default-active="this.$route.path" router>

		<router-link to="/home">
			<el-menu-item index="/home">
				<el-icon><icon-menu /></el-icon>
				<el-text size="large" class="sidebarText">Home</el-text>
			</el-menu-item>
		</router-link>

		<router-link to="/taskTable">
			<el-menu-item index="/taskTable">
				<el-icon><icon-menu /></el-icon>
				<el-text size="large" class="sidebarText">Task</el-text>
			</el-menu-item>
		</router-link>

		<router-link to="/calendar">
			<el-menu-item index="/calendar">
				<el-icon><icon-menu /></el-icon>
				<el-text size="large" class="sidebarText">Calendar</el-text>
			</el-menu-item>
		</router-link>

		<router-link to="/resource">
			<el-menu-item index="/resource">
				<el-icon><icon-menu /></el-icon>
				<el-text size="large" class="sidebarText">Resources</el-text>
			</el-menu-item>
		</router-link>

		<router-link to="/setting">
			<el-menu-item index="/setting">
				<el-icon>
					<setting />
				</el-icon>
				<el-text size="large" class="sidebarText">Setting</el-text>
			</el-menu-item>
		</router-link>

	</el-menu>
</template>

<script lang="ts" setup>
import { Document, Menu as IconMenu, Location, Setting } from '@element-plus/icons-vue'
</script>

<style>
.sidebar {
	width: 100%;
	height: 100%;
}

.sidebarText {
	font-weight: bold;
}
</style>
