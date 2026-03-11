<template>
  <div>
    <el-card class="toolbar-card">
      <div class="toolbar">
        <div class="toolbar-left">
          <el-input v-model="search" placeholder="Search by class name..." clearable style="width:260px">
            <template #prefix><el-icon><Search /></el-icon></template>
          </el-input>
          <el-select v-model="filterState" placeholder="Filter by state" clearable style="width:160px">
            <el-option v-for="s in states" :key="s.value" :label="s.label" :value="s.value" />
          </el-select>
        </div>
        <el-button type="primary" :icon="Plus" @click="openDialog()">New Class</el-button>
      </div>
    </el-card>

    <el-card>
      <el-table :data="filteredList" stripe v-loading="loading">
        <el-table-column prop="classNo" label="No." width="80" />
        <el-table-column prop="className" label="Class Name" min-width="160" />
        <el-table-column prop="courseLevel" label="Course Level" width="160" />
        <el-table-column prop="coordinator" label="Coordinator" width="120" />
        <el-table-column prop="startDate" label="Start Date" width="120" />
        <el-table-column prop="endDate" label="End Date" width="120" />
        <el-table-column prop="attendCount" label="Attendees" width="90" align="center" />
        <el-table-column prop="state" label="State" width="120" align="center">
          <template #default="{ row }">
            <el-tag :type="stateType(row.state)" size="small">{{ row.state }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="Actions" width="160" align="center">
          <template #default="{ row }">
            <el-button size="small" @click="openDialog(row)">Edit</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row.classNo)">Delete</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="isEdit ? 'Edit Class' : 'New Class'" width="600px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="120px">
        <el-row :gutter="16">
          <el-col :span="24">
            <el-form-item label="Class Name" prop="className">
              <el-input v-model="form.className" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Coordinator" prop="coordinator">
              <el-input v-model="form.coordinator" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Course Level" prop="courseLevel">
              <el-input v-model="form.courseLevel" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Level" prop="level">
              <el-select v-model="form.level" style="width:100%">
                <el-option label="Branch" value="Branch" />
                <el-option label="Service Dept" value="Service Dept" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Class Days" prop="classDays">
              <el-input-number v-model="form.classDays" :min="1" style="width:100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Start Date" prop="startDate">
              <el-date-picker v-model="form.startDate" type="date" value-format="YYYY-MM-DD" style="width:100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="End Date" prop="endDate">
              <el-date-picker v-model="form.endDate" type="date" value-format="YYYY-MM-DD" style="width:100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Department" prop="department">
              <el-input v-model="form.department" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Location" prop="location">
              <el-input v-model="form.location" />
            </el-form-item>
          </el-col>
        </el-row>
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
import { trainingClassApi } from '../api/index.js'

const loading = ref(false)
const list = ref([])
const search = ref('')
const filterState = ref('')
const dialogVisible = ref(false)
const isEdit = ref(false)
const formRef = ref()

const states = [
  { value: 'PLANNED', label: 'Planned' },
  { value: 'ENROLLING', label: 'Enrolling' },
  { value: 'ONGOING', label: 'Ongoing' },
  { value: 'COMPLETED', label: 'Completed' },
]

const stateType = (s) => ({
  PLANNED: 'info',
  ENROLLING: 'warning',
  ONGOING: 'success',
  COMPLETED: ''
}[s] || 'info')

const emptyForm = () => ({
  classNo: null, className: '', coordinator: '', level: 'Branch',
  courseLevel: '', attendCount: 0, classDays: 1,
  startDate: '', endDate: '', department: '', location: '', state: 'PLANNED'
})

const form = ref(emptyForm())
const rules = {
  className: [{ required: true, message: 'Please enter class name', trigger: 'blur' }],
  coordinator: [{ required: true, message: 'Please enter coordinator', trigger: 'blur' }],
}

const filteredList = computed(() => {
  return list.value.filter(item => {
    const matchName = !search.value || item.className?.includes(search.value)
    const matchState = !filterState.value || item.state === filterState.value
    return matchName && matchState
  })
})

async function loadData() {
  loading.value = true
  try {
    const res = await trainingClassApi.getAll()
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
      await trainingClassApi.update(form.value.classNo, form.value)
    } else {
      await trainingClassApi.create(form.value)
    }
    ElMessage.success(isEdit.value ? 'Updated successfully' : 'Created successfully')
    dialogVisible.value = false
    loadData()
  } catch {
    ElMessage.error('Operation failed')
  }
}

async function handleDelete(id) {
  await ElMessageBox.confirm('Confirm delete this class?', 'Warning', { type: 'warning' })
  try {
    await trainingClassApi.delete(id)
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
.toolbar { display: flex; justify-content: space-between; align-items: center; gap: 12px; }
.toolbar-left { display: flex; gap: 12px; }
</style>