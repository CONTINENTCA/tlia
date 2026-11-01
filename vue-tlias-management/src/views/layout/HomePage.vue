<script setup>
import {
  Avatar,
  Document,
  EditPen,
  HelpFilled, Histogram,
  HomeFilled, InfoFilled, Menu, Promotion, Share,
  SwitchButton,
  Tools,
  UserFilled
} from "@element-plus/icons-vue";
import {ref,onMounted} from "vue";
import { ElMessage } from "element-plus";
import { useRouter } from "vue-router";

// router
const router = useRouter();
// 当前登录员工姓名
const loginName = ref("")
// 钩子
onMounted(function () {
  const loginUser = JSON.parse(localStorage.getItem("loginUser"))
  if(loginUser && loginUser.name){
    loginName.value = loginUser.name
  }
})

const logout = function (){
  localStorage.removeItem("loginUser");
  router.push("/login");
  ElMessage.success('退出登录成功,退出人:' + loginName.value)
}

</script>

<!--
Router实例：路由实例，基于createRouter函数创建，维护了应用的路由信息。
<router-link>:路由链接组件，浏览器会解析成<a>。
<router-v1ew>:动态视图组件，用来渲染展示与路由路径对应的组件。
-->

<template>
  <div class="common-layout">
    <el-container>
      <!-- Header 区域 -->
      <el-header class="header">
        <span class="title">Zly智能学习辅助系统</span>
        <span class="right_tool">
          <a href="">
            <el-icon><EditPen /></el-icon> 修改密码 &nbsp;&nbsp;&nbsp; |
            &nbsp;&nbsp;&nbsp;
          </a>
          <el-button link @click="logout">
            <el-icon><SwitchButton /></el-icon>
            退出登录【{{ loginName }}】
          </el-button>
        </span>
      </el-header>

      <el-container>
        <!-- 左侧菜单 -->
        <el-aside class="aside">
          <!-- 左侧菜单栏 -->
          <el-menu :router="true">  <!--也可以直接用router 启用vue-rooter 以index这个属性进行跳转-->
            <!-- 首页菜单 -->
            <!--router-link 路由链接组件 浏览器会解析成<a>-->
            <router-link to="/index"> <!--路由标签-->
              <el-menu-item index="/index">
                <el-icon><Promotion /></el-icon> 首页
              </el-menu-item>
            </router-link>
            <!-- 班级管理菜单 -->
            <el-sub-menu index="/manage">
              <template #title>
                <el-icon><Menu /></el-icon> 班级学员管理
              </template>
              <el-menu-item index="/clazz">
                <el-icon><HomeFilled /></el-icon>班级管理
              </el-menu-item>
              <el-menu-item index="/stu">
                <el-icon><UserFilled /></el-icon>学员管理
              </el-menu-item>
            </el-sub-menu>

            <!-- 系统信息管理 -->
            <el-sub-menu index="/system">
              <template #title>
                <el-icon><Tools /></el-icon>系统信息管理
              </template>
              <el-menu-item index="/dept">
                <el-icon><HelpFilled /></el-icon>部门管理
              </el-menu-item>
              <el-menu-item index="/emp">
                <el-icon><Avatar /></el-icon>员工管理
              </el-menu-item>
            </el-sub-menu>

            <!-- 数据统计管理 -->
            <el-sub-menu index="/report">
              <template #title>
                <el-icon><Histogram /></el-icon>数据统计管理
              </template>
              <el-menu-item index="/empReport">
                <el-icon><InfoFilled /></el-icon>员工信息统计
              </el-menu-item>
              <el-menu-item index="/stuReport">
                <el-icon><Share /></el-icon>学员信息统计
              </el-menu-item>
              <el-menu-item index="/log">
                <el-icon><Document /></el-icon>日志信息统计
              </el-menu-item>
            </el-sub-menu>
          </el-menu>
        </el-aside>

        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<style scoped>
.header {
  background-image: linear-gradient(
    to right,
    #00547d,
    #007fa4,
    #00aaa0,
    #00d072,
    #a8eb12
  );
}

.title {
  color: white;
  font-size: 40px;
  font-family: 楷体, serif;
  line-height: 60px;
  font-weight: bolder;
}

.right_tool {
  float: right;
  line-height: 60px;
}

a {
  color: white;
  text-decoration: none;
}

.aside {
  width: 250px;
  border-right: 1px solid #ccc;
  height: 730px;
}
</style>
