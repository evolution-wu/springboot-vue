import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import '@/assets/css/global.css'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import '@element-plus/icons-vue'
//注意引入elementplus的组件
createApp(App).use(store).use(router).use(ElementPlus).mount('#app')
