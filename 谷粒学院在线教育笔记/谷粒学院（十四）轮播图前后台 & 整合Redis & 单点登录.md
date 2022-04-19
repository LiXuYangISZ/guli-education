[TOC]

------



# 一、首页显示banner数据 – 前后台的后端

### 1、在service模块下创建子模块service-cms

### 2、使用代码生成器生成banner代码

（1）创建crm_banner表

（2）生成代码

![image-20220309104903505](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202203091049946.png)

### 3、配置application.yml

```properties
mybatis-plus:
    configuration:
        log-impl: org.apache.ibatis.logging.stdout.StdOutImpl #mybatis日志
    mapper-locations: classpath:com/rg/cmsservice/mapper/xml/*.xml #配置mapper xml文件的路径
server:
    port: 8004
spring:
    application:
        name: service-cms
    datasource: # mysql数据库连接
        driver-class-name: com.mysql.jdbc.Driver
        password: 186259
        url: jdbc:mysql://localhost:3306/guli_edu
        username: root
    jackson: #返回json的全局时间格式
        date-format: yyyy-MM-dd HH:mm:ss
        time-zone: GMT+8

    cloud: #nacos服务地址
        nacos:
            discovery:
                server-addr: 192.168.174.128:8848
    redis: #redis相关配置
        host: 192.168.174.128
        port: 6379
        database: 0
        timeout: 1800000
        lettuce: #最大阻塞等待时间
            pool:
                max-active: 20
                max-wait: -1
                max-idle: 5
                min-idle: 0
```

### 4、创建启动类

创建CmsApplication.java

```java
@SpringBootApplication
@ComponentScan("com.rg")
@MapperScan("com.rg.cmsservice.mapper")
public class CmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class, args);
    }

}
```

### 5、编写Controller

banner后台分页查询、添加、修改、删除接口

```java
@RestController
@RequestMapping("/cmsservice/banneradmin")
@CrossOrigin
public class CrmBannerAdminController {
    @Autowired
    private CrmBannerService bannerService;

    //查询轮播图列表
    @PostMapping("pageQuery/{current}/{limit}")
    public R pageQuery(@PathVariable("current") Long current, @PathVariable("limit") Long limit,@RequestBody BannerQuery bannerQuery ){
        Page <CrmBanner> page = bannerService.pageQuery(current,limit,bannerQuery);
        return R.ok().data("items",page.getRecords()).data("total",page.getTotal());
    }

    //根据id获取
    @GetMapping("getById/{id}")
    public R getById(@PathVariable("id") String id){
        CrmBanner banner = bannerService.getById(id);
        return R.ok().data("banner",banner);
    }

    //增加一张轮播图
    @PostMapping("addBanner")
    @CacheEvict(value = "banner", allEntries=true)
    public R addBanner(@RequestBody CrmBanner banner){
        boolean flag = bannerService.save(banner);
        if(flag){
            return R.ok();
        }else{
            return R.error();
        }
    }

    //修改轮播图
    @PutMapping("editBanner")
    @CacheEvict(value = "banner", allEntries=true)  //当进行增删改时,清空banner缓存
    public R editBanner(@RequestBody CrmBanner banner){
        boolean flag = bannerService.updateById(banner);
        if(flag){
            return R.ok();
        }else{
            return R.error();
        }
    }

    //删除轮播图
    @DeleteMapping("removeBanner/{id}")
    @CacheEvict(value = "banner", allEntries=true)
    public R removeBanner(@PathVariable("id") String id){
        boolean flag = bannerService.removeById(id);
        if(flag){
            return R.ok();
        }else{
            return R.error();
        }
    }

}
```

banner前台首页获取数据

```java
@RestController
@RequestMapping("/cmsservice/bannerfront")
@CrossOrigin
public class CrmBannerFrontController {

    @Autowired
    private CrmBannerService bannerService;

    //查询所有轮播图
    @Cacheable(value = "banner",key="'bannerList'")
    @GetMapping("findAllBanner")
    public R findAllBanner(){
        List <CrmBanner> bannerList = bannerService.findAll();
        return R.ok().data("bannerList", bannerList);
    }

}
```

### 6、编写Service

**CrmBannerService**

```java
public interface CrmBannerService extends IService<CrmBanner> {

    List<CrmBanner> findAll();


    //分页查询带条件
    Page<CrmBanner> pageQuery(Long current, Long limit, BannerQuery bannerQuery);

}
```

**CrmBannerServiceImpl**

```java
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    //查询所有banner
    @Override
    public List <CrmBanner> findAll() {
        //根据sort排列,显示前两条记录
        QueryWrapper <CrmBanner> wrapper = new QueryWrapper <>();
        wrapper.orderByDesc("sort").last("limit 2");//last:拼接sql语句
        List <CrmBanner> bannerList = this.baseMapper.selectList(wrapper);
        return bannerList;
    }


    //分页查询带条件
    @Override
    public Page <CrmBanner> pageQuery(Long current, Long limit, BannerQuery bannerQuery) {
        Page <CrmBanner> page = new Page <>(current, limit);
        QueryWrapper <CrmBanner> wrapper = new QueryWrapper <>();
        String title = bannerQuery.getTitle();
        String linkUrl = bannerQuery.getLinkUrl();
        String begin = bannerQuery.getBegin();
        String end = bannerQuery.getEnd();

        if(!StringUtils.isEmpty(title)){
            wrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(linkUrl)){
            wrapper.like("link_url",linkUrl);
        }

        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create", begin);
        }

        if(!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create", end);
        }
        //按照sort降序
        wrapper.orderByDesc("sort");

        this.baseMapper.selectPage(page, wrapper);
        return page;
    }
}
```



### 8、首页显示课程名师数据

- 在service_edu 模块的Controller包下创建front包的IndexFrontController 类

```java
@CrossOrigin
@RestController
@RequestMapping("/eduservice/index")
public class IndexController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;

    //查询前八条热门课程,前四条讲师记录
    @GetMapping("findCourseAndTeacher")
    public R findCourseAndTeacher(){

        List <EduCourse> courseList  = courseService.findCourse();

        List <EduTeacher> teacherList = teacherService.findTeacher();

        return R.ok().data("courseList",courseList).data("teacherList",teacherList);
    }

}
```

- EduCourseService类

```java
//查询前8条热门课程(前台页面)
@Override
@Cacheable(value = "course",key="'courseList'")
public List <EduCourse> findCourse() {
    //查询前8条热门课程 按照view_count,buy_count排序
    QueryWrapper <EduCourse> courseWrapper = new QueryWrapper <>();
    courseWrapper.orderByDesc("view_count","buy_count");
    courseWrapper.last("limit 8");
    // this.baseMapper.select
    List <EduCourse> courseList = this.baseMapper.selectList(courseWrapper);

    return courseList;
}
```

- EduTeacherService类

```java
//查询前四条讲师记录(前台页面)
@Override
@Cacheable(value = "teacher",key="'teacherList'")
public List <EduTeacher> findTeacher() {
    QueryWrapper <EduTeacher> teacherWrapper = new QueryWrapper <>();
    teacherWrapper.orderByDesc("sort");
    teacherWrapper.last("limit 4");
    List <EduTeacher> teacherList = this.baseMapper.selectList(teacherWrapper);
    return teacherList;
}
```

# 二 、首页banner数据 – 后台的前端

实现banner后台的添加修改删除和分页查询操作，和其他后台管理模块类似

### 1、添加路由

```json
{
        path: '/banner',
        component: Layout, //布局
        redirect: '/banner/table',
        name: '轮播图管理',
        meta: { title: '轮播图管理', icon: 'example' },
        children: [{
                path: 'list',
                name: '轮播图列表',
                component: () =>
                    import ('@/views/edu/banner/list'),
                meta: { title: '轮播图列表', icon: 'table' }
            },
            {
                path: 'add',
                name: '添加轮播图',
                component: () =>
                    import ('@/views/edu/banner/add'),
                meta: { title: '添加轮播图', icon: 'tree' }
            },
            {
                path: 'edit/:id', //隐藏路由的写法. :id类似于sql中的占位符,用来接收参数
                name: '编辑轮播图',
                component: () =>
                    import ('@/views/edu/banner/add'), //因为修改和编辑使用同一个表单,所以使用同一个路由.
                meta: { title: '编辑轮播图', noCache: true },
                hidden: true //将该路由进行隐藏,让用户看起来如同是在当前页面进行修改.
            }
        ]
    }
```

### 2、编写api接口---banner.js

```javascript
import request from '@/utils/request'

export default {
    //分页查询banner
    pageQuery(current,limit,bannerQuery) {
        return request({
            url: `/cmsservice/banneradmin/pageQuery/${current}/${limit}`,
            method: 'post',
            data:bannerQuery
        })
    },
    //添加banner
    addBanner(banner){
        return request({
            url:`/cmsservice/banneradmin/addBanner`,
            method:`post`,
            data:banner
        })
    },
    //修改banner
    editBanner(banner){
        return request({
            url:`/cmsservice/banneradmin/editBanner`,
            method:`put`,
            data:banner
        })
    },
    //删除banner
    removeBanner(id){
        return request({
            url:`/cmsservice/banneradmin/removeBanner/${id}`,
            method:`delete`
        })
    },
    //通过id进行查询
    getById(id){
        return request({
            url:`/cmsservice/banneradmin/getById/${id}`,
            method:'get'
        })
    }
}
```

### 3、编写list.vue页面

```html
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
```

### 4、编写add.vue页面

```html
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
```

### 5、效果展示

![image-20220309110451414](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202203091104740.png)

![image-20220309110555473](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202203091105819.png)

# 三、首页显示banner数据 – 前台的前端

### 1、准备工作

（1）使用命令，下载axios依赖

```bash
npm install axios
```

（2）封装axios

项目下建立utils文件夹,里面新建request.js,用于对axios的封装.

![image-20220309111241717](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202203091112051.png)

```javascript
import axios from 'axios'
// 创建axios实例
const service = axios.create({
  baseURL: 'http://localhost:9001', // api的base_url
  timeout: 20000 // 请求超时时间
})
export default service
```

### 2、首页banner 数据显示

（1）创建api 文件夹，在api文件夹创建banner.js文件，定义调用接口路径

```javascript
import request from '@/utils/request'

export default {
    //查询前两条banner数据
    findAllBanner(){
        return request({
            url:`/cmsservice/bannerfront/findAllBanner`,
            method:'get'
        })
    }
}
```

（2）在index.vue页面调用接口得到数据进行显示

```javascript
 created() {
    //查询轮播图
    this.getBannerList()
  },
  methods: {
    //查询首页轮播图
    getBannerList(){
      banner.findAllBanner().then(response=>{
        this.bannerList = response.data.data.bannerList
      })
    }
   //...
  }
```

（3）修改轮播图展示页面

```html
<!-- 幻灯片 开始 -->
<div v-swiper:mySwiper="swiperOption">
    <div class="swiper-wrapper">

        <div v-for="banner in bannerList" :key="banner.id" class="swiper-slide" style="background: #040B1B;">
            <a target="_blank" :href="banner.linkUrl">
                <img :src="banner.imageUrl" :alt="banner.title">
            </a>
        </div>
    </div>
    <div class="swiper-pagination swiper-pagination-white"></div>
    <div class="swiper-button-prev swiper-button-white" slot="button-prev"></div>
    <div class="swiper-button-next swiper-button-white" slot="button-next"></div>
</div>
<!-- 幻灯片 结束 -->
```

### 3、nginx进行访问规则配置

![image-20220309112046088](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202203091120175.png)

### 4、首页显示课程名师数据

（1）在api中定义接口---index.js

```javascript
import request from '@/utils/request'

export default {
    //查询前两条banner数据
    findCourseAndTeacher(){
        return request({
            url:`/eduservice/index/findCourseAndTeacher`,
            method:'get'
        })
    }
}
```

（2）在index.vue页面调用接口得到数据进行显示

```javascript
created() {
  	//...
    //查询讲师和课程
    this.getCourseAndTeacher()
  },
  methods: {
   	//...
    //查询热门课程和讲师
    getCourseAndTeacher(){
      index.findCourseAndTeacher()
      .then(response=>{
        this.courseList = response.data.data.courseList
        this.teacherList =response.data.data.teacherList
      })
    }
  }
```

（3）课程模块遍历显示

```html
<li v-for="course in eduList" :key="course.id">
  <div class="cc-l-wrap">
    <section class="course-img">
      <img
        :src="course.cover"
        class="img-responsive"
        :alt="course.title"
      >
      <div class="cc-mask">
        <a href="#" title="开始学习" class="comm-btn c-btn-1">开始学习</a>
      </div>
    </section>
    <h3 class="hLh30 txtOf mt10">
      <a href="#" :title="course.title" class="course-title fsize18 c-333">{{course.title}}</a>
    </h3>
    <section class="mt10 hLh20 of">
      <span class="fr jgTag bg-green" v-if="Number(course.price) === 0">
        <i class="c-fff fsize12 f-fA">免费</i>
      </span>
      <span class="fr jgTag bg-green" v-else>
        <i class="c-fff fsize12 f-fA">￥{{course.price}}</i>
      </span>
      <span class="fl jgAttr c-ccc f-fA">
        <i class="c-999 f-fA">{{course.buyCount}} 人学习</i>
        |
        <i class="c-999 f-fA">{{course.viewCount}} 人浏览</i>
      </span>
    </section>
  </div>
</li>
```

（4）名师模块遍历显示

```html
<li v-for="teacher in teacherList" :key="teacher.id">
  <section class="i-teach-wrap">
    <div class="i-teach-pic">
      <a href="/teacher/1" :title="teacher.name">
        <img :alt="teacher.name" :src="teacher.avatar">
      </a>
    </div>
    <div class="mt10 hLh30 txtOf tac">
      <a href="/teacher/1" :title="teacher.name" class="fsize18 c-666">{{teacher.name}}</a>
    </div>
    <div class="hLh30 txtOf tac">
      <span class="fsize14 c-999">{{teacher.career}}</span>
    </div>
    <div class="mt15 i-q-txt">
      <p
        class="c-999 f-fA"
      >{{teacher.intro}}</p>
    </div>
  </section>
</li>
```

（5）效果展示

![image-20220309112531098](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202203091125850.png)

![image-20220309112554449](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202203091125808.png)

# 四、首页数据添加Redis缓存

### 1、Redis介绍

Redis是当前比较热门的NOSQL系统之一，它是一个开源的使用C语言编写的key-value存储系统（区别于MySQL的二维表格的形式存储）。和Memcache类似，但很大程度补偿了Memcache的不足。Memcache一样，Redis数据都是缓存在计算机内存中，不同的是，Memcache只能将数据缓存到内存中，无法自动定期写入硬盘，这就表示，一断电或重启，内存清空，数据丢失。所以Memcache的应用场景适用于缓存无需持久化的数据。而Redis不同的是它会周期性的把更新的数据写入磁盘或者把修改操作写入追加的记录文件，实现数据的持久化。

**Redis的特点：**

- Redis读取的速度是110000次/s，写的速度是81000次/s；
- 原子 。Redis的所有操作都是原子性的，同时Redis还支持对几个操作全并后的原子性执行。
- 支持多种数据结构：string（字符串）；list（列表）；hash（哈希），set（集合）；zset(有序集合)
- 持久化，集群部署
- 支持过期时间，支持事务，消息订阅
- 

**<font color=red>一般来说，把经常进行查询，不经常修改，不是特别重要的数据放在redis作为缓存。</font>**

### 2、项目集成Redis

（1）在common模块添加依赖

```xml
<!-- redis -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>

<!-- spring2.X集成redis所需common-pool2-->
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-pool2</artifactId>
    <version>2.6.0</version>
</dependency>
```

（2）在service_base模块添加redis配置文件

RedisConfig类

```java
@EnableCaching //开启缓存
@Configuration  //配置类
public class RedisConfig extends CachingConfigurerSupport {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setConnectionFactory(factory);
        //key序列化方式
        template.setKeySerializer(redisSerializer);
        //value序列化
        template.setValueSerializer(jackson2JsonRedisSerializer);
        //value hashmap序列化
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        return template;
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        //解决查询缓存转换异常的问题
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        // 配置序列化（解决乱码的问题）,过期时间600秒
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(600))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer))
                .disableCachingNullValues();
        RedisCacheManager cacheManager = RedisCacheManager.builder(factory)
                .cacheDefaults(config)
                .build();
        return cacheManager;
    }
}
```

### 3、在接口中添加redis缓存

由于首页数据变化不是很频繁，而且首页访问量相对较大，所以我们有必要把首页接口数据缓存到redis缓存中，减少数据库压力和提高访问速度。

改造`service-cms`模块首页banner接口，首页课程与讲师接口类似

**Spring Boot缓存注解说明**

（1）缓存`@Cacheable`

根据方法对其返回结果进行缓存，下次请求时，如果缓存存在，则直接读取缓存数据返回；如果缓存不存在，则执行方法，并把返回的结果存入缓存中。一般用在查询方法上。

查看源码，属性值如下：

| **属性/方法名** | **解释**                                         |
| --------------- | ------------------------------------------------ |
| value           | 缓存名，必填，它指定了你的缓存存放在哪块命名空间 |
| cacheNames      | 与 value 差不多，二选一即可                      |
| key             | 可选属性，可以使用 SpEL 标签自定义缓存的key      |

（2）缓存`@CachePut`

使用该注解标志的方法，每次都会执行，并将结果存入指定的缓存中。其他方法可以直接从响应的缓存中读取缓存数据，而不需要再去查询数据库。一般用在新增方法上。

查看源码，属性值如下：

| **属性/方法名** | **解释**                                         |
| --------------- | ------------------------------------------------ |
| value           | 缓存名，必填，它指定了你的缓存存放在哪块命名空间 |
| cacheNames      | 与 value 差不多，二选一即可                      |
| key             | 可选属性，可以使用 SpEL 标签自定义缓存的key      |

（3）缓存`@CacheEvict`

使用该注解标志的方法，会清空指定的缓存。一般用在更新或者删除方法上

查看源码，属性值如下：

| **属性/方法名**  | **解释**                                                     |
| ---------------- | ------------------------------------------------------------ |
| value            | 缓存名，必填，它指定了你的缓存存放在哪块命名空间             |
| cacheNames       | 与 value 差不多，二选一即可                                  |
| key              | 可选属性，可以使用 SpEL 标签自定义缓存的key                  |
| allEntries       | 是否清空所有缓存，默认为 false。如果指定为 true，则方法调用后将立即清空所有的缓存 |
| beforeInvocation | 是否在方法执行前就清空，默认为 false。如果指定为 true，则在方法执行前就会清空缓存 |

### 4、service_cms模块接口改造

（1）在service-cms模块配置文件添加redis配置

```properties
spring.redis.host=192.168.174.128
spring.redis.port=6379
spring.redis.database= 0
spring.redis.timeout=1800000

spring.redis.lettuce.pool.max-active=20
spring.redis.lettuce.pool.max-wait=-1
#最大阻塞等待时间(负数表示没限制)
spring.redis.lettuce.pool.max-idle=5
spring.redis.lettuce.pool.min-idle=0
```

（2）修改CrmBannerServiceImpl类中的方法，添加redis缓存注解

![image-20220309113707703](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202203091137202.png)

![image-20220309113844571](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202203091138111.png)

同样,也可以为首页名师模块、课程模块添加Redis缓存.(此处以名师模块为例)

![image-20220309114143905](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202203091141151.png)

![image-20220309114550231](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202203091145384.png)

（3）访问首页后 查看redis

![image-20220309113541181](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202203091135364.png)

**现象:**当第一次访问首页,查询数据库,把数据存入redis缓存.之后访问首页,数据直接从redis中获取. 当进行 banner/teacher/course的增删改操作时,清空对应的redis缓存,下次访问首页形成新的redis缓存.

# 五、单点登录与用户业务介绍

### 1、单一服务器模式

早期单一服务器，用户认证。

![img](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202203091151203.gif)

==使用session对象实现==:登录成功之后，把用户数据放到session里面；判断是否登录，从session获取数据，可以获取到登录。

缺点：单点性能压力大，无法扩展

### 2、 SSO(single sign on) 模式【也叫单点登录】

分布式，SSO(single sign on)模式

![img](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202203091154238.gif)

**优点 ：** 

- 用户身份信息独立管理，更好的分布式管理。

- 可以自己扩展安全策略

**缺点：**

- 认证服务器访问压力较大。

<font color=red>**单点登录三种常见方式**：</font>

- session广播机制实现 --- 简单说就是`session复制`。

- 使用cookie + redis 实现

  在项目中任何一个模块进行登录，登录之后，把数据放在两个地方
  （1）**redis**，在key：生成唯一随机值，在value：用户数据
  （2）**cookie**，把redis里面生成key值放到cookie里面

  访问项目中其他模块，发送请求带着cookie进行发送，获取cookie值，拿着cookie做事情；把cookie获取到的值，到redis进行查询，根据key进行查询，如果查询到数据就是已经登陆!

- 使用token实现(后文介绍)
  在项目某个模块进行登录，登录之后，按照规则生成字符串.把登录之后用户的信息包含到生成字符串里面，把token字符串返回.
  （1）可以把字符串通过cookie返回
  （2）把字符串通过地址栏返回
  再去访问项目其他模块，每次访问在地址栏带着生成的token字符串，在访问模块里面获取地址栏字符串，根据字符串获取用户信息。如果可以获取到，就是登陆

### 3、Token模式

token【令牌】是什么：`按照一定规则生成字符串，字符串可以包含用户信息。`

业务流程图{用户访问业务时，必须登录的流程}

![img](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202203091154543.gif)

**优点：**

- 无状态： token无状态，session有状态的

- 基于标准化: 你的API可以采用标准化的 JSON Web Token (JWT)

**缺点：**

- 占用带宽

- 无法在服务器端销毁

**注：**基于微服务开发，选择token的形式相对较多，因此我使用token作为用户认证的标准

### 4、三种单点登录方式的图解

![02 单点登录三种方式介绍](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202203091210248.png)

------

**<font color=skyblue size=5>如果有收获！！！ 希望老铁们来个三连，点赞、收藏、转发。
创作不易，别忘点个赞，可以让更多的人看到这篇文章，顺便鼓励我写出更好的博客</font>**