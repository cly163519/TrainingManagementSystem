<template>
  <div>
    <el-card class="toolbar-card">
      <div class="toolbar">
        <el-select v-model="filterClassId" placeholder="Filter by class" clearable style="width:260px">
          <el-option v-for="c in classList" :key="c.classNo" :label="c.className" :value="c.classNo" />
        </el-select>
        <el-button type="primary" :icon="Plus" @click="openDialog()">New Enrollment</el-button>
      </div>
    </el-card>

    <el-card>
      <el-table :data="filteredList" stripe v-loading="loading"  style="width:100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="trainingClassId" label="Class" width="200">
          <template #default="{ row }">
            {{ classList.find(c => c.classNo === row.trainingClassId)?.className || row.trainingClassId }}
          </template>
        </el-table-column>
        <el-table-column prop="traineeId" label="Trainee" width="160">
          <template #default="{ row }">
            {{ traineeList.find(t => t.salesId === row.traineeId)?.name || row.traineeId }}
          </template>
        </el-table-column>
        <el-table-column prop="enrollDate" label="Enroll Date" width="130" />
        <el-table-column prop="attendanceStatus" label="Attendance" width="130" align="center">
          <template #default="{ row }">
            <el-tag v-if="row.attendanceStatus === 'PRESENT'" type="success" size="small">Present</el-tag>
            <el-tag v-else-if="row.attendanceStatus === 'ABSENT'" type="danger" size="small">Absent</el-tag>
            <el-tag v-else type="info" size="small">Not recorded</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="Actions" min-width="160" align="center">
          <template #default="{ row }">
            <el-button size="small" @click="openDialog(row)">Edit</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row.id)">Delete</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="isEdit ? 'Edit Enrollment' : 'New Enrollment'" width="480px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="130px">
        <el-form-item label="Training Class" prop="trainingClassId">
          <el-select v-model="form.trainingClassId" style="width:100%">
            <el-option v-for="c in classList" :key="c.classNo" :label="c.className" :value="c.classNo" />
          </el-select>
        </el-form-item>
        <el-form-item label="Trainee" prop="traineeId">
          <el-select v-model="form.traineeId" filterable style="width:100%">
            <el-option v-for="t in traineeList" :key="t.salesId" :label="t.name" :value="t.salesId" />
          </el-select>
        </el-form-item>
        <el-form-item label="Enroll Date" prop="enrollDate">
          <el-date-picker v-model="form.enrollDate" type="date" value-format="YYYY-MM-DD" style="width:100%" />
        </el-form-item>
        <el-form-item label="Attendance">
          <el-select v-model="form.attendanceStatus" clearable style="width:100%">
            <el-option label="Present" value="PRESENT" />
            <el-option label="Absent" value="ABSENT" />
          </el-select>
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
import { Plus } from '@element-plus/icons-vue'
import { enrollmentApi, trainingClassApi, traineeApi } from '../api/index.js'

const loading = ref(false)
const list = ref([])
const classList = ref([])
const traineeList = ref([])
const filterClassId = ref(null)
const dialogVisible = ref(false)
const isEdit = ref(false)
const formRef = ref()

const emptyForm = () => ({ id: null, trainingClassId: null, traineeId: null, enrollDate: '', attendanceStatus: '' })
const form = ref(emptyForm())
const rules = {
  trainingClassId: [{ required: true, message: 'Please select a class', trigger: 'change' }],
  traineeId: [{ required: true, message: 'Please select a trainee', trigger: 'change' }],
  enrollDate: [{ required: true, message: 'Please select enroll date', trigger: 'change' }],
}

const filteredList = computed(() =>
    filterClassId.value ? list.value.filter(i => i.trainingClassId === filterClassId.value) : list.value
)

async function loadData() {
  loading.value = true
  try {
    const [e, c, t] = await Promise.all([
      enrollmentApi.getAll(),
      trainingClassApi.getAll(),
      traineeApi.getAll(),
    ])
    list.value = e.data
    classList.value = c.data
    traineeList.value = t.data
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
      await enrollmentApi.update(form.value.id, form.value)
    } else {
      await enrollmentApi.create(form.value)
    }
    ElMessage.success(isEdit.value ? 'Updated successfully' : 'Created successfully')
    dialogVisible.value = false
    loadData()
  } catch {
    ElMessage.error('Operation failed')
  }
}

async function handleDelete(id) {
  await ElMessageBox.confirm('Confirm delete this enrollment?', 'Warning', { type: 'warning' })
  try {
    await enrollmentApi.delete(id)
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