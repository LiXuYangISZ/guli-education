<template>

  <div class="app-container">

    <h2 style="text-align: center;">发布新课程</h2>

    <el-steps :active="1" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="提交审核"/>
    </el-steps>

    <el-form label-width="120px">

      <el-form-item label="课程标题">
        <el-input v-model="courseInfo.title" placeholder=" 示例：机器学习项目课：从基础到搭建项目视频课程。专业名称注意大小写"/>
      </el-form-item>

        <!-- 所属分类  -->
        <!-- 第一种写法使用级联选择器 -->
        <!-- 
          filterable:搜索功能(不区分大小写)
          v-model:绑定的属性值,是个value数组
          props:定义模板数据
          @change:当选择之后的回调函数
         -->
      <!-- <el-form-item label="课程分类">
        <el-cascader
        filterable 
        v-model="subject"
        :options="subjectList"
        :props="subProps"
        @change="handleChange">
      </el-cascader>
      </el-form-item> -->
     <!-- 第二种写法 -->
        <el-form-item label="课程分类">
          <el-select
            filterable
            v-model="courseInfo.subjectParentId"
            placeholder="一级分类"
            @change="subjectLevelOneChanged"
            >

            <el-option
                v-for="subject in subjectOneList"
                :key="subject.id"
                :label="subject.title"
                :value="subject.id"/>
          </el-select>
          <el-select filterable v-model="courseInfo.subjectId" placeholder="二级分类">
            <el-option
                v-for="subject in subjectTwoList"
                :key="subject.id"
                :label="subject.title"
                :value="subject.id"/>
          </el-select>


        </el-form-item>



        <!-- 课程讲师 -->
        <el-form-item label="课程讲师">
          <el-select
          filterable
            v-model="courseInfo.teacherId"
            placeholder="请选择"
            
            >
            <el-option
              v-for="teacher in teacherList"
              :key="teacher.id"
              :label="teacher.name"
              :value="teacher.id"/>
          </el-select>
        </el-form-item>

        <el-form-item label="总课时">
          <el-input-number :min="0" v-model="courseInfo.lessonNum" controls-position="right" placeholder="请填写课程的总课时数"/>
        </el-form-item>

        <!-- 课程简介 -->
        <!-- 课程简介-->
          <el-form-item label="课程简介">
              <tinymce :height="300" v-model="courseInfo.description"/>
          </el-form-item>

        <!-- 课程封面-->
        <el-form-item label="课程封面">

          <el-upload
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
            :action="BASE_API+'/ossService/file/upload'"
            class="avatar-uploader">
            <img :src="courseInfo.cover">
            
          </el-upload>

        </el-form-item>

        <el-form-item label="课程价格">
          <el-input-number :min="0" v-model="courseInfo.price" controls-position="right" placeholder="免费课程请设置为0元"/> 元
        </el-form-item>

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存并下一步</el-button>
      </el-form-item>
</el-form>
  </div>
</template>

<style scoped>
.tinymce-container {
  line-height: 29px;
}
</style>

<script>
import course from '@/api/edu/course'
import subject from '@/api/edu/subject'
import Tinymce from '@/components/Tinymce'
export default {

    components: { Tinymce },

    data() {
        return {
            saveBtnDisabled:false,
            courseInfo:{
              title: '',
              subjectId: '',
              teacherId: '',
              subjectParentId:'',
              lessonNum: 0,
              description: '',
              cover: 'https://guli-photos.oss-cn-hangzhou.aliyuncs.com/course.jpg',
              price: 0
            },
            teacherList:[],
            subjectList:[],//课程分类列表
            subProps:{//模板数据
              value:'id',
              label:'title',
              children:'children'
            },
            subject:[],//选中的二级分类的id
            subjectOneList:[],//一级分类
            subjectTwoList:[],//二级分类
            BASE_API: process.env.BASE_API, // 接口API地址
            OSS_PATH: process.env.OSS_PATH,//OSS地址
            courseId:'',

            
        }
    },
    created() {
      this.init()
    },
    watch:{//监听
      $route(to,from){//当路由路径发生变化时,方法就会被执行
        this.init()
      }
    },
    methods: {
        init(){//初始化方法封装
          if(this.$route.params && this.$route.params.id){//如果是数据回显
            this.courseId = this.$route.params.id
            //查询课程信息
            this.getCourseInfo()
          }else{//如果是课程添加
            this.courseInfo = {cover: 'https://guli-photos.oss-cn-hangzhou.aliyuncs.com/course.jpg'}//先清空...
            this.getOneSubject()//查询所有一级分类
          }
          // this.getAllSubject()//方法二:级联下拉菜单
          this.getTeacherList()
        },
        //注意在进行数据回显操作时,对于下拉菜单,如果你的回显列表id和下拉菜单列表中的id对应,该option会被加入一个selected属性.
        //通过id查询课程信息(进行回显操作)
        getCourseInfo(){
          course.getCourseInfo(this.courseId).then(response=>{
            this.courseInfo = response.data.courseInfoVo
            //方法一:普通二级 查询所有的分类,包含一级和二级
            //注意这里不能直接调用下面的subjectLevelOneChanged.因为该方法最后会把之前二级id进行清空.
            subject.getAllSubject().then(response=>{
              this.subjectOneList = response.data.list
              for(var i = 0;i < this.subjectOneList.length;i++){
                if(this.subjectOneList[i].id==this.courseInfo.subjectParentId){{
                  this.subjectTwoList = this.subjectOneList[i].children
                }}
              }
            });
            // this.subject.push(this.courseInfo.subjectParentId) //方法二:级联下拉菜单
            // this.subject.push(this.courseInfo.subjectId)
          })
        },
        addCourseInfo(){
          course.saveCourseInfo(this.courseInfo)
          .then(response=>{
              this.$message({
                type:'success',
                message:'课程添加成功!'
              })
              //跳转到课程大纲页面
              this.$router.push({path:'/course/chapter/'+response.data.courseId})
          }).catch(response=>{
            this.$message({
                type:'error',
                message:'课程添加失败!'
              })
          })
        },
        updateCourseInfo(){
            course.updateCourseInfo(this.courseInfo).then(response=>{
              this.$message({
                type:'success',
                message:'课程修改成功!'
              })
              //跳转到课程大纲页面
              this.$router.push({path:'/course/chapter/'+this.courseId})
            }).catch(response=>{
                this.$message({
                type:'error',
                message:'课程修改失败!'
              })
            })
        },
        //提交课程信息
        saveOrUpdate(){
          if(!this.courseInfo.id){
            // console.log("添加课程");
            this.addCourseInfo()
          }else{
            // console.log("修改课程");
            this.updateCourseInfo()
          }
            
        },
        //查询所有讲师
        getTeacherList(){
            course.getTeacherList().then(response=>{
              this.teacherList = response.data.items
            })
        },
        //查询所有分类(级联下拉的方法)
        getAllSubject(){
          subject.getAllSubject().then(response=>{
            this.subjectList = response.data.list
          })
        },
        handleChange(){//参数为value数据,如果不需要可以不写.
          console.log(this.subject);
          this.courseInfo.subjectId = this.subject[1]
          this.courseInfo.subjectParentId = this.subject[0]
          // console.log('subjectId:'+this.courseInfo.subjectId);
          // console.log('subjectParentId:'+this.courseInfo.subjectParentId);
        },
        //查询所有的一级分类
        getOneSubject() {
            subject.getAllSubject()
                .then(response => {
                    this.subjectOneList = response.data.list
                })
        },
        subjectLevelOneChanged(value){
            //value就是一级分类id值
            //遍历所有的分类，包含一级和二级
            for(var i=0;i<this.subjectOneList.length;i++) {
                //每个一级分类
                var oneSubject = this.subjectOneList[i]
                //判断：所有一级分类id 和 点击一级分类id是否一样
                if(value === oneSubject.id) {
                    //从一级分类获取里面所有的二级分类
                    this.subjectTwoList = oneSubject.children
                }
            }
            //把二级分类id值清空
            this.courseInfo.subjectId = ''
        },
        handleAvatarSuccess(res,file){//头像上传成功后
          this.courseInfo.cover = res.data.url
        },
        beforeAvatarUpload(file){//上传头像之前进行照片校验
           const isPic = (file.type === 'image/jpeg' || file.type === 'image/jpg' ||  file.type === 'image/png')
           const isLt2M = file.size / 1024 / 1024 < 2

           if (!isPic) {
             this.$message.error('上传头像图片只能是 JPG/JPEG/PNG 格式!')
           }
           if (!isLt2M) {
             this.$message.error('上传头像图片大小不能超过 2MB!')
           }
           return isPic && isLt2M
        }

    }
}
</script>
