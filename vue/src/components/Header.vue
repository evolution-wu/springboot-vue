<template>
<div style="height: 50px; line-height: 50px;border-bottom: 1px solid #ccc;display: flex">
     <div style="width: 200px; padding-left: 30px; font-weight: bold;color: aqua">后台管理</div>
     <div style="flex: 1"></div>
     <div style="width: 100px">
       <el-dropdown>
    <span class="el-dropdown-link" style="margin-top: 18px">
      {{user.username}}
      <el-icon class="el-icon--right">
        <arrow-down />
      </el-icon>
    </span>
         <template #dropdown>
           <el-dropdown-menu>
             <el-dropdown-item @click="$router.push('/person')">个人信息</el-dropdown-item>
             <el-dropdown-item @click="$router.push('/login')">退出系统</el-dropdown-item>
           </el-dropdown-menu>
         </template>
       </el-dropdown>
     </div>
</div>
</template>

<script>

import '@element-plus/icons-vue'
export default {
  name: "Header",

 data() {
  return {
     user: {}  //数据
  }
 },
  created() {
    //将返回的user对象再转化成json对象，然后再赋值给浏览器this.user
    //这里的功能是主要实现登录后行栏上的登录用户同步
    let str = sessionStorage.getItem("user")||"{}"
    this.user = JSON.parse(str)
  }
}
</script>

<style scoped>

</style>