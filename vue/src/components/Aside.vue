<template>
<div>
  <el-menu
      style="width: 200px;min-height: calc(100vh - 50px)"
      default-active="path"
      class="el-menu-vertical-demo"
      router
  >
    <el-sub-menu index="1">
      <template #title>系统管理</template>
        <el-menu-item index="/user" >用户管理</el-menu-item>   <!--index中为路由-->
        <el-menu-item index="/book">其他管理</el-menu-item>
    </el-sub-menu>
    <el-menu-item index="data" :route="{path:'/'}">数据管理</el-menu-item>

  </el-menu>


</div>
</template>


<script>
export default {
  name: "Aside",
  data(){
    return {
      path: this.$route.path   //设置默认的高亮菜单
    }
  }
}
</script>

<style scoped>

</style>
