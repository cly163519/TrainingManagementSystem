<template>
  <el-container class="app-wrapper">
    <el-aside width="220px" class="sidebar">
      <div class="logo">
        <el-icon size="22"><School /></el-icon>
        <span>TMS</span>
      </div>
      <el-menu
          :default-active="$route.path"
          router
          background-color="#1a1f2e"
          text-color="#a8b3cf"
          active-text-color="#ffffff"
      >
        <el-menu-item index="/training-class">
          <el-icon><Collection /></el-icon>
          <span>Training Class</span>
        </el-menu-item>
        <el-menu-item index="/trainee">
          <el-icon><User /></el-icon>
          <span>Trainee</span>
        </el-menu-item>
        <el-menu-item index="/trainer">
          <el-icon><UserFilled /></el-icon>
          <span>Trainer</span>
        </el-menu-item>
        <el-menu-item index="/enrollment">
          <el-icon><DocumentAdd /></el-icon>
          <span>Enrollment</span>
        </el-menu-item>
        <el-menu-item index="/course-file">
          <el-icon><FolderOpened /></el-icon>
          <span>Course File</span>
        </el-menu-item>
      </el-menu>
    </el-aside>

    <el-container>
      <el-header class="topbar">
        <span class="page-title">{{ pageTitle }}</span>
      </el-header>
      <el-main class="main-content">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const titleMap = {
  '/training-class': 'Training Class',
  '/trainee': 'Trainee',
  '/trainer': 'Trainer',
  '/enrollment': 'Enrollment',
  '/course-file': 'Course File',
}
const pageTitle = computed(() => titleMap[route.path] || 'TMS')
</script>

<style>
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
  }
body {
      font-family: 'PingFang SC', 'Microsoft YaHei', sans-serif;
      background: #f5f7fa;
      }
.app-wrapper
      {
        height: 100vh;
      }
.sidebar {
          background: #1a1f2e;
          display: flex;
          flex-direction: column;
          }
.logo {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 20px;
  color: #ffffff;
  font-size: 16px;
  font-weight: 600;
  border-bottom: 1px solid #2d3548;
}
.el-menu {
  border-right: none;
  }
.el-menu-item.is-active {
  background: #3b6ef0 !important;
  }
.topbar {
  background: #ffffff;
  border-bottom: 1px solid #e8ecf4;
  display: flex;
  align-items: center;
  padding: 0 24px;
  height: 60px;
}
.page-title {
  font-size: 16px;
  font-weight: 600;
  color: #1a1f2e;
}
.main-content {
  background: #f5f7fa;
  padding: 24px;
}
.main-content {
  background: #f5f7fa;
  padding: 24px;
  max-width: 1400px;
}

.el-card {
  margin-bottom: 16px;
}

.el-table {
  font-size: 14px;
}


</style>