import { defineStore } from 'pinia'
import { ref, watch, onMounted } from 'vue'
import { storage } from './storage'

export const useThemeStore = defineStore('theme', () => {
    const darkMode = ref(true)

    onMounted(() => {
        const storedDarkMode: boolean = JSON.parse(storage.getItem('darkMode'))
        if (storedDarkMode !== null) {
            darkMode.value = storedDarkMode
        } else {
            storage.setItem('darkMode', JSON.stringify(darkMode.value))
        }
    })

    watch(darkMode, (newVal) => {
        storage.setItem('darkMode', JSON.stringify(newVal))
    })

    const changeDarkMode = () => {
        darkMode.value = !darkMode.value
    }

    return { darkMode, changeDarkMode }
})
