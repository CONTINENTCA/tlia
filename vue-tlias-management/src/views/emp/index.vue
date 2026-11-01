<script setup>
import { ElMessage, ElMessageBox } from "element-plus";
import { ref, watch,onMounted } from "vue";
import {queryPageApi,addEmpApi,getEmpByIdApi,modifyEmpApi,deleteEmpApi} from '@/api/emp'
import {queryAllApi as queryAllDeptApi } from "@/api/dept";
import { Plus } from "@element-plus/icons-vue";

// token（上传图片需要额外设置 否则不能通过令牌校验）
const token = ref('')

// 元数据
//职位列表数据
const jobs = ref([{ name: '班主任', value: 1 },{ name: '讲师', value: 2 },{ name: '学工主管', value: 3 },{ name: '教研主管', value: 4 },{ name: '咨询师', value: 5 },{ name: '其他', value: 6 }])
//性别列表数据
const genders = ref([{ name: '男', value: 1 }, { name: '女', value: 2 }])
//部门数据
const depts = ref([])
// 钩子
onMounted(function() {
  search();
  // 获取部门数据
  queryAllDept()
  // 获取token
  const loginUser = JSON.parse(localStorage.getItem("loginUser"))
  if(loginUser && loginUser.token){
    token.value = loginUser.token
  }
})

// 查询所有部门数据
const queryAllDept = async function(){
  try {
    const ret = await queryAllDeptApi()
    if (ret.code) {
      depts.value = ret.data
    } else {
      ElMessage.error(ret.msg || '部门列表查询失败')
    }
  } catch (error) {
    ElMessage.error('部门列表请求失败')
  }
}

// 搜索对象
const searchEmp = ref({
  name: '',
  gender: '',
  date: [],
  begin: '',
  end: ''
})
// 侦听searchEmp的date属性
// 第一个参数的意义是我只侦听searchEmp对象中某个属性
watch(function(){return searchEmp.value.date},
  function(newValue, oldValue){
    if(newValue.length == 2){ // 数组中有两个元素
      searchEmp.value.begin = newValue[0];
      searchEmp.value.end = newValue[1];
    }else{ // 数组为空
      searchEmp.value.begin = '';
      searchEmp.value.end = '';
    }
  }
)

// 查询员工列表
const search = async function(){
  const ret = await queryPageApi(searchEmp.value.name,
    searchEmp.value.gender,
    searchEmp.value.begin,
    searchEmp.value.end,
    currentPage.value,
    pageSize.value);

  if(ret.code){
    ElMessage.success("查询成功")
    empList.value = ret.data.rows;
    total.value = ret.data.total;
  }else{
    ElMessage.error("查询失败")
  }
}

// 清空
const clear = function(){
  searchEmp.value = {
    name: '',
    gender: '',
    date: [],
    begin: '',
    end: ''
  }
  // 再次调用search函数
  search()
}

// watch倾听 -----------------展示-------------------
/*
const a = ref('')
watch(a,function(newValue, oldValue){
  ElMessage.info('变化后的值是:' + newValue + ',变化前的值:' + oldValue)
})
// 深度侦听(user是个对象 如果不使用深度侦听 只有当user这个对象变了时 才会触发侦听 开启深度侦听后user对象属性变化会触发侦听)
const user = ref({name:'',age:10})
watch(user,function(newValue, oldValue){
  ElMessage.info(`NewUser: ${newValue.name}, ${newValue.age} | OldUser: ${oldValue.name}, ${oldValue.age}`)
},{deep:true}) // 开启深度侦听 侦听user对象中的全部属性的变化
// 有三个可选参数 长江两个选项
// deep(boolean) : 是否深度侦听 默认浅层侦听
// immediate(boolean): 是否在侦听创建时立即触发回调函数

// 只侦听对象中指定的属性
watch(function(){
  return user.value.name
}, function(newValue, oldValue){
  ElMessage.info(`NewValue: ${newValue}`)
})

// 监听多个属性，返回数组
watch(function() {
  return [user.value.name, user.value.age]
}, function(newValues, oldValues) {
  // newValues 和 oldValues 都是数组
  var newName = newValues[0]
  var newAge = newValues[1]
  var oldName = oldValues[0]
  var oldAge = oldValues[1]
  ElMessage.info('姓名: ' + oldName + ' → ' + newName + ', 年龄: ' + oldAge + ' → ' + newAge)
})
*/

// 表格
const empList = ref([])

// 分页条
const currentPage = ref(1)
const pageSize = ref(10)
const background = ref(true)
const size = ref('default')
const disabled = ref(false)
const total = ref(0)
// 每页展示记录数变化
const handleSizeChange = function(val){
  ElMessage.info(`每页展示:${val}条记录`)
  search()
}

// 页码变化时触发
const handleCurrentChange = function(val){
  ElMessage.info(`当前页码: ${val}`)
  search()
}

// 新增员工
const addEmp = function(){
  dialogVisible.value = true
  dialogTitle.value = '新增员工'
  employee.value = {
    image:'',
    username:'',
    name:'',
    gender:'',
    job:'',
    entryDate:'',
    deptId:'',
    phone:'',
    salary:'',
    exprList:[]
  }
}


// 新增/修改
const employee = ref({
  image:'',
  username:'',
  name:'',
  gender:'',
  job:'',
  entryDate:'',
  deptId:'',
  phone:'',
  salary:'',
  exprList:[]
})

// 控制弹窗
const dialogVisible = ref(false)
const dialogTitle = ref('新增员工')

//文件上传
// 图片上传成功后触发
const handleAvatarSuccess = function(response) {
  employee.value.image = response.data;
  console.log(response);
}
// 文件上传之前触发
const beforeAvatarUpload = function(rawFile) {
  if (rawFile.type !== 'image/jpeg' && rawFile.type !== 'image/png') {
    ElMessage.error('只支持上传图片')
    return false
  } else if (rawFile.size / 1024 / 1024 > 10) {
    ElMessage.error('只能上传10M以内图片')
    return false
  }
  return true
}

// 添加工作经历
const addExprItem = function(){
  employee.value.exprList.push({company:'',job:'',begin:'',end:'',exprDate:[]})
}
// 删除工作经历
const delExprItem = function(index){
  employee.value.exprList.splice(index,1)
}
// 侦听
watch(
  function () {
    return employee.value.exprList
  },
  function (newValue, oldValue) {
    if (newValue && newValue.length > 0) {
      newValue.forEach(function (expr) {
        if (expr.exprDate && expr.exprDate.length === 2) {
          expr.begin = expr.exprDate[0]
          expr.end = expr.exprDate[1]
        } else {
          expr.begin = ''
          expr.end = ''
        }
      })
    }
  },
  {
    deep: true
  }
)

// 保存员工
const saveEmp = async function(){
  // 表单校验
  if(!empFormRef.value) return

  empFormRef.value.validate(async function(valid,fields){
    if(valid){
      let ret;
      if(employee.value.id){
        ret = await modifyEmpApi(employee.value)
      }else{
        ret = await addEmpApi(employee.value)
      }
      if(ret.code){
        ElMessage.success("保存成功")
        dialogVisible.value = false
        search()
      }else{ // 失败
        ElMessage.error(ret.msg)
      }
    }else{
      ElMessage.error("表单验证失败")
    }
  })
}

// 表单引用
const empFormRef = ref()


// 表单校验规则
const rules = ref({
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 2, max: 20, message: '用户名长度应在2到20个字符之间', trigger: 'blur' }
  ],
  name: [
    { required: true, message: '请输入姓名', trigger: 'blur' },
    { min: 2, max: 10, message: '姓名长度应在2到10个字符之间', trigger: 'blur' }
  ],
  gender: [
    { required: true, message: '请选择性别', trigger: 'change' }
  ],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1\d{10}$/, message: '请输入有效的手机号', trigger: 'blur' }
  ]
});

// 根据id查询回显员工
const getEmpById = async function(id){
  dialogVisible.value = true
  dialogTitle.value = '修改员工'
  const ret = await getEmpByIdApi(id);
  if(ret.code){
    employee.value = ret.data

    // 对工作经历进行处理
    let exprList = employee.value.exprList
    if(exprList && exprList.length > 0){
      exprList.forEach(function(expr){
        expr.exprDate = [expr.begin, expr.end]
      })
    }

    ElMessage.success("查询回显成功")
  }else{
    ElMessage.error(ret.msg)
  }
}

// 删除员工
const deleteById = async function(id){
  ElMessageBox.confirm(
    '确认删除该员工吗?',
    '提示',
    {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning',
    }
  ).then(async function(){
    const ret = await deleteEmpApi(id);
    if(ret.code){
      ElMessage.success('删除成功')
      search()
    }else{
      ElMessage.error(ret.msg)
    }
  }).catch(function(){
    ElMessage.info('您已取消')
  })
}

// 记录勾选的员工的id
const selectedIds = ref([])

// 复选框勾选发生变化时触发 val:当前选中的记录(数组)
const handleSelectionChange = function(row){
  selectedIds.value = row.map(function(item){ //map 用来遍历数组，并生成一个新数组。
    return item.id
  })
}

// 批量删除员工
const deleteByIds = function(){
  ElMessageBox.confirm(
    '确认删除该员工吗?',
    '提示',
    {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning',
    }
  ).then(async function(){
    if(selectedIds.value && selectedIds.value.length > 0){
      const ret = await deleteEmpApi(selectedIds.value);
      if(ret.code){
        ElMessage.success('批量删除成功')
        search()
      }else{
        ElMessage.error(ret.msg)
      }
    }else{
     ElMessage.info("请先勾选员工")
    }
  }).catch(function(){
    ElMessage.info('您已取消')
  })
}
</script>

<template>
  <h1>员工管理</h1>
  <div class="container">
    <el-form :inline="true" :model="searchEmp">
      <el-form-item label="姓名">
        <el-input v-model="searchEmp.name" placeholder="请输入员工姓名" clearable />
      </el-form-item>
      <el-form-item label="性别">
        <el-select
          v-model="searchEmp.gender"
          placeholder="请选择员工性别"
          clearable
        >
          <el-option label="男" value="1" />
          <el-option label="女" value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="入职时间">
        <el-date-picker
          v-model="searchEmp.date"
          type="daterange"
          range-separator="到"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          size="default"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="search()">查询</el-button>
        <el-button type="info" @click="clear()">清空</el-button>
      </el-form-item>
    </el-form>
  </div>
  <!--功能按钮-->
  <div class="container">
    <el-button type="primary" @click="addEmp">新增员工</el-button>
    <el-button type="danger" @click="deleteByIds">批量删除</el-button>
  </div>

  <!--表格-->
  <div class="container">
    <el-table :data="empList" border style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column prop="username" label="用户名" align="center" />
      <el-table-column prop="name" label="姓名" align="center" />
      <el-table-column prop="gender" label="性别" align="center">
        <template #default="scope">
          <p v-if="scope.row.gender == 1">男</p>
          <p v-else>女</p>
        </template>
      </el-table-column>
      <el-table-column prop="image" label="头像" align="center" >
        <template #default="scope">
          <img :src="scope.row.image" height="40px" alt="暂无资源">
        </template>
      </el-table-column>
      <el-table-column prop="job" label="职位" align="center">
        <template #default="scope">
          <span v-if="scope.row.job == 1">班主任</span>
          <span v-else-if="scope.row.job == 2">讲师</span>
          <span v-else-if="scope.row.job == 3">学工主管</span>
          <span v-else-if="scope.row.job == 4">教研主管</span>
          <span v-else-if="scope.row.job == 5">咨询师</span>
          <span v-else>其它</span>
        </template>
      </el-table-column>
      <el-table-column prop="entryDate" label="入职日期" align="center" />
      <el-table-column prop="updateTime" label="最后操作时间" align="center" />
      <el-table-column label="操作" align="center" >
        <template #default="scope">
          <el-button type="primary" size="small" @click="getEmpById(scope.row.id)">编辑</el-button>
          <el-button type="danger" size="small" @click="deleteById(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>

  <!--分页条-->
  <div class="container">
    <el-pagination
      v-model:current-page="currentPage"
      v-model:page-size="pageSize"
      :page-sizes="[10, 20, 30, 50,75,100]"
      :size= "size"
      :disabled="disabled"
      :background= "background"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
  </div>

  <!-- 新增/修改员工的对话框 -->
  <el-dialog v-model="dialogVisible" :title="dialogTitle">
    {{employee}}
    <el-form ref="empFormRef" :model="employee" :rules="rules" label-width="80px">
      <!-- 基本信息 -->
      <!-- 第一行 -->
      <!--
      采用layout布局模式 24等分
      gutter是一行中的元素间距
      -->
      <el-row :gutter="20"> <!--row表示一行-->
        <el-col :span="12"> <!--col表示一行中的一列 span的大小决定他在这行占的比例 span加起来要等于24-->
          <el-form-item label="用户名" prop="username"> <!--代表要使用rules中username的校验规则-->
            <el-input v-model="employee.username" placeholder="请输入员工用户名，2-20个字"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="姓名" prop="name">
            <el-input v-model="employee.name" placeholder="请输入员工姓名，2-10个字"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第二行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="性别" prop="gender">
            <el-select v-model="employee.gender" placeholder="请选择性别" style="width: 100%;">
              <!--
              <el-option label="男" value="1"></el-option>
              <el-option label="女" value="2"></el-option>
              -->
              <!--
              表示遍历 genders：
              - item：当前选项对象
              - index：当前下标，从 0 开始
              key 是 Vue 用来区分每个选项的唯一标识。
              lable 表示下拉框中显示给用户看的文字。
              value 表示用户选择这个选项后，实际保存到 v-model 中的值。
              -->
              <el-option v-for="(item,index) in genders" :key="item.value" :label="item.name" :value="item.value"></el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="手机号" prop="phone">
            <el-input v-model="employee.phone" placeholder="请输入员工手机号"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第三行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="职位">
            <el-select v-model="employee.job" placeholder="请选择职位" style="width: 100%;">
              <!--
              <el-option label="班主任" value="1"></el-option>
              <el-option label="讲师" value="2"></el-option>
              <el-option label="学工主管" value="3"></el-option>
              <el-option label="教研主管" value="4"></el-option>
              <el-option label="咨询师" value="5"></el-option>
              -->
              <el-option v-for="(item,index) in jobs" :key="item.value" :label="item.name" :value="item.value"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="薪资">
            <el-input v-model="employee.salary" placeholder="请输入员工薪资"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第四行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="所属部门">
            <el-select v-model="employee.deptId" placeholder="请选择部门" style="width: 100%;">
              <!--
              <el-option label="研发部" value="1"></el-option>
              <el-option label="市场部" value="2"></el-option>
              -->
              <el-option v-for="(item,index) in depts" :key="item.id" :value="item.id" :label="item.name"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="入职日期">
            <el-date-picker v-model="employee.entryDate" type="date" style="width: 100%;" placeholder="选择日期" format="YYYY-MM-DD" value-format="YYYY-MM-DD"></el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第五行 -->
      <!--给上传文件的组件额外配置header 注意MyToken要和resquest拦截器一致-->
      <el-row :gutter="20">
        <el-col :span="24">
          <el-form-item label="头像">
            <el-upload
              class="avatar-uploader"
              action="/api/upload"
              :headers="{'MyToken':token}"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload"
            >
              <img v-if="employee.image" :src="employee.image" class="avatar"  alt=""/>
              <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
            </el-upload>
          </el-form-item>
        </el-col>
      </el-row>


      <!-- 工作经历 -->
      <!-- 第六行 -->
      <el-row :gutter="10">
        <el-col :span="24">
          <el-form-item label="工作经历">
            <el-button type="success" size="small" @click="addExprItem">+ 添加工作经历</el-button>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第七行 ...  工作经历 -->
      <el-row :gutter="3" v-for="(item,index) in employee.exprList">
        <el-col :span="10">
          <el-form-item size="small" label="时间" label-width="80px">
            <el-date-picker type="daterange" v-model="item.exprDate" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" format="YYYY-MM-DD" value-format="YYYY-MM-DD" ></el-date-picker>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item size="small" label="公司" label-width="60px">
            <el-input placeholder="请输入公司名称" v-model="item.company"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item size="small" label="职位" label-width="60px">
            <el-input placeholder="请输入职位" v-model="item.job"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="2">
          <el-form-item size="small" label-width="0px">
            <el-button type="danger" @click="delExprItem(index)" >- 删除</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <!-- 底部按钮 -->
    <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="saveEmp">保存</el-button>
        </span>
    </template>
  </el-dialog>

</template>

<style scoped>
.container {
  margin: 10px 0
}

.avatar {
  height: 40px;
}
.avatar-uploader .avatar {
  width: 78px;
  height: 78px;
  display: block;
}
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 78px;
  height: 78px;
  text-align: center;
  /* 添加灰色的虚线边框 */
  border: 1px dashed var(--el-border-color);
}

</style>
