<template>

  <div class="app-container">

    <h2 style="text-align: center;">发布新课程</h2>

    <el-steps :active="2" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="提交审核"/>
    </el-steps>
    <el-button type="text" @click="openChapterDialog">添加章节</el-button>
    
    

    <ul class="chanpterList">
      <li
          v-for="chapter in chapterVideoList"
          :key="chapter.id">
          <p>
              {{ chapter.title }}

              <span class="acts">
                  <el-button type="text" @click="openVideoDialog(chapter.id)">添加课时</el-button>
                  <el-button type="text" @click="editChapter(chapter.id)">编辑</el-button>
                  <el-button type="text" @click="removeChapter(chapter.id)">删除</el-button>
              </span>
          </p>

          <!-- 视频 -->
          <ul class="chanpterList videoList">
              <li
                  v-for="video in chapter.children"
                  :key="video.id">
                  <p>{{ video.title }}
                      <span class="acts">
                          <el-button type="text" @click="editVideo(video.id)">编辑</el-button>
                          <el-button type="text" @click="delVideo(video.id)">删除</el-button>
                      </span>
                  </p>
              </li>
          </ul>
      </li>
    </ul>
    <el-form label-width="120px">
      <el-form-item>
        
        <el-button @click="previous">上一步</el-button>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="next">下一步</el-button>
      </el-form-item>
    </el-form>
    <!-- 添加和修改章节表单 -->
    <el-dialog :visible.sync="dialogChapterFormVisible" title="添加章节">
        <el-form :model="chapter" label-width="120px">
            <el-form-item label="章节标题">
                <el-input v-model="chapter.title"/>
            </el-form-item>
            <el-form-item label="章节排序">
                <el-input-number v-model="chapter.sort" :min="0" controls-position="right"/>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="dialogChapterFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="saveOrUpdate">确 定</el-button>
        </div>
    </el-dialog>

    <!-- 添加和修改课时表单 -->
    <el-dialog :visible.sync="dialogVideoFormVisible" title="添加课时">
      <el-form :model="video" label-width="120px">
        <el-form-item label="课时标题">
          <el-input v-model="video.title"/>
        </el-form-item>
        <el-form-item label="课时排序">
          <el-input-number v-model="video.sort" :min="0" controls-position="right"/>
        </el-form-item>
        <el-form-item label="是否免费">
          <el-radio-group v-model="video.isFree">
            <el-radio :label="true">免费</el-radio>
            <el-radio :label="false">默认</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="上传视频">
          <el-upload
                :on-success="handleVodUploadSuccess"
                :on-remove="handleVodRemove"
                :before-remove="beforeVodRemove"
                :on-exceed="handleUploadExceed"
                :file-list="fileList"
                :action="BASE_API+'/vodService/video/uploadVideoAly'"
                :limit="1"
                class="upload-demo">
          <el-button size="small" type="primary">上传视频</el-button>
          <el-tooltip placement="right-end">
              <div slot="content">最大支持1G，<br>
                  支持3GP、ASF、AVI、DAT、DV、FLV、F4V、<br>
                  GIF、M2T、M4V、MJ2、MJPEG、MKV、MOV、MP4、<br>
                  MPE、MPG、MPEG、MTS、OGG、QT、RM、RMVB、<br>
                  SWF、TS、VOB、WMV、WEBM 等视频格式上传</div>
              <i class="el-icon-question"/>
          </el-tooltip>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVideoFormVisible = false">取 消</el-button>
        <el-button :disabled="saveVideoBtnDisabled" type="primary" @click="saveOrUpdateVideo">确 定</el-button>
      </div>
    </el-dialog>


    
  </div>

</template>

<script>
import chapter from '@/api/edu/chapter'
import video from '@/api/edu/video'
export default {
    data() {
        return {
            saveBtnDisabled:false,
            saveVideoBtnDisabled: false, // 课时按钮是否禁用
            courseId:'',
            chapterId: '', // 课时所在的章节id
            chapterVideoList:[],
            dialogChapterFormVisible: false, //是否显示章节表单
            dialogVideoFormVisible: false, // 是否显示课时表单,
            fileList: [],//上传文件列表
            BASE_API: process.env.BASE_API, // 接口API地址 
            chapter: {// 章节对象
              title: '',
              sort: 0
            },
            video: {// 课时对象
              title: '',
              sort: 0,
              isFree: 0,
              videoSourceId: '',
              videoOriginalName:''
            }
        }
    },
    created() {
         //获取路由id
         if(this.$route.params && this.$route.params.id){
           this.courseId = this.$route.params.id
           //根据id查询章节和小节
           this.getAllChapterVideo()
         } 
    },
    methods: {
//########################小节的相关操作####################################
        //上传视频超过最大数量的方法
        handleUploadExceed(file,fileList){
            this.$message.warning('想要重新上传视频,请先删除已经上传的视频')
        },
        //点击确定调用的方法
        handleVodRemove(){
            video.removeAlyVideo(this.video.videoSourceId).then(response=>{
              this.$message({
                type:'success',
                message:'删除视频成功!' 
              }) 
              //把文件列表清空
              this.fileList = []
              //删除的视频信息不再存入数据库
              this.video.videoSourceId = ''
              this.video.videoOriginalName = ''
            })
            
        },
        //点击×调用的方法
        beforeVodRemove(file,fileList){
          return this.$confirm(`确定移除 ${file.name}?`)
        },
        //上传成功方法
        handleVodUploadSuccess(response,file,fileList){
            this.video.videoSourceId = response.data.videoId
            this.video.videoOriginalName = file.name
        },
        delVideo(id){
           this.$confirm('此操作将永久删除小节记录,是否继续?','提示',{
            confirmButtonText:'确定',
            cancelButtonText:'取消',
            type:'warning'
          }).then(()=>{//点击确定
              video.delVideo(id).then((response)=>{
              this.$message({
                type:'success',
                message:'删除成功!' 
              }) 
              //回到列表页面
              this.getAllChapterVideo()
            }).catch(()=>{
              this.$message({
                type:'error',
                message:'删除失败!' 
              }) 
            })
          }).catch(()=>{
            this.$message({
                type:'info',
                message:'取消删除!' 
              }) 
          })
        },
        //当点击更新/添加中的确认按钮时
        saveOrUpdateVideo(){
            if(!this.video.id){
              this.addVideo()
            }else{
              this.updateVideo()
            }
        },
        //当点击添加小节
        openVideoDialog(chapterId){
            this.dialogVideoFormVisible = true
            this.chapterId = chapterId
            this.fileList = []
            this.video ={// 课时对象  //打开之前先清空
              title: '',
              sort: 0,
              isFree: 0,
              videoSourceId: '',
              videoOriginalName:''
            }
        },
        //添加小节
        addVideo(){
          this.video.courseId = this.courseId
          this.video.chapterId = this.chapterId
          video.addVideo(this.video).then(response=>{
             this.dialogVideoFormVisible = false
            //给与提示
            this.$message({
              type:'success',
              message:'小节添加成功!'
            })
            //刷新页面
           this.getAllChapterVideo()//这些公共语句即使类似也必须放在then里面,不然逻辑就会混乱...
          }).catch(response=>{
            this.dialogVideoFormVisible = false
            //给与提示
            this.$message({
              type:'error',
              message:'小节添加失败!'
            })
            //刷新页面
           this.getAllChapterVideo()//这些公共语句即使类似也必须放在then里面,不然逻辑就会混乱...
          })
        },
        //编辑小节前的数据回显
        editVideo(id){
          this.openVideoDialog(id)//进行初始化
          this.dialogVideoFormVisible = true
          video.getVideo(id).then(response=>{
            //普通数据回显
            this.video = response.data.video
            //进行视频回显
            if(this.video.videoOriginalName!=""){
                this.fileList = [{name:this.video.videoOriginalName}]
            }
          })
        },
        //编辑小节
        updateVideo(){
          video.updateVideo(this.video).then(response=>{
            //给与提示
            this.$message({
              type:'success',
              message:'小节编辑成功!'
            })
              //关闭弹框
            this.dialogVideoFormVisible = false
            this.getAllChapterVideo()
          }).catch(response=>{
            //给与提示
            this.$message({
              type:'error',
              message:'小节编辑成功!'
            })
              //关闭弹框
            this.dialogVideoFormVisible = false
            this.getAllChapterVideo()
          })
        },
// #################章节的相关操作#######################################
        removeChapter(chapterId){
            this.$confirm('此操作将永久删除章节记录,是否继续?','提示',{
            confirmButtonText:'确定',
            cancelButtonText:'取消',
            type:'warning'
        }).then(()=>{//点击确定
            chapter.delChapter(chapterId).then((response)=>{
            this.$message({
              type:'success',
              message:'删除成功!' 
             }) 
            //回到列表页面
            this.getAllChapterVideo()
          }).catch((response)=>{
             this.$message({
                  type: 'error',
                  message: response.data.message
              })
          })
        })
        .catch((response) => { // 失败
          this.$message({
                type: 'info',
                message: '取消删除!'
            })
          this.getAllChapterVideo()
        } 
      )
    },
        openChapterDialog(){
          console.log("12356");
          this.dialogChapterFormVisible = true
          this.chapter = {title: '',sort: 0}
        },
        editChapter(id){
          this.dialogChapterFormVisible = true
          chapter.getChapter(id).then(response=>{
            this.chapter = response.data.chapter
          })
        },
        updateChapter(){
          chapter.updateChapter(this.chapter).then(response=>{
            //给与提示
            this.$message({
              type:'success',
              message:'章节编辑成功!'
            })
              //关闭弹框
            this.dialogChapterFormVisible = false
            this.getAllChapterVideo()
          }).catch(response=>{
            //给与提示
            this.$message({
              type:'error',
              message:'章节编辑失败!'
            })
              //关闭弹框
            this.dialogChapterFormVisible = false
            this.getAllChapterVideo()
          })
           
        },
        addChapter(){
          this.chapter.courseId = this.courseId
          chapter.addChapter(this.chapter).then(response=>{
            this.dialogChapterFormVisible = false
            //给与提示
            this.$message({
              type:'success',
              message:'章节添加成功!'
            })
            //刷新页面
           this.getAllChapterVideo()//这些公共语句即使类似也必须放在then里面,不然逻辑就会混乱...
          }).catch(response=>{
            this.dialogChapterFormVisible = false
            //给与提示
            this.$message({
              type:'error',
              message:'章节添加失败!'
            })
            //刷新页面
           this.getAllChapterVideo()
          })
          
        },
        saveOrUpdate(){
          if(!this.chapter.id || this.chapter.id==""){
            this.addChapter()
          }else{
            this.updateChapter()
          }
        },
        //根据课程id查询章节和小节
        getAllChapterVideo(){
          chapter.getChapterVideo(this.courseId).then(response=>{
            this.chapterVideoList =  response.data.list
            // console.log(this.chapterVideoList);
          })
        },
        previous(){
            this.$router.push({path:'/course/info/'+this.courseId})
        },
        next(){
            this.$router.push({path:'/course/publish/'+this.courseId})
        }
    }
}
</script>

<style scoped>
.chanpterList{
    position: relative;
    list-style: none;
    margin: 0;
    padding: 0;
}
.chanpterList li{
  position: relative;
}
.chanpterList p{
  /* float: left; */
  font-size: 20px;
  margin: 10px 0;
  padding: 10px;
  height: 70px;
  line-height: 50px;
  width: 100%;
  border: 1px solid #DDD;
}
.chanpterList .acts {
    float: right;
    font-size: 14px;
}

.videoList{
  padding-left: 50px;
}
.videoList p{
  /* float: left; */
  font-size: 14px;
  margin: 10px 0;
  padding: 10px;
  height: 50px;
  line-height: 30px;
  width: 100%;
  border: 1px dotted #DDD;
}

</style>