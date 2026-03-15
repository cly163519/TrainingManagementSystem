<template>
  <div>
    <el-card class="toolbar-card">
      <div class="toolbar">
        <el-input v-model="search" placeholder="Search by name..." clearable style="width:260px">
          <template #prefix><el-icon><Search /></el-icon></template>
        </el-input>

        <div class="toolbar-right">
        <el-upload
            :show-file-list="false"
            :before-upload="handleImport"
            accept=".xlsx,.xls"
        >
          <el-button :icon="Upload">Import Excel</el-button>
        </el-upload>
          <el-button type="primary" :icon="Plus" @click="openDialog()">New Trainee</el-button>
          </div>
      </div>
    </el-card>

    <el-card>
      <el-table :data="filteredList" stripe v-loading="loading">
        <el-table-column prop="salesId" label="ID" width="80" />
        <el-table-column prop="name" label="Name" width="120" />
        <el-table-column prop="branch" label="Branch" min-width="160" />
        <el-table-column prop="level" label="Level" width="120" />
        <el-table-column prop="entryDate" label="Entry Date" width="130" />
        <el-table-column label="Actions" width="160" align="center">
          <template #default="{ row }">
            <el-button size="small" @click="openDialog(row)">Edit</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row.salesId)">Delete</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="isEdit ? 'Edit Trainee' : 'New Trainee'" width="480px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="120px">
        <el-form-item label="Name" prop="name">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="Branch" prop="branch">
          <el-input v-model="form.branch" />
        </el-form-item>
        <el-form-item label="Level" prop="level">
          <el-select v-model="form.level" style="width:100%">
            <el-option label="Branch" value="Branch" />
            <el-option label="Service Dept" value="Service Dept" />
          </el-select>
        </el-form-item>
        <el-form-item label="Entry Date" prop="entryDate">
          <el-date-picker v-model="form.entryDate" type="date" value-format="YYYY-MM-DD" style="width:100%" />
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
import { Plus, Search, Upload } from '@element-plus/icons-vue'
import { traineeApi } from '../api/index.js'
import * as XLSX from 'xlsx'

const loading = ref(false)
const list = ref([])
const search = ref('')
const dialogVisible = ref(false)
const isEdit = ref(false)
const formRef = ref()

const emptyForm = () => ({ salesId: null, name: '', branch: '', level: 'Branch', entryDate: '' })
const form = ref(emptyForm())
const rules = {
  name: [{ required: true, message: 'Please enter name', trigger: 'blur' }],
  branch: [{ required: true, message: 'Please enter branch', trigger: 'blur' }],
}

const filteredList = computed(() =>
    list.value.filter(i => !search.value || i.name?.includes(search.value))
)

async function loadData() {
  loading.value = true
  try {
    const res = await traineeApi.getAll()
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
      await traineeApi.update(form.value.salesId, form.value)
    } else {
      await traineeApi.create(form.value)
    }
    ElMessage.success(isEdit.value ? 'Updated successfully' : 'Created successfully')
    dialogVisible.value = false
    loadData()
  } catch {
    ElMessage.error('Operation failed')
  }
}

async function handleDelete(id) {
  await ElMessageBox.confirm('Confirm delete this trainee?', 'Warning', { type: 'warning' })
  try {
    await traineeApi.delete(id)
    ElMessage.success('Deleted successfully')
    loadData()
  } catch {
    ElMessage.error('Delete failed')
  }
}

function excelDateToString(value) {
  if (!value) return ''
  if (typeof value === 'number') {
    const date = new Date((value - 25569) * 86400 * 1000)
    return date.toISOString().split('T')[0]
  }
  return value
}

function handleImport(file) {
  const reader = new FileReader()
  reader.onload = async (e) => {
    const workbook = XLSX.read(e.target.result, { type: 'binary' })
    const sheet = workbook.Sheets[workbook.SheetNames[0]]
    const rows = XLSX.utils.sheet_to_json(sheet)

    const trainees = rows.map(row => ({
      name: row['name'] || row['Name'],
      branch: row['branch'] || row['Branch'],
      level: row['level'] || row['Level'],
      entryDate: excelDateToString(row['entryDate'] || row['EntryDate']),
    }))

    try {
      await Promise.all(trainees.map(t => traineeApi.create(t)))
      ElMessage.success(`Imported ${trainees.length} trainees successfully`)
      loadData()
    } catch {
      ElMessage.error('Import failed')
    }
  }
  reader.readAsBinaryString(file)
  return false
}

onMounted(loadData)
</script>

<style scoped>
.toolbar-card { margin-bottom: 16px; }
.toolbar { display: flex; justify-content: space-between; align-items: center; }
.toolbar-right { display: flex; gap: 12px; }
</style>