<script lang="ts" setup>
import {onMounted, reactive, ref} from "vue";
import {useStore} from "vuex";
import {layer} from "@layui/layer-vue";
import {useRouter} from "vue-router";

const props=defineProps(['title'])

const store=useStore()
const router=useRouter()

const activeIndex = ref('0')
const handleSelect = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
const handleOpen = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
const handleClose = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
const user=reactive({
  value:store.state.userStore.user.p_name
})

const logout=()=>{
  store.commit('userStore/setRole','user')
  layer.msg('注销成功',{ icon : 1, time: 1000})
  router.push({path:'/'})
}
</script>

<template>
  <el-container>
    <el-header>
      <el-menu
          :default-active="activeIndex"
          class="el-menu-demo"
          mode="horizontal"
          :ellipsis="false"
          @select="handleSelect"
      >
        <el-menu-item index="0">{{props.title}}</el-menu-item>
        <div class="flex-grow" />
        <el-menu-item index="1">{{user.value}}</el-menu-item>
        <el-menu-item @click="logout" index="2">注销</el-menu-item>
      </el-menu>
    </el-header>
    <el-container>
      <el-aside width="200px">
        <el-menu
            default-active="1"
            class="el-menu-vertical-demo"
            @open="handleOpen"
            @close="handleClose"
        >
      <slot name="side"></slot>
        </el-menu>
      </el-aside>
      <el-main class="card-container">
<!--        <div class="card-container">

        </div>-->
        <router-view></router-view>

      </el-main>
    </el-container>
<!--    <lay-footer>copyright@yuanzzliuyc2023</lay-footer>-->
  </el-container>

</template>

<style scoped>
.flex-grow {
  flex-grow: 1;
}
.card-container {
  margin: 0px;
  background: whitesmoke;
  padding: 20px;
}
</style>