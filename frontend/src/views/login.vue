<script setup>
import { reactive, ref } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import { PersonnelService } from '../utils/service/personnelService'
import { ElMessage } from 'element-plus'

const form = reactive({
  username: '',
  password: ''
})

const loading = ref(false)
const store = useStore()
const router = useRouter()

const onSubmit = async () => {
  if (!form.username || !form.password) {
    ElMessage.warning('请输入账号和密码')
    return
  }

  loading.value = true
  try {
    const response = await PersonnelService.login({
      username: form.username,
      password: form.password
    })
    const user = response.data

    if (!user || !user.role) {
      ElMessage.error('账号或密码错误')
      return
    }

    store.commit('userStore/setUser', user)
    const role = user.role

    const roleRedirects = {
      admin: '/admin',
      counter: '/counter',
      procure: '/procure'
    }

    const targetPath = roleRedirects[role]
    if (targetPath) {
      ElMessage.success('登录成功')
      router.push({ path: targetPath })
    } else {
      ElMessage.error('账号权限异常，请联系管理员')
    }
  } catch (err) {
    ElMessage.error('登录失败，请检查网络连接')
  } finally {
    loading.value = false
  }
}

const onReset = () => {
  form.username = ''
  form.password = ''
}
</script>

<template>
  <div class="login-container">
    <div class="login-card">
      <h2 class="login-title">荣成餐饮店管理系统</h2>
      <lay-form :model="form" label-position="top">
        <lay-form-item label="账号" prop="username">
          <lay-input v-model="form.username" placeholder="请输入账号"></lay-input>
        </lay-form-item>
        <lay-form-item label="密码" prop="password">
          <lay-input v-model="form.password" type="password" placeholder="请输入密码"
            @keyup.enter="onSubmit"></lay-input>
        </lay-form-item>
        <lay-form-item style="text-align: center;">
          <lay-button type="primary" @click="onSubmit" :loading="loading">登录</lay-button>
          <lay-button @click="onReset">重置</lay-button>
        </lay-form-item>
      </lay-form>
      <div class="login-hint">
        <p>演示账号：admin / admin（管理员）</p>
        <p>演示账号：counter / counter（前台）</p>
        <p>演示账号：procure / procure（采购）</p>
      </div>
    </div>
  </div>
</template>

<style scoped>
.login-container {
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.login-card {
  width: 420px;
  padding: 40px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.15);
}

.login-title {
  text-align: center;
  margin-bottom: 30px;
  color: #303133;
  font-size: 22px;
}

.login-hint {
  margin-top: 20px;
  padding-top: 16px;
  border-top: 1px solid #ebeef5;
  text-align: center;
}

.login-hint p {
  margin: 4px 0;
  font-size: 12px;
  color: #909399;
}
</style>
