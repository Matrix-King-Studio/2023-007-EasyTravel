import { createRouter, createWebHashHistory } from 'vue-router'

import Home from '../views/Home.vue'

const routes = [
  {
    path: '/',
    redirect:'/home'
  },
  {
    path: '/home',
    name: 'Home',
    component: Home
  },
  {
    path: '/homeStay',
    name: 'HomeStay',
    component: () => import('../views/Homestay.vue')
  },
  {
    path: '/shopping',
    name: 'Shopping',
    component: () => import('../views/Shopping.vue')
  },
  {
    path: '/carPool',
    name: 'CarPool',
    component: () => import('../views/CarPool.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/houseOrder/:name/:id',
    name: 'HouseOrder',
    component: () => import('../views/HouseOrder.vue')
  },
  {
    path: '/sceneryOrder/:name/:id',
    name: 'SceneryOrder',
    component: () => import('../views/SceneryOrder.vue')
  },
  {
    path: '/goodOrder/:name/:id',
    name: 'GoodOrder',
    component: () => import('../views/GoodOrder.vue')
  },
  {
    path: '/carOrder/:id',
    name: 'CarOrder',
    component: () => import('../views/CarOrder.vue')
  },
  {
    path: '/myOrder',
    name: 'MyOrder',
    meta: {
      requireAuth: true, // 需要验证登录状态
    },
    component: () => import('../views/MyOrder.vue')
  },
]

const router = createRouter({
  scrollBehavior: () => ({ left: 0, top: 0}), //切换到新路由时滚到页面顶部 和vue2有所不同
  history: createWebHashHistory(),
  routes
})

export default router
