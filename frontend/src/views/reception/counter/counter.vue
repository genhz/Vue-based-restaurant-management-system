<script lang="ts" setup>
import Header from '../../../components/Header.vue'

</script>

<template>
  <Header title="前台管理系统">
    <template #side>
      <router-link :to="{name:'summary'}">
        <el-menu-item index="1">
          <span>汇总</span>
        </el-menu-item>
      </router-link>

      <router-link :to="{name:'orderList'}">
        <el-menu-item index="2">
          <span>订单列表</span>
        </el-menu-item>
      </router-link>
      <router-link :to="{name:'unOrderList'}">
        <el-menu-item index="3">
          <span>未完成订单</span>
        </el-menu-item>
      </router-link>
      <router-link :to="{name:'finOrderList'}">
        <el-menu-item index="4">
          <span>历史订单</span>
        </el-menu-item>
      </router-link>
    </template>
  </Header>
</template>

<style scoped>

</style>