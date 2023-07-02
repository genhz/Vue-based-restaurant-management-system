<script setup>
import {reactive} from "vue";
import {useRouter} from "vue-router";
import {PersonnelService} from "@/utils/service/personnelService";
import {layer} from "@layui/layer-vue";

const router=useRouter()

const model=reactive({
  p_name:undefined,
  username:undefined,
  password:undefined,
  phone:undefined,
  p_type:undefined,
  b_salary:undefined,
  role:undefined
})

const submit=()=>{
  PersonnelService.addPersonnel(model).then(()=>{
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
      <lay-page-header content="添加员工" @back="handleBack"></lay-page-header>
    </template>
    <template v-slot:body>
      <lay-form :model="model">
        <lay-container fluid>
          <lay-row space="10">

            <lay-col md="12" sm="24" xs="24">
              <lay-form-item label="员工姓名" prop="total" required>
                <lay-input v-model="model.p_name" placeholder="请输入员工姓名"></lay-input>
              </lay-form-item>
            </lay-col>
            <lay-col md="12" sm="24" xs="24">
              <lay-form-item label="用户名" prop="desk" required>
                <lay-input v-model="model.username" placeholder="用于用户登录系统"></lay-input>
              </lay-form-item>
            </lay-col>
            <lay-col md="12" sm="24" xs="24">
              <lay-form-item label="密码" prop="desk" required>
                <lay-input type="password" v-model="model.password" placeholder="用于用户登录系统"></lay-input>
              </lay-form-item>
            </lay-col>
            <lay-col md="12" sm="24" xs="24">
              <lay-form-item label="联系方式" prop="desk" required>
                <lay-input v-model="model.phone" placeholder="请输入11位国内手机号码"></lay-input>
              </lay-form-item>
            </lay-col>
            <lay-col md="12" sm="24" xs="24">
              <lay-form-item label="职位" prop="desk" required>
                <lay-radio-group name="p_type" v-model="model.p_type">
                  <lay-radio value="服务员">服务员</lay-radio>
                  <lay-radio value="前台">前台</lay-radio>
                  <lay-radio value="厨师">厨师</lay-radio>
                  <lay-radio value="采购人员">采购人员</lay-radio>
                  <lay-radio value="管理员">管理员</lay-radio>
                </lay-radio-group>
              </lay-form-item>
            </lay-col>
            <lay-col md="12" sm="24" xs="24">
              <lay-form-item label="权限" prop="desk" required>
                <lay-radio-group name="role" v-model="model.role">
                  <lay-radio value="counter">counter</lay-radio>
                  <lay-radio value="admin">admin</lay-radio>
                  <lay-radio value="user">user</lay-radio>
                  <lay-radio value="procure">procure</lay-radio>
                </lay-radio-group>
              </lay-form-item>
            </lay-col>
            <lay-col md="12" sm="24" xs="24">
              <lay-form-item label="基本薪资" prop="desk" required>
                <lay-input v-model="model.b_salary" placeholder="请输入每月的基本薪资"></lay-input>
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