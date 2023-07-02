<script setup>
import {ref, reactive, onMounted, computed} from 'vue'
import { layer } from '@layui/layer-vue'
import {OrderService} from "@/utils/service/orderService";
import {useRouter} from "vue-router";
import {MenuService} from "@/utils/service/menuService";
import {DishesService} from "@/utils/service/dishesService";

const props=defineProps(['id'])

const visible = ref(false);

const router=useRouter()

const model = reactive({
  dataSource:[],
  dishes:[],
  value:{
    id:undefined,
    desk:undefined,
    date:undefined,
    notes:undefined,
    total:undefined,
    dishesList: []
  }
})



const columns = ref([
  { title:"编号", width: "80px", key:"id", fixed: "left", sort: "desc" },
  { title:"菜名", width: "120px", key:"name"},
  { title:"品类", width: "80px", key:"type"},
  { title:"价格", width: "80px", key:"price" },
  { title:"数量", width: "80px", key:"number",customSlot: "number" },
  { title:"操作", width: "150px", customSlot:"operator", key:"operator", fixed: "right" }
]);

const total=computed(()=>{
  var to=0
  for (var i=0;i<model.dataSource.length;i++){
    to+=model.dataSource[i].number*model.dataSource[i].price
  }
  let tempVal = parseFloat(to).toFixed(3)
  let realVal = tempVal.substring(0, tempVal.length - 1)
  return realVal
})

const init=()=>{
  OrderService.getOrder(props.id).then((response)=>{
    model.value=response.data
    console.log(model.value)
    console.log(model.value.dishesList)
    model.dataSource=model.value.dishesList
    console.log(model.dataSource)
  }).catch((err)=>{
    console.log(err)
  })
}
const getDishes=()=>{
  MenuService.getAllMenu().then((response)=>{
    model.dishes=response.data
  })
}
const addDish=(dish)=>{
  var f=true
  for (var i=0;i<model.dataSource.length;i++){
    if (model.dataSource[i].id==dish.id){
      f=false
      layer.notifiy({
        title:"Warming",
        content:"请不要重复添加",
        icon:3
      })
    }
  }
  if (f){
    dish.number=1
    layer.notifiy({
      title:"Success",
      content:"添加成功",
      icon:1
    })
    model.dataSource.push(dish)
  }
}
onMounted(()=>{
  init()
  getDishes()
})

const changeVisible=()=>{
  visible.value = !visible.value;
}

const remove = (row) => {
  layer.confirm("您确定要删除吗",
      {
        btn: [
          {text:'确定', callback: (id) => {
              for (var i=0;i<model.dataSource.length;i++){
                if (model.dataSource[i].id==row.id){
                  model.dataSource.splice(i,1)
                }
              }
              layer.msg("删除成功", { icon : 1, time: 1000})
              layer.close(id); }
          },
          {text:'取消', callback: (id) => {
              layer.close(id); }
          }
        ]
      }
  )
}

const handleBack=()=>{
  layer.confirm("您确定要返回吗，未提交的信息不被保存",
      {
        btn: [
          {text:'确定', callback: (id) => {
              router.back()
              layer.close(id); }
          },
          {text:'取消', callback: (id) => {
              layer.close(id); }
          }
        ]
      }
  )

}

const submit = () => {
  // layer.msg(`${JSON.stringify(model)}`, { time: 2000 });
  model.value.dishesList=model.dataSource
  model.value.total=total
  console.log(model.value)
  OrderService.editOrder(model.value).then(()=>{
    layer.msg("提交成功", { icon : 1, time: 1000})
    router.back()
  })
}
const search2=ref()
const search=ref()
const searchName=()=>{
  console.log(search.value)
  DishesService.searchDishesName(search.value).then((response)=>{
    model.dishes=response.data
  })
}

const searchType=()=>{
  DishesService.searchDishesType(search2.value).then((response)=>{
    model.dishes=response.data
  })
}

</script>

<template>
  <lay-layer title="菜品信息" v-model="visible" :area="['800px', '700px']">
    <div style="padding: 20px;">
      <lay-table
          :height="'500px'"
          :columns="columns"
          :default-toolbar="true"
          :data-source="model.dishes">
        <template v-slot:toolbar>
          <lay-row space="10">
            <lay-col md="12"><div class="grid-demo">
              <lay-input v-model="search2" placeholder="请输入品类">
                <template #suffix>
                  <lay-button size="sm" type="primary" @click="searchType">搜索</lay-button>
                </template>
              </lay-input>
            </div></lay-col>
            <lay-col md="12"><div class="grid-demo">
              <lay-input v-model="search" placeholder="请输入菜名">
                <template #suffix>
                  <lay-button size="sm" type="primary" @click="searchName">搜索</lay-button>
                </template>
              </lay-input>
            </div></lay-col>
          </lay-row>
        </template>
        <template v-slot:operator="{ row }">
          <lay-button size="xs" type="primary" @click="addDish(row)">添加</lay-button>
        </template>
      </lay-table>
    </div>
  </lay-layer>

  <lay-card>
    <template v-slot:title>
      <lay-page-header content="详情页面" @back="handleBack"></lay-page-header>
    </template>
    <template v-slot:body>
      <lay-form :model="model.value">
        <lay-table
            :columns="columns"
            :data-source="model.dataSource"
            :default-toolbar="true">
          <template #number="{ row }">
            <lay-input-number v-model="row.number" position="right"></lay-input-number>
          </template>
          <template v-slot:toolbar>
            <lay-container fluid>
              <lay-row space="10">
                <lay-col md="12" sm="24" xs="24">
                  <lay-form-item label="编号" prop="id">
                    <lay-input v-model="model.value.id" disabled></lay-input>
                  </lay-form-item>
                </lay-col>
                <lay-col md="12" sm="24" xs="24">
                  <lay-form-item label="总价" prop="total">
                    <lay-input v-model="total" disabled ></lay-input>
                  </lay-form-item>
                </lay-col>
                <lay-col md="12" sm="24" xs="24">
                  <lay-form-item label="桌号" prop="desk">
                    <lay-input v-model="model.value.desk"></lay-input>
                  </lay-form-item>
                </lay-col>
                <lay-col md="12" sm="24" xs="24">
                  <lay-form-item label="日期" prop="date">
                    <lay-date-picker v-model="model.value.date"></lay-date-picker>
                  </lay-form-item>
                </lay-col>

                <lay-col md="24">
                  <lay-form-item label="备注" prop="notes">
                    <lay-textarea placeholder="请输入描述" v-model="model.value.notes"></lay-textarea>
                  </lay-form-item>
                </lay-col>
              </lay-row>
            </lay-container>
            <lay-form-item style="text-align: center;">
              <lay-button @click="changeVisible">添加菜品</lay-button>
              <lay-button type="primary" @click="submit">提交</lay-button>
            </lay-form-item>

          </template>
          <template v-slot:operator="{ row }">
            <lay-button size="xs" type="danger" @click="remove(row)">删除</lay-button>
          </template>
        </lay-table>

      </lay-form>
    </template>
  </lay-card>


</template>

<style scoped>

</style>