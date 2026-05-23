<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import { showNotify, showToast, showFailToast } from 'vant'
import { MenuService } from '@/utils/service/menuService'
import { DishesService } from '@/utils/service/dishesService'

const container = ref(null)
const store = useStore()
const router = useRouter()
const carSheet = ref(false)
const active = ref(0)
const loading = ref(false)

const carouselImages = [
  '/img/lunbo/九转大肠.jpg',
  '/img/lunbo/肉段.png'
]

const data = reactive({
  carInner: [],
  dishes: [],
  showDishes: [],
  dishType: [],
  search: '',
  table: 0,
  notes: ''
})

data.table = store.state.reception.desk

const total = computed(() => store.getters['reception/getTotal'] * 100)

const addOrder = () => {
  if (store.state.reception.car.length === 0) {
    showFailToast('购物车为空，请先点餐')
    return
  }
  store.commit('reception/setNotes', data.notes)
  store.dispatch('reception/submitOrder', store.getters['reception/getTotal'])
  carSheet.value = false
  getCar()
}

const addCar = (id) => {
  store.commit('reception/addCar', id)
  getCar()
  showNotify({ type: 'success', duration: 500, message: '点餐成功' })
}

const removeCar = (id) => {
  store.commit('reception/delCar', id)
  getCar()
}

const getCar = () => {
  data.carInner = [...store.state.reception.car]
}

const getImgUrl = (name) => {
  if (!name || name === 'default.jpg') return '/img/dishes/九转大肠.jpg'
  return `/img/dishes/${name}`
}

const getAllDishes = async () => {
  loading.value = true
  try {
    const response = await MenuService.getAllMenu()
    const dishes = response.data || []
    data.dishes = dishes
    store.commit('reception/setDishes', dishes)
    data.showDishes = dishes
    data.dishType = [...new Set(dishes.map(d => d.type).filter(Boolean))]
  } catch (err) {
    showFailToast('加载菜单失败，请刷新重试')
  } finally {
    loading.value = false
  }
}

const getType = (index) => {
  if (index === 0) {
    data.showDishes = data.dishes
    return
  }
  const type = data.dishType[index - 1]
  data.showDishes = data.dishes.filter(d => d.type === type)
}

const openTableDialog = () => {
  const value = prompt('请输入你的桌号')
  if (value && !isNaN(Number(value))) {
    data.table = Number(value)
    store.commit('reception/setDesk', data.table)
    showNotify({ type: 'success', message: `你的桌号是: ${value}` })
  }
}

const onClickLeft = () => openTableDialog()

const onClickRight = () => {
  router.push({ name: 'myOrder' })
}

const onSearch = () => {
  const keyword = data.search.trim()
  if (!keyword) {
    data.showDishes = data.dishes
    return
  }
  DishesService.searchDishesName(keyword).then((response) => {
    data.showDishes = response.data || []
    if (data.showDishes.length === 0) {
      showToast('未找到相关菜品')
    }
  }).catch(() => {
    showFailToast('搜索失败')
  })
}

const onChange = (index) => {
  getType(index)
}

onMounted(() => {
  getAllDishes()
  if (!data.table) {
    setTimeout(() => openTableDialog(), 500)
  }
})
</script>

<template>
  <van-nav-bar
    title="满厨娘菜馆"
    :left-text="(data.table || '?') + '桌'"
    right-text="我的订单"
    left-arrow
    @click-left="onClickLeft"
    @click-right="onClickRight"
  />

  <van-sticky>
    <van-search v-model="data.search" placeholder="搜索菜品" @search="onSearch" />
  </van-sticky>

  <!-- Carousel -->
  <van-swipe :autoplay="3000" lazy-render style="height:180px">
    <van-swipe-item v-for="image in carouselImages" :key="image">
      <img :src="image" style="width:100%;height:180px;object-fit:cover" />
    </van-swipe-item>
  </van-swipe>

  <div ref="container">
    <van-sticky :container="container" :offset-top="54">
      <van-row>
        <van-col span="6">
          <van-sidebar v-model="active" @change="onChange">
            <van-sidebar-item title="全部" />
            <van-sidebar-item v-for="(type, i) in data.dishType" :key="i" :title="type" />
          </van-sidebar>
        </van-col>
        <van-col span="18">
          <div style="height:calc(100vh - 370px);overflow-y:auto;background:#f7f8fa">
            <van-pull-refresh v-model="loading" @refresh="getAllDishes">
              <van-empty v-if="!loading && data.showDishes.length === 0" description="暂无菜品" />
              <van-space v-else direction="vertical" fill :size="10" style="padding:8px">
                <van-card
                  v-for="item in data.showDishes"
                  :key="item.id"
                  :price="item.price"
                  :desc="'销量: ' + (item.sales_volume || 0)"
                  :title="item.name"
                  :thumb="getImgUrl(item.image)"
                >
                  <template #footer>
                    <van-button size="mini" type="primary" @click="addCar(item.id)">点餐</van-button>
                  </template>
                </van-card>
              </van-space>
            </van-pull-refresh>
          </div>
        </van-col>
      </van-row>
    </van-sticky>
  </div>

  <!-- Bottom action bar -->
  <van-action-bar>
    <van-action-bar-icon icon="cart-o" text="购物车" :badge="data.carInner.length || ''" @click="carSheet = true" />
    <van-action-bar-button type="danger" text="提交订单" @click="addOrder" />
  </van-action-bar>

  <!-- Cart sheet -->
  <van-action-sheet
    v-model:show="carSheet"
    cancel-text="取消"
    close-on-click-action
    title="购物车"
    :style="{ maxHeight: '70vh' }"
  >
    <van-empty v-if="data.carInner.length === 0" description="购物车是空的" />
    <van-card
      v-for="item in data.carInner"
      :key="item.id"
      :price="item.price"
      :desc="'销量: ' + (item.sales_volume || 0)"
      :title="item.name"
      :thumb="getImgUrl(item.image)"
      :num="item.number"
    >
      <template #footer>
        <van-button size="mini" @click="removeCar(item.id)">取消</van-button>
      </template>
    </van-card>
    <van-cell-group inset style="margin-top:10px">
      <van-field
        v-model="data.notes"
        rows="2"
        autosize
        label="备注"
        type="textarea"
        maxlength="50"
        placeholder="请输入订单备注"
        show-word-limit
      />
    </van-cell-group>
    <van-submit-bar :price="total" button-text="提交订单" @submit="addOrder" />
  </van-action-sheet>
</template>

<style scoped>
img {
  width: 100%;
}
</style>
