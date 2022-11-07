import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import VXETable from 'vxe-table'
import 'element-ui/lib/theme-chalk/index.css'
import 'vxe-table/lib/style.css'
import '@/assets/styles/index.scss'

Vue.config.productionTip = false

Vue.use(ElementUI)
Vue.use(VXETable)

new Vue({
  router,
  store,
  render: (h) => h(App)
}).$mount('#app')
