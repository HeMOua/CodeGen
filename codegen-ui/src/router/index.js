import Vue from 'vue'
import VueRouter from 'vue-router'
import TemplateConfig from '@/views/TemplateConfig.vue'
import GeneratorConfig from '@/views/GeneratorConfig.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect: '/generatorConfig'
  },
  {
    path: '/generatorConfig',
    name: 'generatorConfig',
    component: GeneratorConfig
  },
  {
    path: '/templateConfig',
    name: 'templateConfig',
    component: TemplateConfig
  },
  {
    path: '/buildTemplate',
    name: 'buildTemplate',
    component: () => import(/* webpackChunkName: "buildTemplate" */ '@/views/BuildTemplate.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
