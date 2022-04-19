<template>
  <div class="app-container">
      <el-form label-width="120px">
        <el-form-item label="讲师名称">
          <el-input v-model="teacher.name"/>
        </el-form-item>
        <el-form-item label="讲师排序">
          <el-input-number v-model="teacher.sort" controls-position="right" min="0"/>
        </el-form-item>
        <el-form-item label="讲师头衔">
          <el-select v-model="teacher.level" clearable placeholder="请选择">
            <!--
              数据类型一定要和取出的json中的一致，否则没法回填
              因此，这里value使用动态绑定的值，保证其数据类型是number
            -->
            <el-option :value="1" label="高级讲师"/>
            <el-option :value="2" label="首席讲师"/>
          </el-select>
        </el-form-item>
        <el-form-item label="讲师资历">
          <el-input v-model="teacher.career"/>
        </el-form-item>
        <el-form-item label="讲师简介">
          <el-input v-model="teacher.intro" :rows="10" type="textarea"/>
        </el-form-item>

        <!-- 讲师头像：TODO -->
        <!-- 讲师头像 -->
        <el-form-item label="讲师头像">

            <!-- 头衔缩略图 -->
            <pan-thumb :image="teacher.avatar"/>
            <!-- 文件上传按钮 -->
            <el-button type="primary" icon="el-icon-upload" @click="imagecropperShow=true">更换头像
            </el-button>

            <!--
            v-show：是否显示上传组件
            :key：类似于id, id和图片对应. 如果一个页面多个图片上传控件，可以做区分. 如果上传的图片的id存在,则无法无法上传.
            :url：当点击确认,后台上传的url地址
            @close：关闭上传组件
            @crop-upload-success：上传成功后的回调 
              <input type="file" name="file"/>
            -->
            <image-cropper
                          v-show="imagecropperShow"
                          :width="280"
                          :height="320"
                          :key="imagecropperKey"
                          :url="BASE_API+'/ossService/file/upload'"
                          field="file"
                          @close="close"
                          @crop-upload-success="cropSuccess"/>
        </el-form-item>


        <el-form-item>
          <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存</el-button>
        </el-form-item>
    </el-form>
  </div>
</template>
<script>
import teacher from '@/api/edu/teacher'
import ImageCropper from '@/components/ImageCropper'
import PanThumb from '@/components/PanThumb'

export default {
  components: { ImageCropper, PanThumb },
  data() {
    return {
      teacher:{
        
      },//这里边写不写属性都可,因为程序会自动把页面绑定的属性添加进去
      saveBtnDisabled: false, // 保存按钮是否禁用
      //上传弹框组件是否显示
      imagecropperShow:false,
      imagecropperKey:0,//上传组件key值
      BASE_API:process.env.BASE_API,//获取dev.env.js里面地址
      
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
    close(){//关闭上传弹框的方法
      this.imagecropperShow = false
    },
    //上传成功方法
    cropSuccess(data){
      this.imagecropperShow = false
      //上传成功之后返回图片地址  data.url=response.data.url
      this.teacher.avatar = data.url
      this.imagecropperKey = this.imagecropperKey + 1//每上传一张,key就增加1,这样下一次就可以继续添加了
    },
    //初始化
    init(){
      //判断路径中是否有id来决定是否进行回显功能
      if(this.$route.params && this.$route.params.id){
        //从路径中获取id值
        const id = this.$route.params.id
        this.getTeacherInfo(id)//进行回显
      }else{
        this.teacher = {
          avatar:process.env.OSS_PATH +'/Cat.jpg'
        }
      }
    },
    saveOrUpdate(){
      if(!this.teacher.id){//通过判断id属性是否为空,来决定是添加还是修改
        //添加
        this.saveTeacher();
      }else{
        this.updateTeacher();
      }
      this.saveBtnDisabled = true
    },
    saveTeacher(){
      teacher.addTeacher(this.teacher).then(response=>{
        this.$message({
          type:'success',
          message:'添加成功!'
        });
      }).catch(error=>{
        this.$message({
          type:'error',
          message:'添加失败!'
        });
      })
      //回到列表页面,进行路由跳转
      this.$router.push({path:'/teacher/list'})
    },
    //根据讲师id查询讲师
    getTeacherInfo(id){
      teacher.getById(id)
      .then(response=>{
        this.teacher = response.data.teacher;
      })
    },
    //修改讲师
    updateTeacher(){
      teacher.updateById(this.teacher)
      .then(response=>{
          this.$message({
            type:'success',
            message:'修改成功!'
          });      
      }).catch(error=>{
        this.$message({
          type:'error',
          message:'修改失败!'
        });
      })
       //回到列表页面,路由跳转
      this.$router.push({path:'/teacher/list'})
       
    }
  }
}
</script>