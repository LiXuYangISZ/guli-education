<template>
  <div class="app-container">
    <!--查询条件 -->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="bannerQuery.title" placeholder="标题"/>
      </el-form-item>
    
      <el-form-item>
        <el-input v-model="bannerQuery.linkUrl" placeholder="链接地址"/>
      </el-form-item>
     
      <el-form-item label="添加时间">
        <el-date-picker
          v-model="bannerQuery.begin"
          type="datetime"
          placeholder="选择开始时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="bannerQuery.end"
          type="datetime"
          placeholder="选择截止时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>

      <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>
    <!-- 轮播图列表 -->
    <el-table
      v-loading="listLoading"
      :data="bannerList"
      element-loading-text="数据加载中"
      border
      fit
      highlight-current-row>

      <el-table-column
        label="序号"
        width="125"
        align="center">
         <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column label="轮播图图片" width="500" align="center">
        <template slot-scope="scope">
          <div class="info">
            <div class="pic">
              <img :src="scope.row.imageUrl" alt="scope.row.title" width="300px">
            </div>
          </div>

        </template>
      </el-table-column>

      <el-table-column prop="title" label="标题" width="130" align="center" />

     <el-table-column prop="linkUrl" label="链接地址" width="80" align="center" />

      <el-table-column prop="gmtCreate" label="添加时间" width="175" align="center" />

      <el-table-column prop="sort" label="排序" width="80" align="center" />

      <el-table-column label="操作" width="210" align="center">
        <template slot-scope="scope">
          <router-link :to="'/banner/edit/'+scope.row.id">
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
import banner from '@/api/edu/banner'

export default {
    //注意不可以这样哦 Vue实例里面data可以写函数式或者对象式，但是组件里面只能写函数式！！！
    // data:{}  
   data() {//定义变量和初始值
     return {
       bannerList:null,//查询之后接口返回集合
       page:1,//当前页
       limit:2,//每页记录数
       total:0,//总记录数
       bannerQuery:{}//条件封装对象
     }
   },
   created() {//在页面渲染之前执行,一般调用methods中的方法
      this.getList();
   },
   methods: {//创建具体的方法,调用teacher.js定义方法
      //轮播列表的方法
      getList(page = 1){//page默认值为1,当使用分页组件时会传入新的page参数
        this.page = page
        banner.pageQuery(this.page,this.limit,this.bannerQuery)
        .then(response=>{
          this.bannerList = response.data.items;
          console.log(response.data.total);
          this.total = response.data.total;
        }).catch(error=>{
          console.log(error)
        })
      
      },
      //清空方法
      resetData(){
        //表单输入项情况
        this.bannerQuery = {}
        //查询所有轮播列表数据
        this.getList()
      },
      //删除banner
      removeDataById(id){
        this.$confirm('此操作将永久此轮播图记录,是否继续?','提示',{
          confirmButtonText:'确定',
          cancelButtonText:'取消',
          type:'warning'
        }).then(()=>{//点击确定
          banner.removeBanner(id).then((response)=>{
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