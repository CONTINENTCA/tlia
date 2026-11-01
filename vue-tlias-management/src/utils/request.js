import axios from 'axios'
import { ElMessage } from "element-plus";
import router from '@/router' // 这个其实等效于const router = useRouter()
// 但是useRouter() 只能在 Vue 组件的 setup() 环境中使用，而 request.js 是普通 JavaScript 模块，不是 Vue 组件。

//创建axios实例对象
const request = axios.create({
  baseURL: '/api',
  timeout: 600000
})

//axios的响应 response 拦截器
request.interceptors.response.use(
  function(response) { //成功回调
    return response.data
  },
  function(error) { //失败回调
    if(error.response.status == 401) { // === 是全等 == 会强转（宽松）
      // 提示信息
      ElMessage.error('登陆超时,请重新登录')
      // 跳转登陆页面
      router.push('/login')
    }else{
      ElMessage.error('接口访问异常')
    }
    return Promise.reject(error)
  }
)

// request拦截器 获取localStorage中的token 在请求头增加token
request.interceptors.request.use(
  function(config) {
    const loginUser = JSON.parse(localStorage.getItem('loginUser')) // 把字符串变回json对象
    if(loginUser && loginUser.token) {
      config.headers.MyToken = loginUser.token; // 这里的config.headers.MyToken不一定是token 可以改成MyToken 要和后端的过滤器或拦截器相对应
    }
    return config
  },
  function(error) {
    return Promise.reject(error);
  }
)

// 导出添加拦截器的request对象
export default request
