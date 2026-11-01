
import request from '../utils/request'

// 查询全部部门数据
export const queryAllApi = function () {
  return request.get('/depts');
}
// 新增
export const addApi = function (dept) {
  return request.post('/depts', dept)
}

// 根据id查询
export const queryByIdApi = function (id) {
  return request.get(`/depts/${id}`)
}


// 修改
export const modifyApi = function (dept) {
  return request.put(`/depts`,dept)
}


// 删除
export const deleteByIdApi = function(id){
  return request.delete(`/depts?id=${id}`)
}
