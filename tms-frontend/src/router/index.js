import { createRouter, createWebHistory } from 'vue-router'
import TrainingClassView from '../views/TrainingClassView.vue'
import TraineeView from '../views/TraineeView.vue'
import TrainerView from '../views/TrainerView.vue'
import EnrollmentView from '../views/EnrollmentView.vue'
import CourseFileView from '../views/CourseFileView.vue'

const routes = [
    { path: '/', redirect: '/training-class' },
    { path: '/training-class', component: TrainingClassView },
    { path: '/trainee', component: TraineeView },
    { path: '/trainer', component: TrainerView },
    { path: '/enrollment', component: EnrollmentView },
    { path: '/course-file', component: CourseFileView },
]

export default createRouter({
    history: createWebHistory(),
    routes,
})