<template>
	<el-menu class="el-menu-vertical" :default-active="this.$route.path" router>

		<router-link to="/home">
			<el-menu-item index="/home" class="sidebarItem">
				<el-icon><icon-menu /></el-icon>
				<el-text size="large" class="sidebarText">Home</el-text>
			</el-menu-item>
		</router-link>

		<router-link to="/taskTable">
			<el-menu-item index="/taskTable" class="sidebarItem">
				<el-icon><icon-menu /></el-icon>
				<el-text size="large" class="sidebarText">Task</el-text>
			</el-menu-item>
		</router-link>

		<router-link to="/calendar">
			<el-menu-item index="/calendar" class="sidebarItem">
				<el-icon><icon-menu /></el-icon>
				<el-text size="large" class="sidebarText">Calendar</el-text>
			</el-menu-item>
		</router-link>

		<router-link to="/resource">
			<el-menu-item index="/resource" class="sidebarItem">
				<el-icon><icon-menu /></el-icon>
				<el-text size="large" class="sidebarText">Resources</el-text>
			</el-menu-item>
		</router-link>

		<router-link to="/setting">
			<el-menu-item index="/setting" class="sidebarItem">
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

<style scoped>
.el-menu-vertical {
	width: 100%;
	height: 100%;
}

.sidebarText {
	font-weight: bold;
}

@media (prefers-color-scheme: dark) {
	.sidebarItem:hover {
		background-color: silver;
	}
}
</style>
