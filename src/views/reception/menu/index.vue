<script lang="ts" setup>
import { ElMessage, ElMessageBox } from 'element-plus'
import {computed, onMounted, reactive, ref} from "vue";
import {MenuService} from "../../../utils/service/menuService";
import {useStore} from "vuex";
import {showNotify, showToast} from "vant";
import {useRouter} from "vue-router";
import {DishesService} from "../../../utils/service/dishesService";

const container=ref(null)
const store=useStore();
const car = ref(false);
const active = ref(0);
const onChange = (index) => {
  getType(index)
};
const images = [
  './src/assets/img/lunbo/九转大肠.jpg',
  './src/assets/img/lunbo/肉段.png',
];
const data=reactive({
  carInner:[],
  dishes:[],
  showDishes:[],
  dishType:[],
  search:'',
  table:0,
  notes:'',
})
data.table=store.state.reception.desk;
// const carInner=computed(()=>store.state.car)
// const
const total=computed(()=> store.getters['reception/getTolal']*100 )
const addOrder=()=> {
  store.commit('reception/addOrder', {total:store.getters['reception/getTolal'],notes:data.notes,desk:data.table} )
  car.value=false
  getCar()
}
const addCar=function (id) {
  store.commit('reception/addCar',id)
  getCar()
  showNotify({ type: 'success', duration: 500, message: '点餐成功' });
}
const removeCar=function (id){
  console.log(id)
  store.commit('reception/delCar',id)
  getCar()
}
const getCar=function () {
  data.carInner=store.state.reception.car
}
const getAllDishes=()=>{
  MenuService.getAllMenu().then((response)=>{

    data.dishes=response.data
    store.commit('reception/setDishes',data.dishes)
    data.showDishes=response.data
    console.log(data.showDishes)
    data.dishType[0]=data.dishes[0].type
    for (var i=0;i<data.dishes.length;i++){
      if (data.dishType.includes(data.dishes[i].type)){
        continue
      }else {
        data.dishType.push(data.dishes[i].type)
      }
    }
  })
}
const getType=(index)=>{
  data.showDishes=[]
  if (index==0){
    data.showDishes=data.dishes
  }
  var type=data.dishType[index-1]
  for (var i=0;i<data.dishes.length;i++){
    if (data.dishes[i].type==type){
      data.showDishes.push(data.dishes[i])
    }
  }
}

const open = () => {
  ElMessageBox.prompt('请输入你的桌号', '欢迎光临', {
    confirmButtonText: 'OK',
    cancelButtonText: 'Cancel',
  })
      .then(({ value }) => {
        data.table=Number(value),
            ElMessage({
              type: 'success',
              message: `你的桌号是:${value}`,

            })
        store.commit('reception/setDesk',data.table)
      })
      .catch(() => {
        ElMessage({
          type: 'info',
          message: 'Input canceled',
        })
      })
}
const onClickLeft=()=>open()
onMounted(() => {
  getAllDishes()
  open()
})
const router=useRouter()
const onClickRight = () => {
  router.push({name:'myOrder'})
}
const onSearch=()=>{
  showToast(data.search)
  DishesService.searchDishesName(data.search).then((response)=>{
    data.showDishes=response.data
  })
}
</script>

<template>
  <van-nav-bar
      title="满厨娘菜馆"
      :left-text="data.table+'桌'"
      right-text="我的订单"
      left-arrow
      @click-left="onClickLeft"
      @click-right="onClickRight"
  />
  <van-sticky>
    <van-row>
      <van-col span="24">
        <van-search v-model="data.search" placeholder="请输入搜索关键词" @search="onSearch"/>
      </van-col>
    </van-row>
  </van-sticky>
  <van-row>
    <van-col span="24">
      <van-swipe :autoplay="3000" lazy-render>
        <van-swipe-item v-for="image in images" :key="image">
          <div style="width:100%">
            <img :src="image" />
          </div>
        </van-swipe-item>
      </van-swipe>
    </van-col>
  </van-row>
  <div ref="container">
    <van-sticky :container="container">
      <van-row>
        <van-col span="5">
          <van-sidebar v-model="active" @change="onChange">
            <van-sidebar-item title="全部" />
            <template v-for="(items,index) in data.dishType">
              <van-sidebar-item :title="items"/>
            </template>
          </van-sidebar>
        </van-col>
        <van-col span="19">
          <lay-scroll height="425px" style="background-color: whitesmoke" thumbColor="#000000">
          <van-space direction="vertical" fill :size="20">
            <template v-for="(items,index) in data.showDishes" :key="index">
              <van-card
                  style="background-color: white"
                  :price="items.price"
                  :desc="'销量：'+items.sales_volume"
                  :title="items.name"
                  :thumb="'./src/assets/img/dishes/'+items.image"
              >
                <template #footer>
                  <van-button size="mini" @click="addCar(items.id)">点餐</van-button>
                </template>
              </van-card>
              <!--          <dishes :name="items.name" :img-src="items.imgSrc" :price="items.price" :sales="items.sales"></dishes>-->
            </template>
          </van-space>
          </lay-scroll>
        </van-col>
      </van-row>
    </van-sticky>
  </div>

  <van-action-bar>
    <van-action-bar-button type="warning" text="购物车" @click="car = true"/>
    <van-action-bar-button type="danger" text="提交订单" @click="addOrder"/>
  </van-action-bar>

  <van-action-sheet
      v-model:show="car"
      cancel-text="取消"
      close-on-click-action
      title="购物车">
    <template v-for="(items,index) in data.carInner" :key="index">
      <van-card
          :price="items.price"
          :desc="'销量：'+items.sales_volume"
          :title="items.name"
          :thumb="'./src/assets/img/dishes/'+items.image"
          :num="items.number"
      >
        <template #footer>
          <van-button size="mini" @click="removeCar(items.id)">取消</van-button>
        </template>
      </van-card>
      <!--          <dishes :name="items.name" :img-src="items.imgSrc" :price="items.price" :sales="items.sales"></dishes>-->
    </template>
    <van-cell-group inset>
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
img{
  width:100%
}
.common-layout { height: 100vh; }
</style>