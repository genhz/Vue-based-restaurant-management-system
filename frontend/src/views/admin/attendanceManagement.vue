<script setup>
import {useRouter} from "vue-router";
import {onMounted, reactive, ref} from "vue";
import {PersonnelService} from "@/utils/service/personnelService";
import {DishesService} from "@/utils/service/dishesService";
import {layer} from "@layui/layer-vue";

const router=useRouter()
const selectedKeys = ref([]);

const columns = ref([
  { fixed: "left", type: "checkbox", title: "复选"},
  { title:"编号", width: "80px", key:"id", fixed: "left", sort: "desc" },
  { title:"员工姓名", width: "120px", key:"p_name"},
  { title:"联系方式", width: "150px", key:"phone" },
  { title:"职位", width: "50px", key:"p_type"}
]);

const data=reactive({
  dataSource:[]
})

const init=()=>{
  updateTime()
  PersonnelService.getAllPersonnel().then((response)=>{
    data.dataSource=response.data
  })
}

const getSelectedKeys3 = () => {

  let keys=Array.from(selectedKeys.value)
  console.log(keys)
  PersonnelService.Attendance(keys).then((response)=>{
    if (response.data){
      layer.msg("签到成功", { time: 1000, icon: 1 })
    }else {
      layer.msg("不要重复签到", { time: 1000, icon: 7 })
    }
  })
}

const date=ref()
const updateTime=()=>{
  const now = new Date()
  const year = now.getFullYear()  //年
  const month = now.getMonth() + 1 //月
  const day = now.getDate()    //日
  //想展示什么  对应的展示即可
  date.value = `${year}-${month}-${day} `
}


onMounted(()=>{
  init()
})


</script>

<template>
  <lay-table
      :height="'600px'"
      :columns="columns"
      :default-toolbar="true"
      :data-source="data.dataSource"
      v-model:selectedKeys="selectedKeys">
    <template v-slot:toolbar>

      <lay-row space="10">
        <lay-col md="4">
          <lay-date-picker disabled v-model="date" simple type="date"></lay-date-picker>
        </lay-col>
        <lay-col md="16"><h3><lay-button type="primary"  @click="getSelectedKeys3">今日出勤签到登记</lay-button></h3></lay-col>
      </lay-row>


    </template>


  </lay-table>
</template>

<style scoped>

</style>