<script setup>
import {ref} from "vue";
import {DishesService} from "@/utils/service/dishesService";

const search=ref()
const search2=ref()
const searchName=()=>{
  DishesService.searchDishesName(search.value).then((response)=>{
    data.dataSource=response.data
  })
}

const searchType=()=>{
  DishesService.searchDishesType(search2.value).then((response)=>{
    data.dataSource=response.data
  })
}
</script>

<template>
  <lay-row space="10">
    <lay-col md="2"><div class="grid-demo">
      <lay-button size="sm" type="primary" @click="addOrder">新增</lay-button>
    </div></lay-col>
    <lay-col md="8"><div class="grid-demo">
      <lay-input v-model="search2" placeholder="请输入品类">
        <template #suffix>
          <lay-button size="sm" type="primary" @click="searchType">搜索</lay-button>
        </template>
      </lay-input>
    </div></lay-col>
    <lay-col md="8"><div class="grid-demo">
      <lay-input v-model="search" placeholder="请输入菜名">
        <template #suffix>
          <lay-button size="sm" type="primary" @click="searchName">搜索</lay-button>
        </template>
      </lay-input>
    </div></lay-col>
  </lay-row>
</template>

<style scoped>

</style>