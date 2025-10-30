import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import router from './router'
import VueKatex from 'vue3-katex'
import 'katex/dist/katex.min.css'

createApp(App).use(router).use(VueKatex).mount('#app')
