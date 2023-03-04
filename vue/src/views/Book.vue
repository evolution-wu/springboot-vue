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
      <el-table-column prop="id" label="ID" sortable/>  <!--注意这字段名要和后端的实体类属性得对应上-->
      <el-table-column prop="name" label="名称"/>
      <el-table-column prop="price" label="单价" />
      <el-table-column prop="author" label="作者" />
      <el-table-column prop="createTime" label="出版日期" />
      <el-table-column  label="封面">
        <template #default="scope">
          <el-image
              style="width: 100px; height: 100px"
              :src="scope.row.cover"
              :preview-src-list="[scope.row.cover]"
          />
        </template>
      </el-table-column>
      <el-table-column label="Operations">
        <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.row)">更新</el-button> <!--更新得话，当然是一条数据，所有是要传row-->
          <el-popconfirm title="Are you sure to delete this?" @confirm="handleDelete(scope.row.id)">
            <template #reference>        <!--删除得话，只需要主键id就行了，所有是要传row.id-->
              <el-button
                  size="small"
                  type="danger"
              >删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div style="margin: 10px 0px;"> <!--分页插件直接抄算了，还行得-->
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
          <el-form-item label="书名" style="width: 80%">
            <el-input v-model="form.name" />
          </el-form-item>                                   <!--注意这字段名要和后端的实体类属性得对应上-->
          <el-form-item label="单价" style="width: 80%">
            <el-input v-model="form.price" />
          </el-form-item>
          <el-form-item label="作者" style="width: 80%">
            <el-input v-model="form.author" />
          </el-form-item>
          <el-form-item label="出版日期" style="width: 80%">
                      <el-date-picker v-model="form.createTime" value-format="YYYY-MM-DD" type="date" style="width: 80%" clearable></el-date-picker>
        </el-form-item>
          <el-form-item label="封面">
            <el-upload ref="upload"
                action="http://localhost:8888/files/upload" :on-success="filesUploadSuccess">
              <el-button type="primary">Click to upload(上传)</el-button>
            </el-upload>
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
  name: 'Book',
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
      //深拷贝------注意这里得功能，首先先把该行记录转化成json对象给form，之后先弹出修改况，然后真正
      //更新操作是在save中得，因为你更新和插入得逻辑一样得，最后都是后端insert语句
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible=true
      //注意异步操作，当弹出框显示之后在清空那个啥
      this.$nextTick(()=>{
        this.$refs['upload'].clearFiles()   //upload中当新增后记得清空上一次文件记录
      })
    },
    handleDelete(id){
      //删除操作得话，通过主键ID进行删除，注意是delete请求
      console.log(id)
      request.delete("/book/"+id).then(res =>{
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
        this.load()  //最后注意要刷新表格数据
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
      //注意新增操作，弹出眶后因为你点了保存后，会嵌套执行save方法，存储后，
      this.dialogVisible=true
      //this.form = {}  其实确实不用这个
      this.$refs['upload'].clearFiles()   //upload中当新增后记得清空上一次文件记录
    },
    save() {
      if(this.form.id){
        //跟新
        request.put("/book",this.form).then(res =>{
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
        request.post("/book",this.form).then(res =>{
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
    load(){   //加载数据，照抄就行
      request.get("/book",{
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
    },
    //上传文件得方法
    filesUploadSuccess(res){
        console.log(res)
        this.form.cover = res.data
    }

  }
}
</script>

