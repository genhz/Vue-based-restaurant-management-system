<script lang="ts" setup>
import Header from '../../components/Header.vue'

</script>

<template>
  <Header title="库存管理系统">
    <template #side>
      <router-link :to="{name:'procureSummary'}">
        <el-menu-item index="1">
          <span>汇总</span>
        </el-menu-item>
      </router-link>

      <router-link :to="{name:'planning'}">
        <el-menu-item index="2">
          <span>采购计划</span>
        </el-menu-item>
      </router-link>
      <router-link :to="{name:'warehousing'}">
        <el-menu-item index="3">
          <span>入库管理</span>
        </el-menu-item>
      </router-link>
      <router-link :to="{name:'inventorymanage'}">
        <el-menu-item index="4">
          <span>库存管理</span>
        </el-menu-item>
      </router-link>
      <router-link :to="{name:'HistoricalPurchasing'}">
        <el-menu-item index="5">
          <span>历史采购</span>
        </el-menu-item>
      </router-link>
    </template>
  </Header>
</template>

<style scoped>

</style>