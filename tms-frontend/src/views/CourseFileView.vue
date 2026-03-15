<template>
  <div>
    <el-card class="toolbar-card">
      <div class="toolbar">
        <el-select v-model="filterClassId" placeholder="Filter by class" clearable style="width:260px">
          <el-option v-for="c in classList" :key="c.classNo" :label="c.className" :value="c.classNo" />
        </el-select>
        <el-button type="primary" :icon="Plus" @click="openDialog()">New Course File</el-button>
      </div>
    </el-card>

    <el-card>
      <el-table :data="filteredList" stripe v-loading="loading"  style="width:100%">
        <el-table-column prop="courseFileId" label="ID" width="80" />
        <el-table-column prop="slidesName" label="Slides Name" width="160" />
        <el-table-column label="File Name" width="280">
          <template #default="{ row }">
            {{ displayFileName(row.fileName) }}
          </template>
        </el-table-column>
        <el-table-column label="Training Class" width="140">
          <template #default="{ row }">
            {{ classList.find(c => c.classNo === row.trainingClassId)?.className || row.trainingClassId }}
          </template>
        </el-table-column>
        <el-table-column label="Actions" min-width="300" align="center" class-name="action-col">
          <template #default="{ row }">
            <el-button size="small" style="padding: 5px 8px" @click="openDialog(row)">Edit</el-button>
            <el-button size="small" style="padding: 5px 8px" type="success" @click="handleDownload(row.fileName)">Download</el-button>
            <el-button size="small" style="padding: 5px 8px" type="danger" @click="handleDelete(row.courseFileId)">Delete</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="isEdit ? 'Edit Course File' : 'New Course File'" width="480px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="130px">
        <el-form-item label="Training Class" prop="trainingClassId">
          <el-select v-model="form.trainingClassId" style="width:100%">
            <el-option v-for="c in classList" :key="c.classNo" :label="c.className" :value="c.classNo" />
          </el-select>
        </el-form-item>
        <el-form-item label="Slides Name" prop="slidesName">
          <el-input v-model="form.slidesName" />
        </el-form-item>
        <el-form-item label="File" prop="fileName">
          <el-upload
              :show-file-list="false"
              :before-upload="handleFileUpload"
              accept=".ppt,.pptx,.pdf,.doc,.docx"
          >
            <el-button :icon="Upload">Select File</el-button>
          </el-upload>
          <span v-if="form.fileName" style="margin-left:10px; color:#666; font-size:13px">
            {{ displayFileName(form.fileName) }}
          </span>
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
import { Plus, Upload } from '@element-plus/icons-vue'
import { courseFileApi, trainingClassApi } from '../api/index.js'
import axios from 'axios'

const loading = ref(false)
const list = ref([])
const classList = ref([])
const filterClassId = ref(null)
const dialogVisible = ref(false)
const isEdit = ref(false)
const formRef = ref()

const emptyForm = () => ({ courseFileId: null, slidesName: '', fileName: '', trainingClassId: null })
const form = ref(emptyForm())
const rules = {
  trainingClassId: [{ required: true, message: 'Please select a class', trigger: 'change' }],
  slidesName: [{ required: true, message: 'Please enter slides name', trigger: 'blur' }],
}

const filteredList = computed(() =>
    filterClassId.value ? list.value.filter(i => i.trainingClassId === filterClassId.value) : list.value
)

function displayFileName(fileName) {
  if (!fileName) return ''
  const idx = fileName.indexOf('_')
  return idx !== -1 ? fileName.substring(idx + 1) : fileName
}

async function loadData() {
  loading.value = true
  try {
    const [f, c] = await Promise.all([
      courseFileApi.getAll(),
      trainingClassApi.getAll(),
    ])
    list.value = f.data
    classList.value = c.data
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

async function handleFileUpload(file) {
  const formData = new FormData()
  formData.append('file', file)
  try {
    const res = await axios.post('http://localhost:8080/api/file/upload', formData)
    form.value.fileName = res.data
    ElMessage.success('File uploaded successfully')
  } catch {
    ElMessage.error('Upload failed')
  }
  return false
}

async function handleSubmit() {
  await formRef.value.validate()
  try {
    if (isEdit.value) {
      await courseFileApi.update(form.value.courseFileId, form.value)
    } else {
      await courseFileApi.create(form.value)
    }
    ElMessage.success(isEdit.value ? 'Updated successfully' : 'Created successfully')
    dialogVisible.value = false
    loadData()
  } catch {
    ElMessage.error('Operation failed')
  }
}

function handleDownload(fileName) {
  window.open(`http://localhost:8080/api/file/download/${fileName}`)
}

async function handleDelete(id) {
  await ElMessageBox.confirm('Confirm delete this course file?', 'Warning', { type: 'warning' })
  try {
    await courseFileApi.delete(id)
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
.action-col .cell { white-space: nowrap; }

</style>