<script setup>
import {useRouter} from "vue-router";
import {onMounted, reactive, ref} from "vue";
import {MaterialService} from "@/utils/service/MaterialService";
import {layer} from "@layui/layer-vue";
import {PersonnelService} from "@/utils/service/personnelService";

const router=useRouter()

const columns = ref([
  { title:"编号", width: "80px", key:"id", fixed: "left", sort: "desc" },
  { title:"食材名", width: "120px", key:"m_name"},
  { title:"单价", width: "80px", key:"price" },
  { title:"单位", width: "50px", key:"unit"},
  { title:"库存", width: "120px", key:"stock", sort: "desc" },
  { title:"安全库存", width: "120px", key:"safety_stock"},
  { title:"供应商", width: "240px", key:"supplier" },
  { title:"操作", width: "150px", customSlot:"operator", key:"operator", fixed: "right" }
]);

const data=reactive({
  dataSource:[]
})

const init=()=>{
  MaterialService.getAllMaterial().then((response)=>{
        data.dataSource=response.data
    console.log(data.dataSource)
      }
  )
}

const edit=(row)=>{
  console.log(row.id)
  // router.push({name:'editMaterial',params: {id:row.id}})
  router.push(`editMaterial/${row.id}`)
}
const addOrder=()=>{
  router.push({path:'addMaterial'})
}
const remove=(row)=>{
  layer.confirm("您确定要删除吗",
      {
        btn: [
          {text:'确定', callback: (id) => {
              MaterialService.removeMaterial(row.id).then(()=>{
                init()
              })
              layer.close(id); }
          },
          {text:'取消', callback: (id) => {
              layer.close(id); }
          }
        ]
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
    data.dataSource=response.data
  })
}

const searchType=()=>{
  MaterialService.searchMaterialSupplier(search2.value).then((response)=>{
    data.dataSource=response.data
  })
}

</script>

<template>
  <lay-table
      :height="'600px'"
      :columns="columns"
      :default-toolbar="true"
      :data-source="data.dataSource">
    <template v-slot:toolbar>
      <lay-row space="10">
        <lay-col md="2"><div class="grid-demo">
          <lay-button size="sm" type="primary" @click="addOrder">新增</lay-button>
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
      <lay-button size="xs" type="primary" @click="edit(row)">编辑</lay-button>
      <lay-button size="xs" type="danger" @click="remove(row)">删除</lay-button>
    </template>
  </lay-table>
</template>

<style scoped>

</style>