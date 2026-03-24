import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '../views/LoginView.vue'
import TrainingClassView from '../views/TrainingClassView.vue'
import TraineeView from '../views/TraineeView.vue'
import TrainerView from '../views/TrainerView.vue'
import EnrollmentView from '../views/EnrollmentView.vue'
import CourseFileView from '../views/CourseFileView.vue'

const routes = [
    { path: '/login', component: LoginView, meta: { public: true } },
    { path: '/', redirect: '/training-class' },
    { path: '/training-class', component: TrainingClassView },
    { path: '/trainee', component: TraineeView },
    { path: '/trainer', component: TrainerView },
    { path: '/enrollment', component: EnrollmentView },
    { path: '/course-file', component: CourseFileView },
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

// Route Guard: If not logged in, redirect to /login
router.beforeEach(async (to) => {
    if (to.meta.public) return true

    try {
        const res = await fetch('http://localhost:8080/api/users/me', {
            credentials: 'include'
        })
        const data = await res.json()
        if (!data.success) return '/login'
    } catch {
        return '/login'
    }
})

export default router

// export default createRouter({
//     history: createWebHistory(),
//     routes,
// })