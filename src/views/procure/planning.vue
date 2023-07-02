<script setup>
import {useRouter} from "vue-router";
import {onMounted, reactive, ref} from "vue";
import {MaterialService} from "@/utils/service/MaterialService";

const router=useRouter()

const columns = ref([
  { title:"编号", width: "50px", key:"id", fixed: "left", sort: "desc" },
  { title:"食材名", width: "80px", key:"m_name"},
  { title:"单价", width: "80px", key:"price" },
  { title:"单位", width: "50px", key:"unit"},
  { title:"库存", width: "80px", key:"stock", sort: "desc" },
  { title:"采购量", width: "80px", key:"batch"},
  { title:"供应商", width: "240px", key:"supplier" },
]);

const data=reactive({
  dataSource:[]
})

const date=ref()
const updateTime=()=>{
  const now = new Date()
  const year = now.getFullYear()  //年
  const month = now.getMonth() + 1 //月
  const day = now.getDate()    //日
  //想展示什么  对应的展示即可
  date.value = `${year}-${month}-${day} `
}

const init=()=>{
  MaterialService.getPlanning().then((response)=>{
        data.dataSource=response.data
        console.log(data.dataSource)
      }
  )
}

onMounted(()=>{
  updateTime()
  init()
})
</script>

<template>
  <lay-table
      :height="'600px'"
      :columns="columns"
      :default-toolbar="true"
      :data-source="data.dataSource">
    <template v-slot:toolbar>
      <lay-row space="10">
        <lay-col md="8">
          <lay-date-picker disabled v-model="date" simple type="date"></lay-date-picker>
        </lay-col>
        <lay-col md="16"><h3>今日采购计划</h3></lay-col>
      </lay-row>

    </template>
  </lay-table>
</template>

<style scoped>

</style>