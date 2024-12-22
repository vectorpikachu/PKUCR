<template>
    <div class="settings">
        <el-card class="settingSection">
            <el-switch class="settingSwitch" style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949"
                size="large" v-model="darkMode" @change="changeDarkMode" />
            <el-text class="settingLabel">
                Set Dark Mode Following System Preference
            </el-text>
        </el-card>
    </div>
</template>

<script setup>
import { useThemeStore } from '@/store/theme';
import { computed } from 'vue';

const theme = useThemeStore()
const darkMode = computed(() => theme.darkMode)
const changeDarkMode = () => { theme.changeDarkMode() }

</script>

<style scoped>
.settings {
    display: flex;
    flex-direction: column;
    text-align: left;
    margin: 2%;
}

.settingSection {
    box-shadow: none;
}

.settingLabel {
    margin-left: 2%;
    font-weight: bolder;
    font-size: x-large;
    font-style: italic;
    color: deepskyblue;
}

.settingSwitch {
    transform: translateY(-10%);
}
</style>