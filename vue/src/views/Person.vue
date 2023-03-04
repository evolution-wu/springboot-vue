<template>
    <div>
    <el-card style="width: 40% ;margin: 10px">  <!--card标签来封装这个玩意儿，还是一句话注意字段与数据库一一对应-->

    <el-form ref="form" :model="form" label-width="80px" style="">
        <el-form-item label="用户名" >
          <el-input v-model="form.username" disabled></el-input>  <!--限制你用户名不能修改-->
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="form.nickName"></el-input>
        </el-form-item>
        <el-form-item label="年龄">
          <el-input v-model="form.age"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-input v-model="form.sex"></el-input>
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="form.address"></el-input>
        </el-form-item>
        <!--        <el-form-item label="密码">-->
        <!--          <el-input v-model="form.password" show-password></el-input>-->
        <!--        </el-form-item>-->

      </el-form>
      <div style="text-align: center">
        <el-button type="primary" @click="update">保存</el-button>
      </div>

    </el-card>
  </div>

</template>

<script>
import request from "@/utils/request";
export default {
  name: "Person",
  data() {
    return {
      form: {}
    }

  },
  created() {
    //这里就类头部那个登录消息共享
    //这里是因为你的登录信息要先默认显示再CARD上面，然后你好修改
    let str = sessionStorage.getItem("user")||"{}"
    this.form = JSON.parse(str)
  },

  methods: {
    update() {
      request.put("/user", this.form).then(res => {
        console.log(res)
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "更新成功"
          })
          sessionStorage.setItem("user", JSON.stringify(this.form))
          // 触发Layout更新用户信息
          this.$emit("userInfo")
          this.$router.push("/")  //更新成功后跳转
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
      })
    }
  }
}
</script>

<style scoped>

</style>