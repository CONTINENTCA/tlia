import { createRouter, createWebHistory } from "vue-router";

import HomePageView from "@/views/index/index.vue"
import ClazzView from '@/views/clazz/index.vue'
import DeptView from "@/views/dept/index.vue"
import EmpView from "@/views/emp/index.vue"
import LogView from "@/views/log/index.vue"
import StuView from "@/views/stu/index.vue"
import EmpReportView from "@/views/report/emp/index.vue"
import StuReportView from "@/views/report/stu/index.vue"
import LayoutView from "@/views/layout/HomePage.vue"
import LoginView from "@/views/login/index.vue"

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    // {
    //   path: '/',
    //   name: 'home',
    //   component: HomeView
    // },
    /*
    {
      path:'/index',
      name:'index',
      component: HomePageView
    },
    {
      path:'/clazz',
      name:'clazz',
      component: ClazzView
    },
    {
      path:'/stu',
      name:'stu',
      component: StuView
    },
    {
      path:'/dept',
      name:'dept',
      component: DeptView
    },
    {
      path:'/emp',
      name:'emp',
      component: EmpView
    },
    {
      path:'/log',
      name:'log',
      component: LogView
    },
    {
      path:'/empReport',
      name:'empReport',
      component: EmpReportView
    },
    {
      path:'/stuReport',
      name:'stuReport',
      component: StuReportView
    },
     */
    { // 路由嵌套
      path:'/',
      name:'layout',
      component: LayoutView,
      redirect:'/login', // 重定向 说的是如果直接在浏览器搜localhost:5173而没有继续向下 那么就定向到index
      // 表示嵌套路由。子路由会显示在 HomePage.vue 内部的：
      // <router-view></router-view> 中
      children:[
        {
          path:'index', // 这里就不需要/开头了
          name:'index',
          component: HomePageView
        },
        {
          path:'clazz',
          name:'clazz',
          component: ClazzView
        },
        {
          path:'stu',
          name:'stu',
          component: StuView
        },
        {
          path:'dept',
          name:'dept',
          component: DeptView
        },
        {
          path:'emp',
          name:'emp',
          component: EmpView
        },
        {
          path:'log',
          name:'log',
          component: LogView
        },
        {
          path:'empReport',
          name:'empReport',
          component: EmpReportView
        },
        {
          path:'stuReport',
          name:'stuReport',
          component: StuReportView
        }
      ]
    },
    {
      path:'/login',
      name:'login',
      component: LoginView
    }
  ],
});

export default router;

