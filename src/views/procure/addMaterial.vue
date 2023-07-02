<script setup>
import {reactive} from "vue";
import {MaterialService} from "@/utils/service/MaterialService";
import {useRouter} from "vue-router";
import {layer} from "@layui/layer-vue";

const router=useRouter()

const model=reactive({
  m_name:'',
  supplier:'',
  price:undefined,
  unit:'',
  batch:undefined,
  safety_stock:undefined,
  stock:undefined
})

const submit=()=>{
  MaterialService.addMaterial(model).then(()=>{
    layer.msg("添加成功", { icon : 1, time: 1000})
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
      <lay-page-header content="添加食材" @back="handleBack"></lay-page-header>
    </template>
    <template v-slot:body>
      <lay-form :model="model">
        <lay-container fluid>
          <lay-row space="10">
            <lay-col md="12" sm="24" xs="24">
              <lay-form-item label="食材名" prop="total" required>
                <lay-input v-model="model.m_name" placeholder="请输入食材名"></lay-input>
              </lay-form-item>
            </lay-col>
            <lay-col md="12" sm="24" xs="24">
              <lay-form-item label="供应商" prop="desk" required>
                <lay-input v-model="model.supplier" placeholder="请输入供应商"></lay-input>
              </lay-form-item>
            </lay-col>
            <lay-col md="12" sm="24" xs="24">
              <lay-form-item label="单价" prop="desk" required>
                <lay-input v-model="model.price" placeholder="请输入单价"></lay-input>
              </lay-form-item>
            </lay-col>
            <lay-col md="12" sm="24" xs="24">
              <lay-form-item label="单位" prop="desk" required>
                <lay-input v-model="model.unit" placeholder="请输入计量单位"></lay-input>
              </lay-form-item>
            </lay-col>
            <lay-col md="12" sm="24" xs="24">
              <lay-form-item label="固定批量" prop="desk" required>
                <lay-input v-model="model.batch" placeholder="请输入进货时的固定批量"></lay-input>
              </lay-form-item>
            </lay-col>
            <lay-col md="12" sm="24" xs="24">
              <lay-form-item label="安全库存" prop="desk" required>
                <lay-input v-model="model.safety_stock" placeholder="请输入需要进货时的安全库存"></lay-input>
              </lay-form-item>
            </lay-col>
            <lay-col md="12" sm="24" xs="24">
              <lay-form-item label="库存" prop="desk" required>
                <lay-input v-model="model.stock" placeholder="请输入现有库存"></lay-input>
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