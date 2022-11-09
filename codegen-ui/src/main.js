import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import VXETable from 'vxe-table'
import plugins from '@/plugins'
import 'element-ui/lib/theme-chalk/index.css'
import 'vxe-table/lib/style.css'
import '@/assets/styles/index.scss'

import { selectDictLabel, resetForm } from '@/utils'

Vue.config.productionTip = false

Vue.prototype.selectDictLabel = selectDictLabel
Vue.prototype.resetForm = resetForm

Vue.use(ElementUI)
Vue.use(VXETable)
Vue.use(plugins)

new Vue({
  router,
  store,
  render: (h) => h(App)
}).$mount('#app')
