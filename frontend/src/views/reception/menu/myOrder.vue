<script setup>
import { computed, onMounted, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'
import { showFailToast } from 'vant'
import { OrderService } from '@/utils/service/orderService'

const router = useRouter()
const store = useStore()

const data = reactive({
  desk: store.state.reception.desk,
  dishesList: [],
  loading: false
})

const total = computed(() => {
  let sum = 0
  if (data.dishesList) {
    data.dishesList.forEach(dish => {
      sum += (dish.number || 0) * (dish.price || 0)
    })
  }
  return sum * 100
})

const getImgUrl = (name) => {
  if (!name || name === 'default.jpg') return '/img/dishes/九转大肠.jpg'
  return `/img/dishes/${name}`
}

const init = async () => {
  data.loading = true
  try {
    const desk = store.state.reception.desk
    const response = await OrderService.getOrderByDesk(desk)
    data.dishesList = response.data || []
  } catch (err) {
    showFailToast('加载订单失败')
  } finally {
    data.loading = false
  }
}

onMounted(() => {
  init()
})

const onClickLeft = () => {
  router.back()
}
</script>

<template>
  <van-nav-bar
    title="我的订单"
    left-text="返回"
    left-arrow
    @click-left="onClickLeft"
  />

  <van-empty v-if="!data.loading && data.dishesList.length === 0" description="暂无订单" />

  <van-swipe-cell v-for="(item, index) in data.dishesList" :key="index">
    <van-card
      :price="item.price"
      :desc="'销量: ' + (item.sales_volume || 0)"
      :title="item.name"
      class="goods-card"
      :thumb="getImgUrl(item.image)"
      :num="item.number"
    />
  </van-swipe-cell>

  <van-submit-bar
    :price="total"
    button-text="修改订单"
    tip="请到前台修改订单"
  />
</template>

<style scoped>
.goods-card {
  margin-top: 5px;
}
</style>
