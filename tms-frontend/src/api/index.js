import axios from 'axios'

const http = axios.create({
    baseURL: 'http://localhost:8080',
    timeout: 10000,
})

// TrainingClass
export const trainingClassApi = {
    getAll: () => http.get('/api/trainingclass'),
    getById: (id) => http.get(`/api/trainingclass/${id}`),
    create: (data) => http.post('/api/trainingclass', data),
    update: (id, data) => http.put(`/api/trainingclass/${id}`, data),
    delete: (id) => http.delete(`/api/trainingclass/${id}`),
}

// Trainee
export const traineeApi = {
    getAll: () => http.get('/api/trainee'),
    getById: (id) => http.get(`/api/trainee/${id}`),
    create: (data) => http.post('/api/trainee', data),
    update: (id, data) => http.put(`/api/trainee/${id}`, data),
    delete: (id) => http.delete(`/api/trainee/${id}`),
}

// Trainer
export const trainerApi = {
    getAll: () => http.get('/api/trainer'),
    getById: (id) => http.get(`/api/trainer/${id}`),
    create: (data) => http.post('/api/trainer', data),
    update: (id, data) => http.put(`/api/trainer/${id}`, data),
    delete: (id) => http.delete(`/api/trainer/${id}`),
}

// Enrollment
export const enrollmentApi = {
    getAll: () => http.get('/api/enrollment'),
    getByClassId: (classId) => http.get(`/api/enrollment/class/${classId}`),
    create: (data) => http.post('/api/enrollment', data),
    update: (id, data) => http.put(`/api/enrollment/${id}`, data),
    delete: (id) => http.delete(`/api/enrollment/${id}`),
}

// CourseFile
export const courseFileApi = {
    getAll: () => http.get('/api/coursefile'),
    getByClassId: (classId) => http.get(`/api/coursefile/class/${classId}`),
    create: (data) => http.post('/api/coursefile', data),
    update: (id, data) => http.put(`/api/coursefile/${id}`, data),
    delete: (id) => http.delete(`/api/coursefile/${id}`),
}