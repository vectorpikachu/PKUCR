<script lang="ts" setup>
import SideBar from '@/components/SideBar.vue'
import PageHeader from '@/components/PageHeader.vue'
import { startDataFetcher, stopDataFetcher } from '@/store/storage'
import { computed, onMounted, onUnmounted } from 'vue'
import { useThemeStore } from '@/store/theme'

let headerHeightVh = 10
let menuWidthVw = 15
let menuHeightVh = 100 - headerHeightVh
let mainWidthVw = 100 - menuWidthVw

const theme = useThemeStore()
const darkMode = computed(() => theme.darkMode)

onMounted(() => { startDataFetcher() })
onUnmounted(() => { stopDataFetcher() })
</script>

<template>
	<div id="main_container">
		<el-container :style="{ width: `100vw`, height: `${headerHeightVh}vh` }">
			<el-header style="width: 100%; height: 100%;" :class="{ backgroundDark: darkMode, background: !darkMode }">
				<PageHeader />
			</el-header>
		</el-container>
		<el-container :style="{ width: `100vw`, height: `${menuHeightVh}vh` }">
			<el-aside :style="{ width: `${menuWidthVw}vw`, height: `100%` }"
				:class="{ backgroundDark: darkMode, background: !darkMode }">
				<SideBar />
			</el-aside>
			<el-main :style="{ width: `${mainWidthVw}vw`, height: `100%` }"
				:class="{ backgroundDark: darkMode, background: !darkMode }">
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
	box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
	border: 1px solid silver;
}

.backgroundDark {
	background-color: white;
	position: relative;
	box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
	border: 1px solid silver;
}

@media (prefers-color-scheme: dark) {
	.backgroundDark {
		filter: invert(90%);
		-webkit-filter: invert(90%);
	}
}
</style>
