import axios from 'axios'
import { ElMessage } from 'element-plus'
import { getToken } from './auth'

axios.defaults.timeout = 60000
axios.defaults.baseURL = 'http://localhost:8080/'

axios.interceptors.request.use(
  config => {
    config.headers['Content-Type'] = 'application/json;charset=UTF-8'
    config.headers['token'] = getToken()
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

axios.interceptors.response.use(
  response => {
    return response
  },
  error => {
    const { response } = error
    if (response) {
      const status = response.status
      const messages = {
        400: '请求错误(400)',
        401: '未授权，请重新登录(401)',
        403: '拒绝访问(403)',
        404: '请求出错(404)',
        408: '请求超时(408)',
        500: '服务器错误(500)',
        501: '服务未实现(501)',
        502: '网络错误(502)',
        503: '服务不可用(503)',
        504: '网络超时(504)',
        505: 'HTTP版本不受支持(505)'
      }
      const msg = messages[status] || `连接出错(${status})!`
      ElMessage.error(`${msg}，请检查网络或联系管理员！`)
      return Promise.reject(response.data)
    } else {
      ElMessage.warning('网络连接异常,请稍后再试!')
      return Promise.reject(error)
    }
  }
)

export function request(url = '', params = {}, type = 'POST') {
  return new Promise((resolve, reject) => {
    let promise
    if (type.toUpperCase() === 'GET') {
      promise = axios({ url, params })
    } else if (type.toUpperCase() === 'POST') {
      promise = axios({ method: 'POST', url, data: params })
    }
    promise.then(res => resolve(res)).catch(err => reject(err))
  })
}
