import axios from 'axios'
import { Notification, Message } from 'element-ui'

// 创建axios实例
const service = axios.create({
  // axios中请求配置有baseURL选项，表示请求URL公共部分
  baseURL: process.env.VUE_APP_BASE_API,
  // 超时
  timeout: 10000
})

// 响应拦截器
service.interceptors.response.use(res => {
  const code = res.data.code
  if (code !== 200) {
    Notification.error({
      title: res.data.msg
    })
    return Promise.reject('error')
  } else {
    return res.data
  }
}, error => {
  console.log('err' + error)
  Message({
    message: error.message,
    type: 'error',
    duration: 5 * 1000
  })
  return Promise.reject(error)
}
)

export default service
