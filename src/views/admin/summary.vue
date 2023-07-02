<template >
  <lay-row space="30">
    <lay-col md="4">
      <lay-card title="本月总收入">
        <h1 style="padding:20px 15px; font-family: sans-serif">
          <lay-count-up :end-val="info.monthinput" prefix="¥" suffix="↑"></lay-count-up>
        </h1>
      </lay-card>
    </lay-col>
    <lay-col md="4">
      <lay-card title="本月总支出">
        <h1 style="padding:20px 15px; font-family: sans-serif">
          <lay-count-up :end-val="info.monthpay" prefix="¥" suffix="↑"></lay-count-up>
        </h1>
      </lay-card>
    </lay-col>
    <lay-col md="4">
      <lay-card title="本月总利润">
        <h1 style="padding:20px 15px; font-family: sans-serif">
          <lay-count-up :end-val="info.monthprofit" prefix="¥" suffix="↑"></lay-count-up>
        </h1>
      </lay-card>
    </lay-col>
    <lay-col md="4">
      <lay-card title="本月总订单量">
        <h1 style="padding:20px 15px; font-family: sans-serif">
          <lay-count-up :end-val="info.monthorders" suffix="↑"></lay-count-up>
        </h1>
      </lay-card>
    </lay-col>
    <lay-col md="4">
      <lay-card title="本月员工薪资">
        <h1 style="padding:20px 15px; font-family: sans-serif">
          <lay-count-up :end-val="info.monthsalary" prefix="¥" suffix="↑"></lay-count-up>
        </h1>
      </lay-card>
    </lay-col>
  </lay-row>
  <lay-row space="10">
    <lay-col md="24">
      <lay-card title="最近一月数据概览">
        <div style="height: 600px">
          <v-chart :option="IncomeAndExpensesForTheMonth" autoresize :loading="false" />
        </div>
      </lay-card>
    </lay-col>
  </lay-row>

</template>


<script setup>
import {onMounted, reactive, ref} from "vue";
import {EchartsService} from "@/utils/service/echartsService";

const info=ref({
})

onMounted(()=>{
  init()
})

const init = () => {
  EchartsService.getmonthall().then((response)=>{
    info.value=response.data
  })
  EchartsService.getMonthExpenses().then((response)=>{
    data.Expenses=response.data
    EchartsService.getMonthIncome().then((response)=>{
      data.Income=response.data
      console.log("收入")
      console.log(data.Income)
      updateData()
    })
  })

}
const data=reactive({
  Income:{
    ydata:[],
    xdata:[]
  },
  Expenses:{
    ydata:[],
    xdata:[]
  },
})
const updateData = () => {
  // IncomeAndExpensesForTheMonth.series.data = [120, 10, 50, 380, 70, 210, 230]; //可以
  IncomeAndExpensesForTheMonth.xAxis[0].data = data.Income.xdata.reverse()
  IncomeAndExpensesForTheMonth.series[0].data=data.Income.ydata.reverse()
  IncomeAndExpensesForTheMonth.series[1].data=data.Expenses.ydata.reverse()
  let profit=[]
  for (var i=0;i<data.Income.ydata.length;i++){
    profit[i]=data.Income.ydata[i]-data.Expenses.ydata[i]
  }
  IncomeAndExpensesForTheMonth.series[2].data=profit
};


const IncomeAndExpensesForTheMonth = reactive({
  tooltip: {
    trigger: 'axis',
    axisPointer: {
      type: 'cross',
      crossStyle: {
        color: '#999'
      }
    }
  },
  toolbox: {
    feature: {
      dataView: { show: true, readOnly: false },
      magicType: { show: true, type: ['line', 'bar'] },
      restore: { show: true },
      saveAsImage: { show: true }
    }
  },
  legend: {
    data: ['销售收入', '采购支出', '毛利润']
  },
  xAxis: [
    {
      type: 'category',
      data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
      axisPointer: {
        type: 'shadow'
      }
    }
  ],
  yAxis: [
    {
      type: 'value',
      name: '收支',
      // min: 0,
      // max: 250,
      // interval: 50,
      axisLabel: {
        formatter: '{value} 元'
      }
    },
    {
      type: 'value',
      name: '利润',
      // min: 0,
      // max: 25,
      // interval: 5,
      axisLabel: {
        formatter: '{value} 元'
      }
    }
  ],
  series: [
    {
      name: '销售收入',
      type: 'bar',
      tooltip: {
        valueFormatter: function (value) {
          return value + ' 元';
        }
      },
      data: [
        2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3
      ]
    },
    {
      name: '采购支出',
      type: 'bar',
      tooltip: {
        valueFormatter: function (value) {
          return value + ' 元';
        }
      },
      data: [
        2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3
      ]
    },
    {
      name: '毛利润',
      type: 'line',
      yAxisIndex: 1,
      tooltip: {
        valueFormatter: function (value) {
          return value + ' 元';
        }
      },
      data: [2.0, 2.2, 3.3, 4.5, 6.3, 10.2, 20.3, 23.4, 23.0, 16.5, 12.0, 6.2]
    }
  ]
});


</script>