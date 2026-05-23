<script setup>
import {reactive, ref} from "vue";

const props=defineProps(['visible'])

const columns = ref([
  { title:"编号", width: "80px", key:"id", fixed: "left", sort: "desc" },
  { title:"桌号", width: "80px", key:"desk", sort: "desc" },
  { title:"日期", width: "120px", key:"date" },
  { title:"总价", width: "120px", key:"total" },
  { title:"备注", width: "260px", key:"notes" },
]);
const data=reactive({
  dataSource:[]
})

</script>

<template>
  <lay-layer title="订单列表" v-model="props.visible" :area="['800px', '700px']">
    <div style="padding: 20px;">
      <lay-table
          :height="'500px'"
          :columns="columns"
          :default-toolbar="true"
          :data-source="data.dataSource">
      </lay-table>
    </div>
  </lay-layer>
</template>

<style scoped>

</style>