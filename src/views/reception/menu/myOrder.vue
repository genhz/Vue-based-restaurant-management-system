<script lang="ts" setup>
import {useRouter} from "vue-router";
import {computed, onMounted, reactive} from "vue";
import {useStore} from "vuex";
import {OrderService} from "../../../utils/service/orderService";

const router=useRouter()
const store=useStore()
const data=reactive({
  desk:store.state.reception.desk,
  dishesList:[]
})
const total=computed(()=>{
  let tolal=0
  data.dishesList.forEach(dishes=>{
    tolal+=dishes.number*dishes.price
    console.log(tolal)
  })
  return tolal*100
})
const init=()=>{
  let desk=store.state.reception.desk
  console.log(desk)
  OrderService.getOrderByDesk(desk).then((response)=>{
    data.dishesList=response.data
  })
}

onMounted(()=>{
  init()
})

const onClickLeft=()=>{
  router.back()
}
const remove = (index) => {
  data.dishesList.splice(index,1)
}
</script>

<template>
  <van-nav-bar
      title="我的订单"
      left-text="返回"
      left-arrow
      @click-left="onClickLeft"
  />
  <template v-for="(items,index) in data.dishesList" :key="index">
    <van-swipe-cell>
      <van-card
          :price="items.price"
          :desc="'销量：'+items.sales_volume"
          :title="items.name"
          class="goods-card"
          :thumb="'./src/assets/img/dishes/'+items.image"
          :num="items.number"
      >
      </van-card>
      <template #right>
        <van-button square text="编辑" type="primary" class="delete-button" />
        <van-button square @click="remove(index)" text="删除" type="danger" class="delete-button" />
      </template>
    </van-swipe-cell>
  </template>
  <van-submit-bar disabled :price="total" button-text="修改订单" tip="请到前台修改订单" @submit="addOrder" />


</template>

<style scoped>
.goods-card {
  margin-top: 5px;
  //background-color: white;
}

.delete-button {
  height: 100%;
}
</style>