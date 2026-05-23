<template >
  <lay-row space="10">
    <lay-col md="12">
      <lay-card title="食材总揽">
        <div style="height: 500px">
          <v-chart :option="Material" autoresize :loading="false" />
          <!--    <button @click="updateMaterial">更新数据</button>-->
        </div>
      </lay-card>
    </lay-col>
    <lay-col md="12">
      <lay-card title="供应商总览">
        <div style="height: 500px">
          <v-chart :option="Supplied" autoresize :loading="false" />
          <!--    <button @click="updateSupplied">更新数据</button>-->
        </div>
      </lay-card>
    </lay-col>
  </lay-row>


</template>


<script setup>
import {onMounted, reactive} from "vue";
import {EchartsService} from "@/utils/service/echartsService";

onMounted(()=>{
  init()
})

const data=reactive({
  Supplied:[],
  Material:[]
})
const updateMaterial = () => {
  EchartsService.getMaterialNamePidService().then((response)=>{
    Material.series[0].data=response.data
    console.log("食材")
    console.log(response.data)
  })
};
const updateSupplied = () => {
  EchartsService.getMaterialSuppliedPidService().then((response)=>{
    Supplied.series[0].data=response.data
    console.log("供应商")
    console.log(response.data)
  })
};

const init=()=>{
  updateSupplied()
  updateMaterial()
}

const Supplied = reactive({
  // title: {
  //   text: '食材总览'
  // },
  legend: {
    top: 'bottom'
  },
  toolbox: {
    show: true,
    feature: {
      mark: { show: true },
      dataView: { show: true, readOnly: false },
      restore: { show: true },
      saveAsImage: { show: true }
    }
  },
  series: [
    {
      name: 'Nightingale Chart',
      type: 'pie',
      radius: [50, 250],
      center: ['50%', '50%'],
      roseType: 'area',
      itemStyle: {
        borderRadius: 8
      },
      data: [
        { value: 40, name: 'rose 1' },
        { value: 38, name: 'rose 2' },
        { value: 32, name: 'rose 3' },
        { value: 30, name: 'rose 4' },
        { value: 28, name: 'rose 5' },
        { value: 26, name: 'rose 6' },
        { value: 22, name: 'rose 7' },
        { value: 18, name: 'rose 8' }
      ]
    }
  ]
})
const Material = reactive({
  // title: {
  //   text: '供应商总览'
  // },
  legend: {
    top: 'bottom'
  },
  toolbox: {
    show: true,
    feature: {
      mark: { show: true },
      dataView: { show: true, readOnly: false },
      restore: { show: true },
      saveAsImage: { show: true }
    }
  },
  series: [
    {
      name: 'Nightingale Chart',
      type: 'pie',
      radius: [50, 250],
      center: ['50%', '50%'],
      roseType: 'area',
      itemStyle: {
        borderRadius: 8
      },
      data: [
        { value: 40, name: 'rose 1' },
        { value: 38, name: 'rose 2' },
        { value: 32, name: 'rose 3' },
        { value: 30, name: 'rose 4' },
        { value: 28, name: 'rose 5' },
        { value: 26, name: 'rose 6' },
        { value: 22, name: 'rose 7' },
        { value: 18, name: 'rose 8' }
      ]
    }
  ]
});


</script>

<style scoped>
.grid-demo {
  padding: 10px;
  line-height: 50px;
  border-radius: 2px;
  text-align: center;
}
</style>