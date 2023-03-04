<template>
<div style="width: 100%;height: 100vh;background-color: slateblue; overflow: hidden">
  <div style="width: 400px;margin: 150px auto">
    <div style="color: azure;font-size: 30px;text-align: center">欢迎注册</div>
    <el-form ref="form" :model = "form" style="margin: 30px" :rules="rules">
      <el-form-item prop="username">
        <el-input :prefix-icon="User()" v-model="form.username" />
      </el-form-item>
      <el-form-item prop="password">
      <el-input :prefix-icon="Lock()" v-model="form.password" show-password></el-input>
    </el-form-item>
      <el-form-item prop="confirm">
        <el-input :prefix-icon="Lock()" v-model="form.confirm" show-password></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" style="width: 100%" @click="regsiter">登录</el-button>
      </el-form-item>
    </el-form>

  </div>
</div>
</template>

<script>
import {Lock, User} from "@element-plus/icons-vue";
import request from "@/utils/request";

export default {
  name: "Register",
  data() {
    return {
      form: {},
      rules: {
             username: [{required: true, message: "请输入用户名",trigger: "blur"}],
             password: [{required: true, message: "请输入密码",trigger: "blur"}],
             confirm: [{required: true, message: "请输入确认密码",trigger: "blur"}]
      }
    }
  },
  methods: {
    regsiter() {
      if (this.form.password != this.form.confirm)
      {
        this.$message({
          type: "error",
          message: "2次密码不一致!"
        })
        return //直接返回
      }
      this.$refs['form'].validate((valid)=> {
        if (valid) {
          request.post("/user/register", this.form).then(res => {
            console.log(res)
            if (res.code == '0') {
              this.$message({
                type: "success",
                message: "注册成功"
              })
              this.$router.push("/login")  //注册成功后跳转
            } else {
              this.$message({
                type: "error",
                message: res.msg
              })
            }
          })
        }
      })
    },
    Lock() {
      return Lock
    },
    User() {
      return User
    },
  }
}

</script>

<style scoped>

</style>