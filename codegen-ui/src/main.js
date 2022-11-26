import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import plugins from '@/plugins'
import 'element-ui/lib/theme-chalk/index.css'
import 'view-ui-plus/dist/styles/viewuiplus.css'
import '@/assets/styles/index.scss'
import { Split } from 'view-ui-plus'

import { selectDictLabel, resetForm } from '@/utils'

Vue.config.productionTip = false

Vue.prototype.selectDictLabel = selectDictLabel
Vue.prototype.resetForm = resetForm

Vue.use(ElementUI)
Vue.use(plugins)
Vue.component(Split.name, Split)

new Vue({
  router,
  store,
  render: (h) => h(App)
}).$mount('#app')
