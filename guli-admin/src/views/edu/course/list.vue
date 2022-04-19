<template>

  <div class="app-container">
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">

      <!-- 所属分类：级联下拉列表 -->
      <!-- 一级分类 -->
      <el-form-item label="课程类别">
        <el-select
          v-model="searchObj.subjectParentId"
          placeholder="请选择"
          @change="subjectLevelOneChanged">
          <el-option
            v-for="subject in subjectNestedList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"/>
        </el-select>

        <!-- 二级分类 -->
        <el-select v-model="searchObj.subjectId" placeholder="请选择">
          <el-option
            v-for="subject in subSubjectList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"/>
        </el-select>
      </el-form-item>

      <!-- 标题 -->
      <el-form-item>
        <el-input v-model="searchObj.title" placeholder="课程标题"/>
      </el-form-item>
      <!-- 讲师 -->
      <el-form-item>
        <el-select
          v-model="searchObj.teacherId"
          placeholder="请选择讲师">
          <el-option
            v-for="teacher in teacherList"
            :key="teacher.id"
            :label="teacher.name"
            :value="teacher.id"/>
        </el-select>
      </el-form-item>

      <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>
    
    <!-- 表格 -->
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="数据加载中"
      border
      fit
      highlight-current-row
      row-class-name="myClassList">

      <el-table-column
        label="序号"
        width="70"
        align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column label="课程信息" width="470" align="center">
        <template slot-scope="scope">
          <div class="info">
            <div class="pic">
              <img :src="scope.row.cover" alt="scope.row.title" width="150px">
            </div>
            <div class="title">
              <a href="">{{ scope.row.title }}</a>
              <p>{{ scope.row.lessonNum }}课时</p>
            </div>
          </div>

        </template>
      </el-table-column>

      <el-table-column label="创建时间" align="center">
        <template slot-scope="scope">
          {{ scope.row.gmtCreate.substr(0, 10) }}
        </template>
      </el-table-column>
      <el-table-column label="发布时间" align="center">
        <template slot-scope="scope">
          {{ scope.row.gmtModified.substr(0, 10) }}
        </template>
      </el-table-column>
      <el-table-column label="价格" width="100" align="center" >
        <template slot-scope="scope">
          {{ Number(scope.row.price) === 0 ? '免费' :
          '¥' + scope.row.price.toFixed(2) }}
        </template>
      </el-table-column>
      <el-table-column prop="buyCount" label="付费学员" width="100" align="center" >
        <template slot-scope="scope">
          {{ scope.row.buyCount }}人
        </template>
      </el-table-column>

      <el-table-column prop="viewCount" label="播放次数" width="100" align="center" >
        <template slot-scope="scope">
          {{ scope.row.viewCount }}次
        </template>
      </el-table-column>

      <el-table-column label="操作" width="150" align="center">
        <template slot-scope="scope">
          <router-link :to="'/course/info/'+scope.row.id">
            <el-button type="text" size="mini" icon="el-icon-edit" >编辑课程信息</el-button>
          </router-link>
          <router-link :to="'/course/chapter/'+scope.row.id">
            <el-button type="text" size="mini" icon="el-icon-edit" >编辑课程大纲</el-button>
          </router-link>
          <el-button type="text" size="mini" icon="el-icon-delete" @click="removeCourse(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

<!-- 分页 -->
<el-pagination
  :current-page="page"
  :page-size="limit"
  :total="total"
  style="padding: 30px 0; text-align: center;"
  layout="total, prev, pager, next, jumper"
  @current-change="getList"
/>

    

  </div>
</template>

<style scoped>
.myClassList .info {
    width: 450px;
    overflow: hidden;
}
.myClassList .info .pic {
    width: 150px;
    height: 90px;
    overflow: hidden;
    float: left;
}
.myClassList .info .pic a {
    display: block;
    width: 100%;
    height: 100%;
    margin: 0;
    padding: 0;
}
.myClassList .info .pic img {
    display: block;
    width: 100%;
}
.myClassList td .info .title {
    width: 280px;
    float: right;
    height: 90px;
}
.myClassList td .info .title a {
    display: block;
    height: 48px;
    line-height: 24px;
    overflow: hidden;
    color: #00baf2;
    margin-bottom: 12px;
}
.myClassList td .info .title p {
    line-height: 20px;
    margin-top: 5px;
    color: #818181;
}
</style>

<script>
import course from '@/api/edu/course'
import subject from '@/api/edu/subject'
export default {

   

    data() {
      return {
        listLoading: false, // 是否显示loading信息
        list: null, // 数据列表
        total: 0, // 总记录数
        page: 1, // 页码
        limit: 10, // 每页记录数
        searchObj: {
          subjectParentId: '',
          subjectId: '',
          title: '',
          teacherId: ''
        }, // 查询条件
        teacherList: [], // 讲师列表
        subjectNestedList: [], // 一级分类列表
        subSubjectList: [] // 二级分类列表,
      }
    },
    created() {
      this.init()
    },
    methods: {
        //编辑课程大纲
        updateChapter(id){
          this.$router.push({path:'/course/chapter/'+id})
        },
        //编辑课程信息
        updateCourse(id){
          this.$router.push({path:'/course/info/'+id})
        },
        removeCourse(id){
            this.$confirm('此操作将永久删除该课程的所有内容,是否继续?','提示',{
            confirmButtonText:'确定',
            cancelButtonText:'取消',
            type:'warning'
          }).then(()=>{//点击确定
              course.removeCourse(id).then((response)=>{
                this.$message({
                  type:'success',
                  message:'删除成功!' 
                }) 
                //回到列表页面
                this.getList()
              }).catch(()=>{
                this.$message({
                  type:'error',
                  message:'删除失败!' 
                }) 
              })
          }).catch(() => { // 点击取消
              this.$message({
                  type: 'info',
                  message: '已取消删除!'
              })
           })
        },
        //分页查询
        getList(page = 1){//page默认值为1,当使用分页组件时会传入新的page参数
          this.page = page
          course.pageQuery(this.page,this.limit,this.searchObj).then(response=>{
            this.list = response.data.list
            this.total = response.data.total
          })
        },
      //查询表单重置方法
        resetData(){
          this.searchObj = {},
          this.subSubjectList = []
        },
        //当一级分类下拉菜单改变时的方法
        subjectLevelOneChanged(id){
          // console.log(id);
          for(var i = 0;i < this.subjectNestedList.length;i++){
            if(this.subjectNestedList[i].id == id){
              this.subSubjectList = this.subjectNestedList[i].children
            }
          }
        },
        //初始化方法
        init(){
          //1.查询一级分类
          subject.getAllSubject().then(response=>{
            this.subjectNestedList = response.data.list
          })
          //2.查询所有讲师
          course.getTeacherList().then(response=>{
            this.teacherList = response.data.items
          })
          //3.默认查询
          this.getList()
        }
    }
}
</script>
