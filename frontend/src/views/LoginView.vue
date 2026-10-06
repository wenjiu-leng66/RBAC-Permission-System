<script setup>
import { reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { login } from '../stores/auth'

const form = reactive({ account: 'E001', password: '123456' }); const loading = ref(false)
const router = useRouter(); const route = useRoute()
async function submit() {
  loading.value = true
  try { await new Promise(r => setTimeout(r, 400)); await login(form.account, form.password); ElMessage.success('登录成功'); router.push(route.query.redirect || '/dashboard') }
  catch (e) { ElMessage.error(e.message) } finally { loading.value = false }
}
</script>

<template>
  <div class="login-page">
    <div class="login-backdrop"><span class="orb orb-one" /><span class="orb orb-two" /><span class="grid-lines" /></div>
    <div class="login-layout">
      <section class="login-intro">
        <div class="intro-mark">市</div>
        <p class="eyebrow">MUNICIPAL ACCESS PLATFORM</p>
        <h1>市政权限<br><strong>管理系统</strong></h1>
        <p class="intro-copy">统一管理组织、用户、角色与业务权限，让每一次访问都有清晰依据。</p>
        <div class="intro-points"><span>● 统一身份认证</span><span>● 清晰授权关系</span><span>● 可追溯的访问控制</span></div>
      </section>
      <el-card class="login-card" shadow="always">
        <div class="login-card-heading"><span class="card-kicker">欢迎回来</span><h2>登录工作台</h2><p>使用员工号或手机号登录</p></div>
        <el-form :model="form" @submit.prevent="submit" size="large">
          <el-form-item label="登录账号"><el-input v-model="form.account" autocomplete="username" placeholder="请输入员工号或手机号"><template #prefix>◉</template></el-input><span class="field-hint">仅支持员工号和手机号，不使用用户名登录</span></el-form-item>
          <el-form-item label="密码"><el-input v-model="form.password" type="password" show-password autocomplete="current-password" placeholder="请输入密码"><template #prefix>◆</template></el-input></el-form-item>
          <el-button type="primary" native-type="submit" :loading="loading" class="full-width login-button">进入系统 <span class="arrow">→</span></el-button>
        </el-form>
        <div class="demo-tip"><span>演示账号</span><code>E001</code><i>/</i><code>123456</code><span class="demo-or">或</span><code>13800000001</code></div>
        <p class="login-footer">RBAC0 · 内部管理平台</p>
      </el-card>
    </div>
  </div>
</template>
