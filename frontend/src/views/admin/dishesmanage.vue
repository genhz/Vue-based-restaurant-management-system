<script setup>
import {useRouter} from "vue-router";
import {onMounted, reactive, ref} from "vue";
import {DishesService} from "@/utils/service/dishesService";
import {layer} from "@layui/layui-vue";
import {OrderService} from "@/utils/service/orderService";
import {MaterialService} from "@/utils/service/MaterialService";

const router=useRouter()

const columns = ref([
  { title:"编号", width: "80px", key:"id", fixed: "left", sort: "desc" },
  { title:"菜名", width: "120px", key:"name", sort: "desc" },
  { title:"品类", width: "80px", key:"type" },
  { title:"单价", width: "80px", key:"price" },
  { title:"销量", width: "120px", key:"sales_volume"},
  // { title:"状态", width: "80px", key:"state", customSlot: "state",sort: "desc" },
  { title:"操作", width: "150px", customSlot:"operator", key:"operator", fixed: "right" }
]);

const data=reactive({
  dataSource:[]
})

const init=()=>{
  DishesService.getAllDishes().then((response)=>{
        data.dataSource=response.data
        console.log(data.dataSource)
      }
  )
}

const changeStatus = (isChecked, row) => {
  data.dataSource.forEach((item) => {
    if(item.id === row.id) {
      layer.msg('成功', { icon: 1 })
      item.status = !row.status;
      DishesService.updateDishesState(item)
      // OrderService.updateOrderStatus(item)
    }
  })
}

const edit=(row)=>{
  router.push({name:'editDishes',params: {id:row.id}})
}

const remove=(row)=>{
  layer.confirm("您确定要删除",
      {
        btn: [
          {text:'确定', callback: (id) => {
              DishesService.removeDishes(row.id).then(()=>{
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


const addOrder=()=>{
  router.push({name:'adddishesmanage'})
}
onMounted(()=>{
  init()

})

const search=ref()
const search2=ref()
const searchName=()=>{
  DishesService.searchDishesName(search.value).then((response)=>{
    data.dataSource=response.data
  })
}

const searchType=()=>{
  DishesService.searchDishesType(search2.value).then((response)=>{
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
    <template #state="{ row }">
      <lay-switch :model-value="row.state" @change="changeStatus($event , row)"></lay-switch>
    </template>
    <template v-slot:toolbar>


      <lay-row space="10">
        <lay-col md="2"><div class="grid-demo">
          <lay-button size="sm" type="primary" @click="addOrder">新增</lay-button>
        </div></lay-col>
        <lay-col md="8"><div class="grid-demo">
          <lay-input v-model="search2" placeholder="请输入品类">
            <template #suffix>
              <lay-button size="sm" type="primary" @click="searchType">搜索</lay-button>
            </template>
          </lay-input>
        </div></lay-col>
        <lay-col md="8"><div class="grid-demo">
          <lay-input v-model="search" placeholder="请输入菜名">
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