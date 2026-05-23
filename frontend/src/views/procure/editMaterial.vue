<script setup>
import {onMounted, reactive} from "vue";
import {MaterialService} from "@/utils/service/MaterialService";
import {useRouter} from "vue-router";
import {layer} from "@layui/layer-vue";

const router=useRouter()

const props=defineProps(['id'])

const model=reactive({
  value:{
    m_name:'',
    supplier:'',
    price:undefined,
    unit:'',
    batch:undefined,
    safety_stock:undefined,
    stock:undefined
  }

})
const init=()=>{
  console.log(props.id)
  MaterialService.getAllMaterialById(props.id).then((response)=>{
    model.value=response.data
    console.log(model.value)
  })
}

onMounted(()=>{
  init()
})

const submit=()=>{
  console.log(model.value)
  MaterialService.editMaterial(model.value).then(()=>{
    layer.msg("修改成功", { icon : 1, time: 1000})
    router.back()
  })
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
</script>

<template>
  <lay-card>
    <template v-slot:title>
      <lay-page-header content="修改食材" @back="handleBack"></lay-page-header>
    </template>
    <template v-slot:body>
      <lay-form :model="model.value">
        <lay-container fluid>
          <lay-row space="10">
            <lay-col md="12" sm="24" xs="24">
              <lay-form-item label="食材名" prop="total">
                <lay-input v-model="model.value.m_name"></lay-input>
              </lay-form-item>
            </lay-col>
            <lay-col md="12" sm="24" xs="24">
              <lay-form-item label="供应商" prop="desk">
                <lay-input v-model="model.value.supplier"></lay-input>
              </lay-form-item>
            </lay-col>
            <lay-col md="12" sm="24" xs="24">
              <lay-form-item label="单价" prop="desk">
                <lay-input v-model="model.value.price"></lay-input>
              </lay-form-item>
            </lay-col>
            <lay-col md="12" sm="24" xs="24">
              <lay-form-item label="单位" prop="desk">
                <lay-input v-model="model.value.unit"></lay-input>
              </lay-form-item>
            </lay-col>
            <lay-col md="12" sm="24" xs="24">
              <lay-form-item label="固定批量" prop="desk">
                <lay-input v-model="model.value.batch"></lay-input>
              </lay-form-item>
            </lay-col>
            <lay-col md="12" sm="24" xs="24">
              <lay-form-item label="安全库存" prop="desk">
                <lay-input v-model="model.value.safety_stock"></lay-input>
              </lay-form-item>
            </lay-col>
            <lay-col md="12" sm="24" xs="24">
              <lay-form-item label="库存" prop="desk">
                <lay-input v-model="model.value.stock"></lay-input>
              </lay-form-item>
            </lay-col>
          </lay-row>
        </lay-container>
        <lay-form-item style="text-align: center;">
          <lay-button type="primary" @click="submit">提交</lay-button>
        </lay-form-item>
      </lay-form>

    </template>
  </lay-card>
</template>

<style scoped>

</style>