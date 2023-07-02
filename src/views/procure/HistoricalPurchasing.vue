<script lang="ts" setup>

import {onMounted, reactive, ref} from "vue";
import {PayService} from "@/utils/service/payService";
import {MaterialService} from "@/utils/service/MaterialService";

const columns = ref([
  { title:"日期", width: "150px", key:"date", fixed: "left", sort: "desc" },
  { title:"总支出", width: "150px", sort: "desc",key:"price" },
  { title:"详情", width: "150px", customSlot:"operator", key:"operator", fixed: "right" }
]);

// id:item.id,
//     name:item.materialList.m_name,
//     number:item.number,
//     price:item.materialList.price,
//     safety_stock:item.materialList.safety_stock,
//     stock:item.materialList.stock,
//     unit:item.materialList.unit,
//     supplier:item.materialList.supplier

const columnsPurchasing=ref([
  { title:"编号", width: "150px", key:"id", fixed: "left", sort: "desc" },
  { title:"食材", width: "150px",key:"name" },
  { title:"采购数量", width: "150px",key:"number" },
  { title:"单价", width: "150px",key:"price" },
  { title:"安全库存", width: "150px",key:"safety_stock" },
  { title:"库存", width: "150px",key:"stock" },
  { title:"单位", width: "150px",key:"unit" },
  { title:"供应商", width: "150px",key:"supplier" },
])
const data=reactive({
  dataSource:[],
  dataSourcePurchasing:[],
})
const init = () => {
  MaterialService.getProcurelist().then((response)=>{
    data.dataSource=response.data.reverse()
  })
}
onMounted(()=>{
  init()
})
const Purchasing = (row) => {
  data.dataSourcePurchasing=[]
  MaterialService.getProcurelistmonth(row.date).then((response)=>{
    let dataSourcePurchasing=response.data
    dataSourcePurchasing.forEach(item=>{
      data.dataSourcePurchasing.push({
        id:item.id,
        name:item.materialList[0].m_name,
        number:item.number,
        price:item.materialList[0].price,
        safety_stock:item.materialList[0].safety_stock,
        stock:item.materialList[0].stock,
        unit:item.materialList[0].unit,
        supplier:item.materialList[0].supplier
      })
    })
  })
}
</script>

<template>
  <lay-row space="10">
    <lay-col md="6">
      <lay-table
          :height="'600px'"
          :columns="columns"
          :default-toolbar="true"
          :data-source="data.dataSource">
        <template v-slot:toolbar>
          <lay-row space="10">
            <lay-col md="16"><h3>历史采购</h3></lay-col>
          </lay-row>
        </template>
        <template v-slot:operator="{ row }">
          <lay-button size="xs" type="normal" @click="Purchasing(row)">查看详情</lay-button>
        </template>

      </lay-table>
    </lay-col>
    <lay-col md="18">
      <lay-table
          :height="'600px'"
          :columns="columnsPurchasing"
          :default-toolbar="true"
          :data-source="data.dataSourcePurchasing">
        <template v-slot:toolbar>
          <lay-row space="10">
            <lay-col md="16"><h3>详情</h3></lay-col>
          </lay-row>
        </template>
<!--        <template v-slot:operator="{ row }">-->
<!--          <lay-button size="xs" type="normal" @click="Purchasing(row)">查看详情</lay-button>-->
<!--        </template>-->

      </lay-table>
    </lay-col>
  </lay-row>
</template>

<style scoped>

</style>