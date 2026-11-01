import request from '@/utils/request'

// 登录
export const loginApi = function(data){
  return request.post('/login', data)
}