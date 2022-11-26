import Vue from 'vue'
import VueRouter from 'vue-router'
import Layout from '@/components/Layout'
import TemplateConfig from '@/views/TemplateConfig.vue'
import GeneratorConfig from '@/views/GeneratorConfig.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    component: Layout,
    redirect: 'generatorConfig',
    children: [
      {
        path: 'generatorConfig',
        name: 'generatorConfig',
        component: GeneratorConfig,
        meta: { title: '代码生成' }
      },
      {
        path: 'templateConfig',
        name: 'templateConfig',
        component: TemplateConfig,
        meta: { title: '模板配置' }
      },
      {
        path: 'buildTemplate',
        name: 'buildTemplate',
        component: () => import(/* webpackChunkName: "buildTemplate" */ '@/views/BuildTemplate'),
        meta: { title: '模板构建' }
      }
    ]
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
