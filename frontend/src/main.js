import './assets/main.css'

import { createApp } from 'vue'
import App from './App.vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import Layui from '@layui/layui-vue'
import '@layui/layui-vue/lib/index.css'
import 'vant/lib/index.css';
import Vant from 'vant'
import router from "./router/index";
import store from "@/store/index";

import * as echarts from 'echarts'
import "echarts";
import ECharts from 'vue-echarts'

const app=createApp(App)
app.config.globalProperties.$echarts = echarts
// app.use(ECharts)
app.component('v-chart', ECharts)
app.use(Vant)
app.use(store)
app.use(ElementPlus)
app.use(Layui)
app.use(router)
app.mount('#app')