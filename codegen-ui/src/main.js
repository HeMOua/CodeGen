import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import ViewUI from 'view-design'
import mavonEditor from 'mavon-editor'
import plugins from '@/plugins'
import 'element-ui/lib/theme-chalk/index.css'
import 'view-design/dist/styles/iview.css'
import '@/assets/styles/index.scss'
import 'mavon-editor/dist/css/index.css'

import { selectDictLabel, resetForm } from '@/utils'

Vue.config.productionTip = false

Vue.prototype.selectDictLabel = selectDictLabel
Vue.prototype.resetForm = resetForm

Vue.use(mavonEditor)
Vue.use(ElementUI)
Vue.use(ViewUI)
Vue.use(plugins)

new Vue({
  router,
  store,
  render: (h) => h(App)
}).$mount('#app')
