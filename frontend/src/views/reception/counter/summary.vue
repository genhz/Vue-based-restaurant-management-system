<script setup>
import {getCurrentInstance, onMounted, reactive} from 'vue';
import {EchartsService} from "@/utils/service/echartsService";
import {singleLineChart} from "@/utils/echart/SingleLineChart";

const data=reactive({
  incomeData:{
    xdata:[],
    ydata:[]
  },
  orderData:{
    xdata:[],
    ydata:[]
  },
  infor:{
    dayIncome:undefined,
    dayOrder:undefined,
    weekIncome:undefined,
    weekOrder:undefined
  }
})

const init=()=>{
  EchartsService.getWeekIncome().then((response)=>{
    data.incomeData=response.data
    const incomeDOM = document.getElementById('income');
    singleLineChart(data.incomeData.xdata.reverse(),data.incomeData.ydata.reverse(),incomeDOM,'金额','日期')
  })
  EchartsService.getWeekOrder().then((response)=>{
    data.orderData=response.data
    const ordersDOM = document.getElementById('orders');
    singleLineChart(data.orderData.xdata.reverse(),data.orderData.ydata.reverse(),ordersDOM,'订单量','日期')
  })
  EchartsService.getInfor().then((response)=>{
    data.infor=response.data
  })
}

onMounted(()=>{
  init()
})
</script>

<template>
  <lay-row space="10">
    <lay-col md="6">
      <lay-card title="今日收入">
        <h1 style="padding:20px 15px; font-family: sans-serif">
          <lay-count-up :end-val="data.infor.dayIncome" prefix="¥" suffix="↑"></lay-count-up>
        </h1>
      </lay-card>
    </lay-col>
    <lay-col md="6">
      <lay-card title="今日订单数">
        <h1 style="padding:20px 15px; font-family: sans-serif">
          <lay-count-up :end-val="data.infor.dayOrder" suffix="↑"></lay-count-up>
        </h1>
      </lay-card>
    </lay-col>
    <lay-col md="6">
      <lay-card title="本周收入">
        <h1 style="padding:20px 15px; font-family: sans-serif">
          <lay-count-up :end-val="data.infor.weekIncome" prefix="¥" suffix="↑"></lay-count-up>
        </h1>
      </lay-card>
    </lay-col>
    <lay-col md="6">
      <lay-card title="本周订单数">
        <h1 style="padding:20px 15px; font-family: sans-serif">
          <lay-count-up :end-val="data.infor.weekOrder" suffix="↑"></lay-count-up>
        </h1>
      </lay-card>
    </lay-col>
    <lay-col md="12">
      <lay-card title="最近7天收入">
        <lay-panel shadow="hover">
          <div style="height:500px;" id="income"></div>
        </lay-panel>
      </lay-card>

    </lay-col>
    <lay-col md="12">
      <lay-card title="最近7天订单量">
        <lay-panel shadow="hover">
          <div style="height:500px;" id="orders"></div>
        </lay-panel>
      </lay-card>

    </lay-col>
  </lay-row>
</template>

<style scoped>
.color-income{
  background-color: #16b777;
}
.color-order{
  background-color: #16baaa;
}
.grid-demo {
  padding: 10px;
  line-height: 100px;
  border-radius: 5px;
  text-align: center;
  font-size:20px;
  color: #fff;
}
</style>