<script setup>
import {DishesService} from "@/utils/service/dishesService";
import {useRouter} from "vue-router";
import {onMounted, reactive, ref} from "vue";
import {MaterialService} from "@/utils/service/MaterialService";
import {layer} from "@layui/layer-vue";
import {OrderService} from "@/utils/service/orderService";

const router=useRouter()

const visible = ref(false);

const props=defineProps(['id'])

const columns = ref([
  { title:"编号", width: "80px", key:"id", fixed: "left", sort: "desc" },
  { title:"食材名", width: "100px", key:"m_name"},
  { title:"数量", width: "70px", key:"number",customSlot: "number"},
  { title:"单价", width: "80px", key:"price" },
  { title:"单位", width: "50px", key:"unit"},
  { title:"库存", width: "120px", key:"stock", sort: "desc" },
  { title:"安全库存", width: "120px", key:"safety_stock"},
  { title:"供应商", width: "240px", key:"supplier" },
  { title:"操作", width: "150px", customSlot:"operator", key:"operator", fixed: "right" }
]);

const materialColumns=ref([
  { title:"编号", width: "80px", key:"id", fixed: "left", sort: "desc" },
  { title:"食材名", width: "120px", key:"m_name"},
  { title:"单价", width: "80px", key:"price" },
  { title:"库存", width: "120px", key:"stock", sort: "desc" },
  { title:"单位", width: "50px", key:"unit"},
  { title:"操作", width: "150px", customSlot:"operator", key:"operator", fixed: "right" }
])

const model=reactive({
  dataSource:[],
  materialList:[],
  materialAddList:[],
  value:{
    id:undefined,
    name:undefined,
    type:undefined,
    image:undefined,
    price:undefined,
    sales_volume:undefined,
    materialList: []
  }
})

const addMaterial=(row)=>{
  var f=true
  for (var i=0;i<model.dataSource.length;i++){
    if (model.dataSource[i].id==row.id){
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
    layer.notifiy({
      title:"Success",
      content:"添加成功",
      icon:1
    })
    model.dataSource.push(row)
  }
}

const init=()=>{
  console.log(props.id)
  MaterialService.getAllMaterial().then((response)=>{
    model.materialAddList=response.data
  })

  DishesService.getDishesById(props.id).then((response)=>{
    model.value=response.data
    model.dataSource=model.value.materialList
  }).catch((err)=>{
    console.log(err)
  })
}
onMounted(()=>{
  init()
})

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

const remove=(row)=>{
  layer.confirm("您确定要删除吗",
      {
        btn: [
          {text:'确定', callback: (id) => {
              for (var i=0;i<model.dataSource.length;i++) {
                if (model.dataSource[i].id == row.id) {
                  model.dataSource.splice(i, 1)
                }
              }
              layer.close(id); }
          },
          {text:'取消', callback: (id) => {
              layer.close(id); }
          }
        ]
      }
  )
}

const changeVisible=()=>{
  visible.value = !visible.value;
}
const submit=()=>{
  model.value.materialList=model.dataSource
  console.log(model.value)
  DishesService.editDishes(model.value).then(()=>{
    layer.msg("提交成功", { icon : 1, time: 1000})
    router.back()
  })
}

const search=ref()
const searchName=()=>{
  console.log(search.value)
  MaterialService.searchMaterialName(search.value).then((response)=>{
    model.materialAddList=response.data
  })
}

</script>

<template>

  <lay-layer title="食材信息" v-model="visible" :area="['800px', '700px']">
    <div style="padding: 20px;">
      <lay-table
          :height="'500px'"
          :columns="materialColumns"
          :default-toolbar="true"
          :data-source="model.materialAddList">
        <template v-slot:toolbar>
          <lay-row space="10">
            <lay-col md="12"><div class="grid-demo">
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
            <lay-input-number v-model="row.number"  position="right"></lay-input-number>
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
                  <lay-form-item label="菜名" prop="total">
                    <lay-input v-model="model.value.name"></lay-input>
                  </lay-form-item>
                </lay-col>
                <lay-col md="12" sm="24" xs="24">
                  <lay-form-item label="品类" prop="desk">
                    <lay-input v-model="model.value.type"></lay-input>
                  </lay-form-item>
                </lay-col>
                <lay-col md="12" sm="24" xs="24">
                  <lay-form-item label="单价" prop="desk">
                    <lay-input v-model="model.value.price"></lay-input>
                  </lay-form-item>
                </lay-col>
                <lay-col md="12" sm="24" xs="24">
                  <lay-form-item label="销量" prop="desk">
                    <lay-input v-model="model.value.sales_volume"></lay-input>
                  </lay-form-item>
                </lay-col>


              </lay-row>
            </lay-container>
            <lay-form-item style="text-align: center;">
              <lay-button @click="changeVisible">添加食材</lay-button>
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