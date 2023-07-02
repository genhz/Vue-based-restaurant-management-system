<script setup>

import {onMounted, reactive, ref} from "vue";
import {PayService} from "@/utils/service/payService";
import orderInfo from '../../components/orderInfo.vue'

const visibleOrder=ref(false)
const visibleSalary=ref(false)
const visiblePurchasing=ref(false)

const columnsOrder = ref([
  { title:"编号", width: "80px", key:"id", fixed: "left", sort: "desc" },
  { title:"桌号", width: "80px", key:"desk", sort: "desc" },
  { title:"日期", width: "120px", key:"date" },
  { title:"总价", width: "120px", key:"total" },
]);
const columnsSalary = ref([
  { title:"编号", width: "80px", key:"id", fixed: "left", sort: "desc" },
  { title:"员工编号", width: "120px", key:"per_id" },
  { title:"员工姓名", width: "120px", key:"per_name" },
  { title:"签到天数", width: "120px", key:"day" },
  { title:"薪资", width: "120px", key:"salary" },
]);
const columnsPurchasing = ref([
  { title:"编号", width: "80px", key:"id", fixed: "left", sort: "desc" },
  { title:"采购日期", width: "120px", key:"p_date" },
  { title:"总价", width: "120px", key:"p_total" },
]);

const columns = ref([
  { title:"编号", width: "80px", key:"id", fixed: "left", sort: "desc" },
  { title:"月份", width: "150px", key:"month" },
  { title:"支出", width: "120px", key:"pay_total", sort: "desc" },
  { title:"详情", width: "150px", customSlot:"operator", key:"operator", fixed: "right" }
]);
const columns2 = ref([
  { title:"编号", width: "80px", key:"id", fixed: "left", sort: "desc" },
  { title:"月份", width: "150px", key:"month" },
  { title:"收入", width: "120px", key:"pr_price", sort: "desc" },
  { title:"详情", width: "150px", customSlot:"operator", key:"operator", fixed: "right" }
]);

const data=reactive({
  dataSource:[],
  dataSource2:[],
  dataSourceOrder:[],
  dataSourceSalary:[],
  dataSourcePurchasing:[],
})
const init = () => {
  PayService.getAllPay().then((response)=>{
    data.dataSource=response.data
  })
  PayService.getAllProfit().then((response)=>{
    data.dataSource2=response.data
  })
}
onMounted(()=>{
  init()
})
const Salary=(row)=>{
  PayService.getSalaryById(row.id).then((response)=>{
    console.log('getSalaryById')
    console.log(response.data)
    data.dataSourceSalary=response.data
    visibleSalary.value = !visibleSalary.value;
  })
}

const Purchasing=(row)=>{
  PayService.getPurchasingById(row.id).then((response)=>{
    data.dataSourcePurchasing=response.data
    console.log(response.data)
    visiblePurchasing.value = !visiblePurchasing.value;
  })

}

const orders=(row)=>{
  PayService.getOrderById(row.id).then((response)=>{
    data.dataSourceOrder=response.data
    console.log(response.data)
    visibleOrder.value = !visibleOrder.value;
  })

}

</script>

<template>
  <lay-layer title="订单列表" v-model="visibleOrder" :area="['800px', '900px']">
    <div style="padding: 20px;">
      <lay-table
          :height="'500px'"
          :columns="columnsOrder"
          :default-toolbar="true"
          :data-source="data.dataSourceOrder">
      </lay-table>
    </div>
  </lay-layer>
  <lay-layer title="薪资列表" v-model="visibleSalary" :area="['800px', '900px']">
    <div style="padding: 20px;">
      <lay-table
          :height="'500px'"
          :columns="columnsSalary"
          :default-toolbar="true"
          :data-source="data.dataSourceSalary">
      </lay-table>
    </div>
  </lay-layer>
  <lay-layer title="采购列表" v-model="visiblePurchasing" :area="['800px', '900px']">
    <div style="padding: 20px;">
      <lay-table
          :height="'500px'"
          :columns="columnsPurchasing"
          :default-toolbar="true"
          :data-source="data.dataSourcePurchasing">
      </lay-table>
    </div>
  </lay-layer>

  <lay-row space="10">
    <lay-col md="12">
      <lay-table
          :height="'600px'"
          :columns="columns2"
          :default-toolbar="true"
          :data-source="data.dataSource2">
        <template v-slot:toolbar>
          <lay-row space="10">
            <lay-col md="16"><h3>收入信息</h3></lay-col>
          </lay-row>
        </template>
        <template v-slot:operator="{ row }">

          <lay-button size="xs" type="normal" @click="orders(row)">订单信息</lay-button>
        </template>

      </lay-table>
    </lay-col>
      <lay-col md="12">
        <lay-table
            :height="'600px'"
            :columns="columns"
            :default-toolbar="true"
            :data-source="data.dataSource">
          <template v-slot:toolbar>
            <lay-row space="10">
              <lay-col md="16"><h3>支出信息</h3></lay-col>
            </lay-row>
          </template>
          <template v-slot:operator="{ row }">

            <lay-button size="xs" type="normal" @click="Salary(row)">薪资信息</lay-button>
            <lay-button size="xs" type="normal" @click="Purchasing(row)">采购信息</lay-button>
          </template>

        </lay-table>
      </lay-col>
  </lay-row>


</template>

<style scoped>

</style>