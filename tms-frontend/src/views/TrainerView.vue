<template>
  <div>
    <el-card class="toolbar-card">
      <div class="toolbar">
        <el-input v-model="search" placeholder="Search by name..." clearable style="width:260px">
          <template #prefix><el-icon><Search /></el-icon></template>
        </el-input>
        <el-button type="primary" :icon="Plus" @click="openDialog()">New Trainer</el-button>
      </div>
    </el-card>

    <el-card>
      <el-table :data="filteredList" stripe v-loading="loading"  style="width:100%">
        <el-table-column prop="trainerId" label="ID" width="80" />
        <el-table-column prop="name" label="Name" width="120" />
        <el-table-column prop="courseName" label="Course Name" min-width="200" />
        <el-table-column prop="branch" label="Branch" width="160" />
        <el-table-column label="Actions" min-width="160" align="center">
          <template #default="{ row }">
            <el-button size="small" @click="openDialog(row)">Edit</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row.trainerId)">Delete</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="isEdit ? 'Edit Trainer' : 'New Trainer'" width="480px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="120px">
        <el-form-item label="Name" prop="name">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="Course Name" prop="courseName">
          <el-input v-model="form.courseName" />
        </el-form-item>
        <el-form-item label="Branch" prop="branch">
          <el-input v-model="form.branch" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="handleSubmit">Confirm</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Plus, Search } from '@element-plus/icons-vue'
import { trainerApi } from '../api/index.js'

const loading = ref(false)
const list = ref([])
const search = ref('')
const dialogVisible = ref(false)
const isEdit = ref(false)
const formRef = ref()

const emptyForm = () => ({ trainerId: null, name: '', courseName: '', branch: '' })
const form = ref(emptyForm())
const rules = {
  name: [{ required: true, message: 'Please enter name', trigger: 'blur' }],
  courseName: [{ required: true, message: 'Please enter course name', trigger: 'blur' }],
}

const filteredList = computed(() =>
    list.value.filter(i => !search.value || i.name?.includes(search.value))
)

async function loadData() {
  loading.value = true
  try {
    const res = await trainerApi.getAll()
    list.value = res.data
  } catch {
    ElMessage.error('Failed to load data')
  } finally {
    loading.value = false
  }
}

function openDialog(row = null) {
  isEdit.value = !!row
  form.value = row ? { ...row } : emptyForm()
  dialogVisible.value = true
}

async function handleSubmit() {
  await formRef.value.validate()
  try {
    if (isEdit.value) {
      await trainerApi.update(form.value.trainerId, form.value)
    } else {
      await trainerApi.create(form.value)
    }
    ElMessage.success(isEdit.value ? 'Updated successfully' : 'Created successfully')
    dialogVisible.value = false
    loadData()
  } catch {
    ElMessage.error('Operation failed')
  }
}

async function handleDelete(id) {
  await ElMessageBox.confirm('Confirm delete this trainer?', 'Warning', { type: 'warning' })
  try {
    await trainerApi.delete(id)
    ElMessage.success('Deleted successfully')
    loadData()
  } catch {
    ElMessage.error('Delete failed')
  }
}

onMounted(loadData)
</script>

<style scoped>
.toolbar-card { margin-bottom: 16px; }
.toolbar { display: flex; justify-content: space-between; align-items: center; }
</style>