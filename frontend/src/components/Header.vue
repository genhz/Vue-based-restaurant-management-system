<script setup>
import { computed } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

const props = defineProps(['title'])

const store = useStore()
const router = useRouter()

const userName = computed(() => store.state.userStore.user.p_name)

const logout = () => {
  store.commit('userStore/logout')
  ElMessage.success('已退出登录')
  router.push({ path: '/' })
}
</script>

<template>
  <el-container>
    <el-header>
      <el-menu
        :default-active="'0'"
        class="el-menu-demo"
        mode="horizontal"
        :ellipsis="false"
      >
        <el-menu-item index="0">{{ props.title }}</el-menu-item>
        <div class="flex-grow" />
        <el-menu-item index="1">{{ userName }}</el-menu-item>
        <el-menu-item index="2" @click="logout">注销</el-menu-item>
      </el-menu>
    </el-header>
    <el-container>
      <el-aside width="200px">
        <el-menu
          default-active="1"
          class="el-menu-vertical-demo"
        >
          <slot name="side"></slot>
        </el-menu>
      </el-aside>
      <el-main class="main-content">
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<style scoped>
.flex-grow {
  flex-grow: 1;
}
.main-content {
  margin: 0;
  background: #f5f7fa;
  padding: 20px;
  min-height: calc(100vh - 60px);
}
</style>
