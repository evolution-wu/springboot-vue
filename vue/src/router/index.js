import { createRouter, createWebHistory } from 'vue-router'

import Layout from '../layout/Layout.vue'
const routes = [
  {
    path: '/',
    name: 'Layout',
    component: Layout, //注意一般导航栏还有之类共有的布局结构，就可以先显示，剩余在此基础上显示的组件就嵌套来显示
    redirect: "/user",
    children: [     //组件嵌套
      {  path: 'user',
         name: 'User',
         component:  () => import("@/views/User.vue")
      },
      {  path: 'book',
         name: 'Book',
        component:  () => import("@/views/Book.vue")
      },
      {  path: 'person',
        name: 'Person',
        component:  () => import("@/views/Person.vue")
      }
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import("@/views/Login.vue")
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import("@/views/Register.vue")
  },

]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
