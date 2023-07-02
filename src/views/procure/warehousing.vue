<script setup>
import {useRouter} from "vue-router";
import {computed, onMounted, reactive, ref} from "vue";
import {MaterialService} from "@/utils/service/MaterialService";
import {layer} from "@layui/layer-vue";
import {PersonnelService} from "@/utils/service/personnelService";

const router=useRouter()

const visible = ref(false);

const columns = ref([
  { title:"编号", width: "80px", key:"id", fixed: "left", sort: "desc" },
  { title:"食材名", width: "120px", key:"m_name"},
  { title:"单价", width: "80px", key:"price" },
  { title:"单位", width: "50px", key:"unit"},
  { title:"库存", width: "80px", key:"stock", sort: "desc" },
  { title:"安全库存", width: "80px", key:"safety_stock"},
  { title:"供应商", width: "240px", key:"supplier" },
  { title:"采购量", width: "120px", key:"number",customSlot: "number"},
  { title:"操作", width: "150px", customSlot:"operator", key:"operator", fixed: "right" }
]);

const data=reactive({
  dataSource:[],
  allDataSource:[]
})

const init=()=>{
  MaterialService.getPlanning().then((response)=>{
        data.dataSource=response.data
        console.log(data.dataSource)
      }
  )
  MaterialService.getAllMaterial().then((response)=>{
    data.allDataSource=response.data
  })
}

const changeVisible=()=>{
  visible.value = !visible.value;
}

const addMaterial=(row)=>{
  var f=true
  for (var i=0;i<data.dataSource.length;i++){
    if (data.dataSource[i].id==row.id){
      f=false
      layer.notifiy({
        title:"Warming",
        content:"请不要重复添加",
        icon:3
      })
    }
  }
  if (f){
    row.number=1
    data.dataSource.push(row)
  }
}
const remove=(row)=>{
  data.dataSource.splice(data.dataSource.indexOf(row), 1)
}
const submit=()=>{
  console.log("test")
  // console.log(data.dataSource)
  MaterialService.commitWarehousing(data.dataSource).then(()=>{
        layer.msg("入库成功", { icon : 1, time: 1000})
        init()
  }
  )
}

onMounted(()=>{
  init()
})

const search=ref()
const search2=ref()
const searchName=()=>{
  console.log(search.value)
  MaterialService.searchMaterialName(search.value).then((response)=>{
    data.allDataSource=response.data
  })
}

const searchType=()=>{
  MaterialService.searchMaterialSupplier(search2.value).then((response)=>{
    data.allDataSource=response.data
  })
}

</script>

<template>
  <lay-layer v-model="visible" :area="['900px', '720px']">
    <div style="padding: 20px;">
      <lay-table
          :height="'500px'"
          :columns="columns"
          :default-toolbar="true"
          :data-source="data.allDataSource">
        <template v-slot:toolbar>
          <lay-row space="10">
            <lay-col md="6"><div class="grid-demo">
              <lay-button size="sm" type="primary" @click="changeVisible">新增</lay-button>
              <lay-button size="sm" type="primary" @click="submit">提交</lay-button>
            </div></lay-col>
            <lay-col md="8"><div class="grid-demo">
              <lay-input v-model="search2" placeholder="请输入食材供应商">
                <template #suffix>
                  <lay-button size="sm" type="primary" @click="searchType">搜索</lay-button>
                </template>
              </lay-input>
            </div></lay-col>
            <lay-col md="8"><div class="grid-demo">
              <lay-input v-model="search" placeholder="请输入食材名">
                <template #suffix>
                  <lay-button size="sm" type="primary" @click="searchName">搜索</lay-button>
                </template>
              </lay-input>
            </div></lay-col>
          </lay-row>
        </template>
        <template v-slot:operator="{ row }">
          <lay-button size="xs" type="primary" @click="addMaterial(row)">添加</lay-button>
        </template>
      </lay-table>
    </div>
  </lay-layer>

  <lay-table
      :height="'600px'"
      :columns="columns"
      :default-toolbar="true"
      :data-source="data.dataSource">
    <template #number="{ row }">
      <lay-input-number v-model="row.number"></lay-input-number>
    </template>
    <template v-slot:toolbar>
      <lay-row space="10">
        <lay-col md="6"><div class="grid-demo">
          <lay-button size="sm" type="primary" @click="changeVisible">新增</lay-button>
          <lay-button size="sm" type="primary" @click="submit">提交</lay-button>
        </div></lay-col>
      </lay-row>
    </template>
    <template v-slot:operator="{ row }">
      <lay-button size="xs" type="danger" @click="remove(row)">删除</lay-button>
    </template>
  </lay-table>
</template>

<style scoped>

</style>