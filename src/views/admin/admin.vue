<script lang="ts" setup>
import { ref } from 'vue'
import Header from '../../components/Header.vue'

</script>

<template>

    <Header title="后台管理系统">
      <template #side>
        <router-link :to="{name:'adminsummary'}">
          <el-menu-item index="1">
            <span>汇总</span>
          </el-menu-item>
        </router-link>

        <router-link :to="{name:'dishesmanage'}">
          <el-menu-item index="2">
            <span>菜品管理</span>
          </el-menu-item>
        </router-link>
        <router-link :to="{name:'inventorymanageAdmin'}">
          <el-menu-item index="6">
            <span>库存管理</span>
          </el-menu-item>
        </router-link>
        <router-link :to="{name:'staffmanage'}">
          <el-menu-item index="3">
            <span>员工管理</span>
          </el-menu-item>
        </router-link>
        <router-link :to="{name:'attendanceManagement'}">
          <el-menu-item index="4">
            <span>考勤管理</span>
          </el-menu-item>
        </router-link>
        <router-link :to="{name:'payManagement'}">
          <el-menu-item index="5">
            <span>营业统计</span>
          </el-menu-item>
        </router-link>

      </template>
    </Header>

</template>

<style scoped>

</style>