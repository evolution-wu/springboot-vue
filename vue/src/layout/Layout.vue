<template>
  <div>
  <!--第一次路由显示toubu-->
  <Header></Header>
  <!--主体-->
    <div style="display: flex">
      <Aside></Aside>
      <!--第二层路由，也就是显示主体-->
      <router-view style="flex: 1"></router-view>
      </div>
  </div>
</template>

<script>
export default {
  name: "Layout",
  components: {
    Header,
    Aside
  }
}
import Header from "@/components/Header.vue";
import Aside from "@/components/Aside.vue";

</script>

<style scoped>

</style>