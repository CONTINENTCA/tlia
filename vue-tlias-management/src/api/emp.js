import request from "@/utils/request"

// 查询全部员工数据
export const queryPageApi = function(name,gender,begin,end,page,pageSize){
  return request.get(`/emps?name=${name}&gender=${gender}&begin=${begin}&end=${end}&page=${page}&pageSize=${pageSize}`)
}

// 新增员工
export const addEmpApi = function(emp){
  return request.post('/emps',emp)
}

// 根据id查询员工
export const getEmpByIdApi = function(id){
  return request.get(`/emps/${id}`)
}

// 修改员工
export const modifyEmpApi = function(emp){
  return request.put('/emps',emp);
}

// 删除员工
export const deleteEmpApi = function(ids){
  return request.delete(`/emps?ids=${ids}`)
}