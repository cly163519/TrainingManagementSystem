<template>
  <div class="login-wrapper">
    <div class="login-box">
      <h2>Training Management System</h2>
      <el-form :model="form" label-width="80px">
        <el-form-item label="Username">
          <el-input v-model="form.username" />
        </el-form-item>
        <el-form-item label="Password">
          <el-input v-model="form.password" type="password" show-password />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" style="width:100%" @click="handleLogin">
            Login
          </el-button>
        </el-form-item>
        <p v-if="errorMsg" style="color:red;text-align:center">{{ errorMsg }}</p>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { login } from '../api/index.js'

const router = useRouter()
const form = ref({ username: '', password: '' })
const errorMsg = ref('')

async function handleLogin() {
  try {
    const res = await login(form.value)
    if (res.data.success) {
      router.push('/')
    } else {
      errorMsg.value = res.data.message
    }
  } catch (e) {
    errorMsg.value = 'Login failed, please try again'
  }
}
</script>

<style scoped>
.login-wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: #f0f2f5;
}
.login-box {
  background: white;
  padding: 40px;
  border-radius: 8px;
  width: 400px;
  box-shadow: 0 2px 12px rgba(0,0,0,0.1);
}
h2 {
  text-align: center;
  margin-bottom: 24px;
}
</style>