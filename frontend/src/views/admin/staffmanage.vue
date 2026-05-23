<script setup>
import {useRouter} from "vue-router";
import {onMounted, reactive, ref} from "vue";
import {PersonnelService} from "@/utils/service/personnelService";
import {DishesService} from "@/utils/service/dishesService";
import {layer} from "@layui/layer-vue";

const router=useRouter()

const columns = ref([
  { title:"编号", width: "80px", key:"id", fixed: "left", sort: "desc" },
  { title:"员工姓名", width: "120px", key:"p_name"},
  { title:"联系方式", width: "150px", key:"phone" },
  { title:"职位", width: "50px", key:"p_type"},
  { title:"基本薪资", width: "120px", key:"b_salary", sort: "desc" },
  { title:"操作", width: "150px", customSlot:"operator", key:"operator", fixed: "right" }
]);

const data=reactive({
  dataSource:[]
})

const init=()=>{
  PersonnelService.getAllPersonnel().then((response)=>{
    data.dataSource=response.data
  })
}

const edit=(row)=>{
  console.log(row.id)
  router.push({name:'editPersonnel',params: {id:row.id}})
}
const addOrder=()=>{
  router.push({name:'addPersonnel'})
}
const remove=(row)=>{
  layer.confirm("您确定要删除吗",
      {
        btn: [
          {text:'确定', callback: (id) => {
              PersonnelService.removePersonnel(row.id).then(()=>{
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
  PersonnelService.searchPersonnelName(search.value).then((response)=>{
    data.dataSource=response.data
  })
}

const searchType=()=>{
  PersonnelService.searchPersonnelType(search2.value).then((response)=>{
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
          <lay-input v-model="search2" placeholder="请输入员工职位">
            <template #suffix>
              <lay-button size="sm" type="primary" @click="searchType">搜索</lay-button>
            </template>
          </lay-input>
        </div></lay-col>
        <lay-col md="8"><div class="grid-demo">
          <lay-input v-model="search" placeholder="请输入员工姓名">
            <template #suffix>
              <lay-button size="sm" type="primary" @click="searchName">搜索</lay-button>
            </template>
          </lay-input>
        </div></lay-col>
      </lay-row>


    </template>

    <template v-slot:operator="{ row }">
      <lay-button size="xs" type="primary" @click="edit(row)">编辑</lay-button>
      <lay-button size="xs" type="danger" @click="remove(row)">解雇</lay-button>
    </template>
  </lay-table>
</template>

<style scoped>

</style>