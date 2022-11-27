import Vue from 'vue'
import VueRouter from 'vue-router'
import Layout from '@/components/Layout'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    component: Layout,
    redirect: 'index',
    children: [
      {
        path: 'index',
        name: 'Index',
        component: () => import('@/views/index.vue'),
        meta: { title: '首页' }
      }
    ]
  },
  {
    path: '/config',
    component: Layout,
    redirect: '/config/index',
    meta: { title: '模板配置' },
    children: [
      {
        path: 'index',
        name: 'ConfigIndex',
        component: () => import(/* webpackChunkName: "ConfigIndex" */ '@/views/config'),
        meta: { title: '所有模板' }
      },
      {
        path: 'build',
        name: 'ConfigBuild',
        component: () => import(/* webpackChunkName: "ConfigBuild" */ '@/views/config/build.vue'),
        meta: { title: '模板构建' }
      }
    ]
  }
]

// fix vue-router NavigationDuplicated
const VueRouterPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
  return VueRouterPush.call(this, location).catch(err => err)
}
const VueRouterReplace = VueRouter.prototype.replace
VueRouter.prototype.replace = function replace(location) {
  return VueRouterReplace.call(this, location).catch(err => err)
}

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
