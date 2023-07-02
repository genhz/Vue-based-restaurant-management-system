<script lang="ts" setup>
import { reactive } from 'vue'
import {useStore} from "vuex";
import {PersonnelService} from "../utils/service/personnelService";
import {layer} from "@layui/layer-vue";
import {useRouter} from "vue-router";

// do not use same name with ref
const form = reactive({
  username: '',
  password: '',
  role:''
})



const store=useStore()
const router=useRouter()
const onSubmit = () => {
  let user={
    username:form.username,
    password:form.password,
  }
  PersonnelService.login(user).then((response)=>{
    user=response.data
    if (JSON.stringify(user) === '""'){
      layer.msg("账号或密码错误", { icon : 7, time: 1000})
    }else {
      store.commit('userStore/setUser',user)
      const role=store.state.userStore.user.role
      console.log(role)
      if (role=='admin'){
        router.push({path:'/admin'})
      }else if (role=='counter'){
        router.push({path:'/counter'})
      }
      else if (role=='procure'){
        router.push({path:'procure'})
      }else {
        layer.msg("权限错误", { icon : 7, time: 1000})
      }
      layer.msg("登录成功", { icon : 1, time: 1000})
    }
  })

}

</script>

<template>
  <div class="card-container">
    <lay-card title="欢迎使用后台管理系统" class="inner">
      <lay-form :model="form" label-position="top">
        <lay-form-item label="账户" prop="username">
          <lay-input v-model="form.username"></lay-input>
        </lay-form-item>
        <lay-form-item label="密码" prop="password">
          <lay-input v-model="form.password" type="password"></lay-input>
        </lay-form-item>
        <lay-form-item style="text-align: center;">
          <lay-button type="primary" @click="onSubmit">提交</lay-button>
          <lay-button type="default" @click="">重置</lay-button>
        </lay-form-item>
      </lay-form>
    </lay-card>
  </div>



</template>



<style scoped>
.inner{
  margin:200px auto;
  height:400px;
  width:500px;
}
.card-container {
  height:100%;
  //background: whitesmoke;
  background-image: url("src/assets/img/admin.jpg");
  background-repeat: no-repeat;
  background-size: 100%;
  padding: 20px;
}
</style>