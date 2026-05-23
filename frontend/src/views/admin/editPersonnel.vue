<script setup>
import {onMounted, reactive} from "vue";
import {useRouter} from "vue-router";
import {PersonnelService} from "@/utils/service/personnelService";
import {layer} from "@layui/layer-vue";

const router=useRouter()

const props=defineProps(['id'])

const model=reactive({
  value:{
    p_name:undefined,
    username:undefined,
    password:undefined,
    phone:undefined,
    p_type:undefined,
    b_salary:undefined,
    role:undefined
  }

})
const init=()=>{
  console.log(props.id)
  PersonnelService.getPersonnelById(props.id).then((response)=>{
    model.value=response.data
    console.log(model.value)
  })
}

onMounted(()=>{
  init()
})

const submit=()=>{
  console.log(model.value)
  PersonnelService.editPersonnel(model.value).then((response)=>{
    if (response.data){
      layer.msg("修改成功", { icon : 1, time: 1000})
      router.back()
    }else {
      layer.msg("账号已存在", { icon : 7, time: 1000})
    }
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
      <lay-page-header content="修改员工信息" @back="handleBack"></lay-page-header>
    </template>
    <template v-slot:body>
      <lay-form :model="model.value">
        <lay-container fluid>
          <lay-row space="10">



            <lay-col md="12" sm="24" xs="24">
              <lay-form-item label="员工姓名" prop="total">
                <lay-input v-model="model.value.p_name"></lay-input>
              </lay-form-item>
            </lay-col>
            <lay-col md="12" sm="24" xs="24">
              <lay-form-item label="用户名" prop="desk">
                <lay-input v-model="model.value.username"></lay-input>
              </lay-form-item>
            </lay-col>
            <lay-col md="12" sm="24" xs="24">
              <lay-form-item label="密码" prop="desk">
                <lay-input v-model="model.value.password" type="password"></lay-input>
              </lay-form-item>
            </lay-col>
            <lay-col md="12" sm="24" xs="24">
              <lay-form-item label="联系方式" prop="desk">
                <lay-input v-model="model.value.phone"></lay-input>
              </lay-form-item>
            </lay-col>
            <lay-col md="12" sm="24" xs="24">
              <lay-form-item label="职位" prop="desk">
                <lay-radio-group name="p_type" v-model="model.value.p_type">
                  <lay-radio value="服务员">服务员</lay-radio>
                  <lay-radio value="前台">前台</lay-radio>
                  <lay-radio value="厨师">厨师</lay-radio>
                  <lay-radio value="采购人员">采购人员</lay-radio>
                  <lay-radio value="管理员">管理员</lay-radio>
                </lay-radio-group>
              </lay-form-item>
            </lay-col>
            <lay-col md="12" sm="24" xs="24">
              <lay-form-item label="权限" prop="desk">
                <lay-radio-group name="role" v-model="model.value.role">
                  <lay-radio value="counter">counter</lay-radio>
                  <lay-radio value="admin">admin</lay-radio>
                  <lay-radio value="user">user</lay-radio>
                  <lay-radio value="procure">procure</lay-radio>
                </lay-radio-group>
              </lay-form-item>
            </lay-col>
            <lay-col md="12" sm="24" xs="24">
              <lay-form-item label="基本薪资" prop="desk">
                <lay-input v-model="model.value.b_salary"></lay-input>
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