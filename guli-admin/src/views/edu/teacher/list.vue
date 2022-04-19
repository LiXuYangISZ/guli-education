<template>
  <div class="app-container">
    <!--查询条件 -->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="teacherQuery.name" placeholder="讲师名"/>
      </el-form-item>

      <el-form-item>
        <el-select v-model="teacherQuery.level" clearable placeholder="讲师头衔">
          <el-option :value="1" label="高级讲师"/>
          <el-option :value="2" label="首席讲师"/>
        </el-select>
      </el-form-item>

      <el-form-item label="添加时间">
        <el-date-picker
          v-model="teacherQuery.begin"
          type="datetime"
          placeholder="选择开始时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="teacherQuery.end"
          type="datetime"
          placeholder="选择截止时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>

      <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>
    <!-- 讲师列表 -->
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="数据加载中"
      border
      fit
      highlight-current-row>

      <el-table-column
        label="序号"
        width="70"
        align="center">
         <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="name" label="名称" width="80" />

      <el-table-column label="头衔" width="80">
        <template slot-scope="scope">
          <!-- ===表示类型和数值都一样  ==表示数值一样 -->
          {{ scope.row.level===1?'高级讲师':'首席讲师' }}
        </template>
      </el-table-column>

      <el-table-column prop="intro" label="简历" />

      <el-table-column prop="gmtCreate" label="添加时间" width="160"/>

      <el-table-column prop="sort" label="排序" width="60" />

      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <router-link :to="'/teacher/edit/'+scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">修改</el-button>
          </router-link>
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

       <!-- 分页 -->
    <el-pagination
      background
      :current-page="page"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper"
      @current-change="getList"
    />

      
  </div>

</template>
<script>
import teacher from '@/api/edu/teacher'

export default {
    //注意不可以这样哦 Vue实例里面data可以写函数式或者对象式，但是组件里面只能写函数式！！！
    // data:{}  
   data() {//定义变量和初始值
     return {
       list:null,//查询之后接口返回集合
       page:1,//当前页
       limit:5,//每页记录数
       total:0,//总记录数
       teacherQuery:{}//条件封装对象
     }
   },
   created() {//在页面渲染之前执行,一般调用methods中的方法
      this.getList();
   },
   methods: {//创建具体的方法,调用teacher.js定义方法
    //讲师列表的方法
      getList(page = 1){//page默认值为1,当使用分页组件时会传入新的page参数
        this.page = page
        teacher.getTeacherListPage(this.page,this.limit,this.teacherQuery)
        .then(response=>{
          this.list = response.data.rows;
          this.total = response.data.total;
          // console.log(response);
        }).catch(error=>{
          console.log(error)
        })
      
      },
      //清空方法
      resetData(){
        //表单输入项情况
        this.teacherQuery = {}
        //查询所有讲师数据
        this.getList()
      },
      //删除讲师
      removeDataById(id){
        this.$confirm('此操作将永久删除讲师记录,是否继续?','提示',{
          confirmButtonText:'确定',
          cancelButtonText:'取消',
          type:'warning'
        }).then(()=>{//点击确定
          teacher.removeById(id).then((response)=>{
            this.$message({
              type:'success',
              message:'删除成功!' 
             }) 
            //回到列表页面
            this.getList()
          }).catch(()=>{
            this.$message({
                type: 'error',
                message: '删除失败!'
            })
          })
        }).catch(() => { // 失败
          this.$message({
                type: 'info',
                message: '已取消删除!'
            })
     })
  }

  }
}
</script>