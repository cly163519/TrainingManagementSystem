<template>
  <div>
    <el-card class="toolbar-card">
      <div class="toolbar">
        <div class="toolbar-left">
          <el-input v-model="search" placeholder="Search by class name..." clearable style="width:220px">
            <template #prefix><el-icon><Search /></el-icon></template>
          </el-input>
          <el-select v-model="filterState" placeholder="Filter by state" clearable style="width:150px">
            <el-option label="Planned" value="PLANNED" />
            <el-option label="Enrolling" value="ENROLLING" />
            <el-option label="Ongoing" value="ONGOING" />
            <el-option label="Completed" value="COMPLETED" />
          </el-select>
          <el-select v-model="filterLevel" placeholder="Filter by level" clearable style="width:150px">
            <el-option label="Branch Company" value="BRANCH COMPANY" />
            <el-option label="Sub Branch" value="SUB BRANCH" />
            <el-option label="Service Dept" value="SERVICE DEPT" />
          </el-select>
        </div>
        <el-button type="primary" :icon="Plus" @click="openDialog()">New Class</el-button>
      </div>
    </el-card>

    <el-card>
      <el-table :data="filteredList" stripe v-loading="loading">
        <el-table-column prop="classNo" label="No." width="60" />
        <el-table-column prop="className" label="Class Name" width="150" />
        <el-table-column prop="courseLevel" label="Course Level" width="140" />
        <el-table-column prop="coordinator" label="Coordinator" width="120" />
        <el-table-column prop="startDate" label="Start Date" width="110" />
        <el-table-column prop="endDate" label="End Date" width="110" />
        <el-table-column prop="attendCount" label="Attendees" width="90" align="center" />
        <el-table-column prop="state" label="State" width="110" align="center">
          <template #default="{ row }">
            <el-tag :type="stateType(row.state)" size="small">{{ stateLabel(row.state) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="Actions" width="280" align="center">
          <template #default="{ row }">
            <el-button size="small" @click="openDialog(row)">Edit</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row.classNo)">Delete</el-button>
            <el-button size="small" type="warning" v-if="row.state === 'PLANNED'">Enroll</el-button>
            <el-button size="small" type="success" v-if="row.state === 'ENROLLING'">Start</el-button>
            <el-button size="small" type="info" v-if="row.state === 'ONGOING'">Complete</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="isEdit ? 'Edit Class' : 'New Class'" width="600px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="110px">
        <el-row :gutter="16">
          <el-col :span="12">
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
            <el-form-item label="Course Level">
              <el-input v-model="form.courseLevel" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Level">
              <el-select v-model="form.level" style="width:100%">
                <el-option label="Branch Company" value="BRANCH COMPANY" />
                <el-option label="Sub Branch" value="SUB BRANCH" />
                <el-option label="Service Dept" value="SERVICE DEPT" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Start Date">
              <el-date-picker v-model="form.startDate" type="date" value-format="YYYY-MM-DD" style="width:100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="End Date">
              <el-date-picker v-model="form.endDate" type="date" value-format="YYYY-MM-DD" style="width:100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Class Days">
              <el-input-number v-model="form.classDays" :min="1" style="width:100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Attendees">
              <el-input-number v-model="form.attendCount" :min="0" style="width:100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Department">
              <el-input v-model="form.department" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Location">
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
const filterLevel = ref('')
const dialogVisible = ref(false)
const isEdit = ref(false)
const formRef = ref()

const emptyForm = () => ({
  classNo: null, className: '', coordinator: '', courseLevel: '',
  level: 'BRANCH COMPANY', classDays: 1, attendCount: 0,
  startDate: '', endDate: '', department: '', location: '', state: 'PLANNED'
})

const form = ref(emptyForm())
const rules = {
  className: [{ required: true, message: 'Please enter class name', trigger: 'blur' }],
  coordinator: [{ required: true, message: 'Please enter coordinator', trigger: 'blur' }],
}

const states = [
  { value: 'PLANNED', label: 'Planned' },
  { value: 'ENROLLING', label: 'Enrolling' },
  { value: 'ONGOING', label: 'Ongoing' },
  { value: 'COMPLETED', label: 'Completed' },
]

const stateLabel = (s) => states.find(x => x.value === s)?.label || s
const stateType = (s) => ({ PLANNED: 'info', ENROLLING: 'warning', ONGOING: 'success', COMPLETED: '' }[s] || 'info')

const filteredList = computed(() =>
    list.value.filter(item => {
      const matchName = !search.value || item.className?.includes(search.value)
      const matchState = !filterState.value || item.state === filterState.value
      const matchLevel = !filterLevel.value || item.level === filterLevel.value
      return matchName && matchState && matchLevel
    })
)

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
.toolbar { display: flex; justify-content: space-between; align-items: center; }
.toolbar-left { display: flex; gap: 12px; }
</style>