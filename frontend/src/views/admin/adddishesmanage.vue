<script lang="ts" setup>

import {reactive, ref, toRaw, watch} from "vue";
import {DishesService} from "../../utils/service/dishesService";
import {useRouter} from "vue-router";
import { genFileId } from 'element-plus'
import type { UploadInstance, UploadProps, UploadRawFile } from 'element-plus'
import {layer} from "@layui/layer-vue";


const size = ref('default')
const labelPosition = ref('left')

const router=useRouter()

const columns = ref([
  { title:"编号", width: "80px", key:"id", fixed: "left", sort: "desc" },
  { title:"食材名", width: "120px", key:"m_name"},
  { title:"数量", width: "50px", key:"number"},
  { title:"单价", width: "80px", key:"price" },
  { title:"单位", width: "50px", key:"unit"},
  { title:"库存", width: "120px", key:"stock", sort: "desc" },
  { title:"安全库存", width: "120px", key:"safety_stock"},
  { title:"供应商", width: "240px", key:"supplier" },
  { title:"操作", width: "150px", customSlot:"operator", key:"operator", fixed: "right" }
]);

const dataSource = ref([]);

const model=reactive({
    name:'',
    type:'',
    image:'',
    price:undefined,
    sales_volume:undefined,
    state:true,
})

const submit=()=>{
  submitUpload()
  layer.msg("提交成功", { icon : 1, time: 1000})
}

const upload = ref<UploadInstance>()


const handleExceed: UploadProps['onExceed'] = (files) => {
  upload.value!.clearFiles()
  const file = files[0] as UploadRawFile
  file.uid = genFileId()
  upload.value!.handleStart(file)
}

const submitUpload = () => {
  upload.value!.submit()
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
      <lay-page-header content="添加菜品" @back="handleBack"></lay-page-header>
    </template>
    <template v-slot:body>
      <lay-form :model="model">
        <lay-container fluid>
          <lay-row space="10">
            <lay-col md="12" sm="24" xs="24">
              <lay-form-item required label="菜名" prop="total">
                <lay-input v-model="model.name"></lay-input>
              </lay-form-item>
            </lay-col>
            <lay-col md="12" sm="24" xs="24">
              <lay-form-item label="品类" prop="desk" required>
                <lay-input v-model="model.type"></lay-input>
              </lay-form-item>
            </lay-col>
            <lay-col md="12" sm="24" xs="24">
              <lay-form-item label="单价" prop="desk" required>
                <lay-input v-model="model.price"></lay-input>
              </lay-form-item>
            </lay-col>
            <lay-col md="12" sm="24" xs="24">
              <lay-form-item label="销量" prop="desk" required>
                <lay-input v-model="model.sales_volume"></lay-input>
              </lay-form-item>
            </lay-col>
            <lay-col md="12" sm="24" xs="24">

            </lay-col>
          </lay-row>
        </lay-container>
        <lay-form-item style="text-align: center;">
          <el-upload
              ref="upload"
              class="upload-demo"
              action="http://localhost:8080/addDishes"
              accept=".JPG"
              :data="model"
              :limit="1"
              :on-exceed="handleExceed"
              :auto-upload="false"
          >
            <template #trigger>
              <el-button type="primary">选择菜品图片</el-button>
            </template>
            <template #tip>
              <div class="el-upload__tip text-red">
                只能上传一个文件
              </div>
            </template>
          </el-upload><lay-button type="primary" @click="submit">提交</lay-button>
        </lay-form-item>
      </lay-form>

<!--      :data="{ name: model.name ,type:model.type, price:model.price,sales_volume:model.sales_volume,state:model.state}"-->

<!--      <el-form-item>-->
<!--        <el-button class="ml-3" type="success" @click="submitUpload">-->
<!--          提交信息-->
<!--        </el-button>-->
<!--      </el-form-item>-->

    </template>
  </lay-card>
</template>

<style scoped>

</style>