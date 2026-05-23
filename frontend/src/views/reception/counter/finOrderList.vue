<script setup>
import {ref, watch, reactive, computed, onMounted} from 'vue';
import { layer } from '@layui/layui-vue';
import {useRouter} from "vue-router";
import {OrderService} from "@/utils/service/orderService";

const loading = ref(false);

const router=useRouter()

const columns = ref([
  { title:"编号", width: "80px", key:"id", fixed: "left", sort: "desc" },
  { title:"桌号", width: "80px", key:"desk", sort: "desc" },
  { title:"日期", width: "120px", key:"date" },
  { title:"总价", width: "120px", key:"total" },
  { title:"备注", width: "260px", key:"notes" },
  { title:"完成", width: "80px", key:"status", customSlot: "status",sort: "desc"},
  { title:"操作", width: "150px", customSlot:"operator", key:"operator", fixed: "right" }
]);


const data=reactive({
  dataSource:[]
})

const changeStatus = (isChecked, row) => {
  data.dataSource.forEach((item) => {
    if(item.id === row.id) {
      layer.msg('成功', { icon: 1 })
      item.status = isChecked;
      OrderService.updateOrderStatus(item)
    }
  })
}

const init=()=>{
  OrderService.getfinOrder().then((response)=>{
    data.dataSource=response.data.reverse()
  })
}
onMounted(()=>{
  init()
})

const remove = (row) => {
  OrderService.removeOrder(row.id)
  init()
}
const edit=(row)=>{
  router.push({name:'editOrder',params: {id:row.id}})
}

const addOrder=()=>{
  var order={}
  OrderService.addOrder(order)
  init()
}


</script>

<template>
  <lay-table
      :height="'600px'"
      :columns="columns"
      :default-toolbar="true"
      :data-source="data.dataSource">
    <template #status="{ row }">
      <lay-switch :model-value="row.status" @change="changeStatus($event , row)"></lay-switch>
    </template>
    <template v-slot:toolbar>
      <lay-button size="sm" type="primary" @click="addOrder">新增</lay-button>
    </template>
    <template v-slot:operator="{ row }">
      <lay-button size="xs" type="primary" @click="edit(row)">编辑</lay-button>
      <lay-button size="xs" type="danger" @click="remove(row)">删除</lay-button>
    </template>
  </lay-table>


</template>

<style scoped>

</style>