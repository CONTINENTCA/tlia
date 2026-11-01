<script setup>
import { ref ,onMounted} from 'vue'
//import axios from 'axios'
import { queryAllApi, addApi, queryByIdApi, modifyApi, deleteByIdApi } from "@/api/dept";
import {ElMessage,ElMessageBox} from "element-plus";

// 钩子函数
onMounted(function(){
  search();
})

// 查询所有部门
async function search(){
  //const res = await axios.get("https://m1.apifoxmock.com/m1/8249945-8011505-default/depts")
  //if(res.data.code == 1){
  //  tableData.value = res.data.data
  //}
  const res = await queryAllApi();
  if(res.code){
    tableData.value = res.data // 这里不需要data.data了因为拦截器返回的就是.data
  }
}

import { Delete, EditPen } from "@element-plus/icons-vue";
const tableData = ref([])

// Dialog对话框
const dialogFormVisible = ref(false)
const formTitle = ref('')
const dept = ref({name:''})


// 保存部门
async function save() {
  if(!deptFormRef.value){
    return;
  }
  // deptFormRef.value调用表单变量
  deptFormRef.value.validate(async function(valid,fields){
    //valid参数表示是否验证通过 true通过 false不通过
    if(valid){
      let ret;
      if(dept.value.id){ // 如果dept有id 执行修改
        ret = await modifyApi(dept.value)
      }else{ // 没id执行新增
        ret = await addApi(dept.value)
      }
      if(ret.code){ // 成功
        // 关闭对话框
        dialogFormVisible.value = false
        // 提示信息
        ElMessage.success('操作成功')
        // 查询
        search()
      }else{
        // 提示信息
        ElMessage.error(ret.msg);
      }
    }else{ // 如果验证表单不通过
      ElMessage.error('表单验证不通过')
    }
  })
}

// 新增部门
function addDept(){
  dialogFormVisible.value = true
  formTitle.value = '新增部门'
  // 清空
  dept.value = {name:''}
  // 重置表单校验规则 提示信息
  if(deptFormRef.value){ // 如果这个表单存在 那么重置
    deptFormRef.value.resetFields();
  }
}

// 表单校验相关的
const rules = ref({
  // blur代表鼠标失去焦点
  name: [
    { required: true, message: '部门名称是必填的', trigger: 'blur' },
    { min: 2, max: 10, message: '名称长度必须在2到10之间', trigger: 'blur' },
  ]
})

const deptFormRef = ref()

// 编辑
const edit = async function(id){
  const ret = await queryByIdApi(id);
  if(ret.code){
    // 展示出对话框
    dialogFormVisible.value = true
    dept.value = ret.data
    formTitle.value = '编辑部门'
    // 重置提示信息
    if(deptFormRef.value){
      deptFormRef.value.resetFields();
    }
  }
}

const deleteById = async function(id){
  ElMessageBox.confirm(
    '您是否确认删除部门?',
    '提示',
    {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning',
    }
  ).then(async function(){ // 点击确认后执行的函数
      const ret = await deleteByIdApi(id);
      if(ret.code){
        ElMessage({
          type: 'success',
          message: '部门已删除',
        })
        search()
      }else{
        ElMessage.error(ret.msg)
      }
    }).catch(function(){ // 点击取消时执行的函数
      ElMessage({
        type: 'info',
        message: '该操作已取消',
      })
    })
}

</script>

<template>
  <h2>部门管理</h2>
  <div class="container">
    <el-button type="primary" @click="addDept()">新增部门</el-button>
  </div>

  <div style="display: flex;">
    <el-table :data="tableData" :border="true" style="width: 100%">
      <el-table-column type="index" label="序号" width="100" align="center" />
      <el-table-column prop="name" label="部门名称" width="260" align="center" />
      <el-table-column prop="createTime" label="最后操作时间" width="300" align="center" />
      <el-table-column prop="updateTime" label="操作" align="center">
        <template #default="scope">
          <el-button type="primary" size="small" @click="edit(scope.row.id)"><el-icon><EditPen/></el-icon>编辑</el-button>
          <el-button type="warning" size="small" @click="deleteById(scope.row.id)"><el-icon><Delete/></el-icon>删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>

  <!--Dialog对话框-->
  <el-dialog v-model="dialogFormVisible" :title="formTitle" width="500">
    <el-form :model="dept" :rules="rules" ref="deptFormRef"> <!--这里的ref代表deptFormRef变量和这个表单变量绑定 可以通过这个变量调用表单-->
      <el-form-item label="部门名称" prop="name" label-width="80px">
        <el-input v-model="dept.name" placeholder="请输入部门名称" />
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="save()">确定</el-button>
      </div>
    </template>
  </el-dialog>

</template>

<style scoped>
  .container {
    margin: 10px 0;
  }
</style>

