<template>
  <div class="app-container">
      <el-form label-width="120px">
        <el-form-item label="标题">
          <el-input v-model="banner.title"/>
        </el-form-item>
        <el-form-item label="排序">
          <el-input-number v-model="banner.sort" controls-position="right" min="0"/>
        </el-form-item>
        
        <el-form-item label="链接地址">
          <el-input v-model="banner.linkUrl"/>
        </el-form-item>

        <el-form-item label="轮播图片">

          <el-upload
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
            :action="BASE_API+'/ossService/file/upload'"
            class="avatar-uploader">
            <img :src="banner.imageUrl">
            
          </el-upload>

        </el-form-item>


        <el-form-item>
          <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存</el-button>
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
import banner from '@/api/edu/banner'

export default {
  
  data() {
    return {
      banner:{
        imageUrl:'https://online-teach-file.oss-cn-beijing.aliyuncs.com/cms/2019/11/14/297acd3b-b592-4cfb-a446-a28310369675.jpg',
        
      },//这里边写不写属性都可,因为程序会自动把页面绑定的属性添加进去
      saveBtnDisabled: false, // 保存按钮是否禁用
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
    //初始化
    init(){
      //判断路径中是否有id来决定是否进行回显功能
      if(this.$route.params && this.$route.params.id){
        //从路径中获取id值
        const id = this.$route.params.id
        this.getBannerInfo(id)//进行回显
      }else{
        this.banner = {
          imageUrl:'https://online-teach-file.oss-cn-beijing.aliyuncs.com/cms/2019/11/14/297acd3b-b592-4cfb-a446-a28310369675.jpg'
        }
      }
    },
    saveOrUpdate(){
      if(!this.banner.id){//通过判断id属性是否为空,来决定是添加还是修改
        //添加
        this.saveBanner();
      }else{
        this.updateBanner();
      }
      this.saveBtnDisabled = true
    },
    saveBanner(){
      banner.addBanner(this.banner).then(response=>{
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
      this.$router.push({path:'/banner/list'})
    },
    //根据id查询banner
    getBannerInfo(id){
      banner.getById(id)
      .then(response=>{
        this.banner = response.data.banner;
      })
    },
    // //修改banner
    updateBanner(){
      banner.editBanner(this.banner)
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
      this.$router.push({path:'/banner/list'})
       
    },
    handleAvatarSuccess(res,file){//头像上传成功后
          this.banner.imageUrl = res.data.url
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