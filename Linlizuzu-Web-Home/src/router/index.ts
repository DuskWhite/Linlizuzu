import { createRouter,createWebHistory,RouteRecordRaw} from 'vue-router'

const routers:Array<RouteRecordRaw> = {
    path:"/",
    component:()=>import('../views/market3.vue')
}

const router = createRouter({
    history:createWebHistory(),
    routes:
})