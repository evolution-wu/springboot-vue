<template>
  <div style="padding: 10px">
    <!--功能区域-->
    <div style="margin: 10px 0px">
      <el-button type="primary" @click="insert">新增</el-button>
      <el-button type="primary">导入</el-button>
      <el-button type="primary">导出</el-button>
      <!--搜索区域-->
      <el-input v-model="search" placeholder="请输入关键字" style="width: 500px;padding-left: 200px" clearable/>
      <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>
    </div>
    <!--搜索区域
    <div style="margin: 10px 0px">
      <el-input v-model="search" placeholder="请输入关键字" />
    </div>-->
    <el-table :data="tableData" border stripe style="width: 100%">
      <el-table-column prop="id" label="ID" sortable/>
      <el-table-column prop="username" label="用户名"/>
      <el-table-column prop="nickName" label="昵称" />
      <el-table-column prop="age" label="年龄" />
      <el-table-column prop="sex" label="性别" />
      <el-table-column prop="address" label="地址" />
      <el-table-column label="Operations">
        <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.row)">更新</el-button>
          <el-popconfirm title="Are you sure to delete this?" @confirm="handleDelete(scope.row.id)">
            <template #reference>
              <el-button
                  size="small"
                  type="danger"
                 >删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div style="margin: 10px 0px;">
      <el-pagination
          current-page="currentPage"
          page-size="pageSize"
          :page-sizes="[5, 10, 20]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          background
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"/>

      <el-dialog v-model="dialogVisible" title="提示" width="30%">
        <el-form :model="form" label-width="120px">
          <el-form-item label="用户名" style="width: 80%">
            <el-input v-model="form.username" />
          </el-form-item>
          <el-form-item label="密码" style="width: 80%">
            <el-input v-model="form.password" />
          </el-form-item>
          <el-form-item label="昵称" style="width: 80%">
            <el-input v-model="form.nickName" />
          </el-form-item>
          <el-form-item label="年龄" style="width: 80%">
            <el-input v-model="form.age" />
          </el-form-item>
          <el-form-item label="性别" style="width: 80%">
              <el-radio v-model="form.sex" label="男">男</el-radio>
              <el-radio v-model="form.sex" label="女">女</el-radio>
          </el-form-item>
          <el-form-item label="地址" style="width: 80%">
              <el-input type="textarea" v-model="form.address" />
          </el-form-item>
        </el-form>
        <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="save">确定</el-button>
      </span>
        </template>
      </el-dialog>

    </div>

  </div>
</template>

<script>
// @ is an alias to /src




import request from "@/utils/request";

export default {
  name: 'User',
  components: {

  },
  data(){
    return {
      form: {},
      dialogVisible: false,
      currentPage: 1,
      pageSize: 10,
      total: 100,
      search:'',
      tableData:[]
    }
  },
  created() {
    this.load()
  },
  methods:{
    //表格的俩个按钮-->
    handleEdit(row) {
      //深拷贝
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible=true
    },
    handleDelete(id){
         console.log(id)
         request.delete("/user/"+id).then(res =>{
           if (res.code == '0'){
             this.$message({
               type: "success",
               message:"删除成功"
             })
           }else{
             this.$message({
               type: "error",
               message: res.msg
             })
           }
           this.load()//刷新表格数据
         })
    },
    //分页的按钮-->
    handleSizeChange(pageSize){
      this.pageSize=pageSize
      this.load()
    },
    handleCurrentChange(pageNum){
      this.currentPage=pageNum
      this.load()
    },
    insert(){
       this.dialogVisible=true
       this.form = {}
    },
    save() {
      if(this.form.id){
        //跟新
        request.put("/user",this.form).then(res =>{
          console.log(res)
          if (res.code == '0'){
            this.$message({
              type: "success",
              message:"更新成功"
            })
          }else{
            this.$message({
              type: "error",
              message: res.msg
            })
          }
          this.load()//刷新表格数据
          this.dialogVisible=false//关闭弹窗
        })
      }else{
        //新增
        request.post("/user",this.form).then(res =>{
          console.log(res)
          if (res.code == '0'){
            this.$message({
              type: "success",
              message:"新增成功"
            })
          }else{
            this.$message({
              type: "error",
              message: res.msg
            })
          }
          this.load()//刷新表格数据
          this.dialogVisible=false//关闭弹窗
        })
      }

    },
    load(){
      request.get("/user",{
        params: {
        pageNum: this.currentPage,
        pageSize: this.pageSize,
        search: this.search
        }
      }).then(res =>{
        console.log(res)
        this.tableData = res.data.records
        this.total = res.data.total
      })
    }

  }
}
</script>
