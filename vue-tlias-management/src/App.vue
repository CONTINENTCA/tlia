<!--定义js 控制模板部分数据和行为-->
<script setup>
//引入views/layout/index.vue命名为Layout @代表src
import HomePage from "@/views/layout/HomePage.vue";

</script>

<!--模板部分 控制的是网页结构（html）-->
<template>
  <!--
  <HomePage></HomePage>
  不能写死 要写动态路由组件
  -->
  <!--
  router-view 动态视图组件 用来渲染展示与路由路径对应的组件
  这是第一级路由出口。
  当访问 /emp 时，它会先显示：
  LayoutView
  然后 HomePage.vue 中还有一个：
  <router-view></router-view>
  这是第二级路由出口，用来显示：
  EmpView
  整体关系是：
  App.vue
  └── router-view
      └── HomePage.vue
          ├── el-header
          ├── el-aside
          └── router-view
              └── emp/index.vue
  -->
  <router-view></router-view>
</template>

<!--当前组件的css样式-->
<style scoped>

</style>
