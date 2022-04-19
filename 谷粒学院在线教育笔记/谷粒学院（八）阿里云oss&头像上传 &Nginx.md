## 一、阿里云oss存储服务

为了解决海量数据存储与弹性扩容，项目中我们采用云存储的解决方案---阿里云OSS。<font color=red>也可用于搭建免费图床哦(很香的)</font>

### 1、开通 “对象存储OSS”服务

（1）打开阿里云网站 https://www.aliyun.com/
（2）注册阿里云账户，最好使用支付宝，需要实名认证
（3）使用注册的用户登录阿里云里面
（4）找到阿里云oss

![image-20220221222342348](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202202212223562.png)

（5）立即开通

![image-20220221222501630](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202202212225515.png)

### 2、进入oss管理控制台

- 使用oss，首先创建bucket

![image-20220221223012120](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202202212230389.png)

![image-20220221223127072](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202202212231248.png)

- 控制台上传图片

![image-20220221223349298](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202202212233039.png)

### 3、Java代码操作阿里云oss上传文件

1、准备工作：创建操作阿里云oss许可证（阿里云颁发id和秘钥）

![image-20220221223628986](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202202212236146.png)

![image-20220221223704078](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202202212237461.png)

2、参考文档

https://help.aliyun.com/document_detail/32008.htm

![image-20220221223856366](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202202212238514.png)

![image-20220221223941065](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202202212239550.png)

![image-20220221223919083](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202202212239240.png)

3、具体使用

（1）创建Maven项目

（2）POM

```xml
<dependencies>
   <!--aliyunOSS-->
   <dependency>
       <groupId>com.aliyun.oss</groupId>
       <artifactId>aliyun-sdk-oss</artifactId>
       <version>2.8.3</version>
   </dependency>
   <dependency>
       <groupId>junit</groupId>
       <artifactId>junit</artifactId>
       <version>4.12</version>
   </dependency>
</dependencies>
```

（3）找到编码时需要用到的常量值

- endpoint
- bucketName
- accessKeyId
- accessKeySecret

（4）测试创建Bucket的连接

```java
public class OSSTest {
	// Endpoint以杭州为例，其它Region请按实际情况填写。
	String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
	// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
	String accessKeyId = "<yourAccessKeyId>";
	String accessKeySecret = "<yourAccessKeySecret>";
	String bucketName = "<yourBucketName>";
	
	// 创建OSSClient实例。
	OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
	
	// 创建存储空间。
	ossClient.createBucket(bucketName);
	
	// 关闭OSSClient。
	ossClient.shutdown();     
}	
```

（5）判断存储空间是否存在

```java
@Test
public void testExist() {
    // 创建OSSClient实例。
    OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    boolean exists = ossClient.doesBucketExist(bucketName);
    System.out.println(exists);
    // 关闭OSSClient。
    ossClient.shutdown();
}
```

6）设置存储空间的访问权限

```java
@Test
public void testAccessControl() {
    // 创建OSSClient实例。
    OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

    // 设置存储空间的访问权限为：公共读。
    ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
    // 关闭OSSClient。
    ossClient.shutdown();
}
```

## 二、后端集成OSS

### 1、在service模块下创建子模块`service_oss`

### 2、配置pom.xml

service已经引入service的公共依赖，所以service-oss模块只需引入阿里云oss相关依赖即可，service父模块已经引入了service-base模块，所以Swagger相关默认已经引入

```xml
<dependencies>
    <!-- 阿里云oss依赖 -->
    <dependency>
        <groupId>com.aliyun.oss</groupId>
        <artifactId>aliyun-sdk-oss</artifactId>
    </dependency>

    <!-- 日期工具栏依赖 -->
    <dependency>
        <groupId>joda-time</groupId>
        <artifactId>joda-time</artifactId>
    </dependency>
</dependencies>
```

### 3、配置application.yml

```yaml
#服务端口
server:
  port: 8002

#服务名
spring:
  application:
    name: service-oss

#环境设置:dev,test,prod
  profiles:
    active: dev

#阿里云OSS地址
aliyun:
  oss:
    file:
      endpoint: oss-cn-hangzhou.aliyuncs.com
      keyid: your accessKeyId
      keysecret: your accessKeySecret
      bucketname: guli-photos
```

### 4、编写主启动类

```java
@SpringBootApplication
@ComponentScan("com.rg")
public class OssApplication {
    public static void main(String[] args) {
        SpringApplication.run(OssApplication.class, args);
    }
}
```

### 5、运行测试--->报错

![51aca4b820c44b83a5b775114da42256](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202202220923602.png)

**[解决方式](https://blog.csdn.net/LXYDSF/article/details/123018411)**：

（1）添加上数据库配置
（2）在启动类添加属性，默认不去加载数据库配置【推荐使用】

```java
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
```

## 三、实现头像上传功能(后端)

### 1、创建属性工具类，读取配置文件内容

```java
@Component //把属性的设置交给Spring.
public class ConstantPropertiesUtil implements InitializingBean {// InitializingBean:在初始化的时候,该类被执行.

    //从配置文件中读取值,赋值给这些属性
    //注意@value无法给静态属性注入值
    @Value("${aliyun.oss.file.endpoint}")
    private  String endpoint;

    @Value("${aliyun.oss.file.keyid}")
    private String keyId;

    @Value("${aliyun.oss.file.keysecret}")
    private String keySecret;

    @Value("${aliyun.oss.file.bucketname}")
    private String bucketName;

    //定义公开静态方法
    public static String END_POINT;
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String BUCKET_NAME;

    //当属性值设置完毕后执行该方法.
    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT = endpoint;
        ACCESS_KEY_ID = keyId;
        ACCESS_KEY_SECRET = keySecret;
        BUCKET_NAME = bucketName;
    }
}
```

### 2、编写OssController

```java
@Api(description="阿里云文件管理")
@CrossOrigin //跨域
@RestController
@RequestMapping("/ossService/file")
public class OssController {

    @Autowired
    private OssService ossService;

    @ApiOperation("上传头像文件")
    @PostMapping("upload")
    public R uploadFile(@ApiParam(name = "file", value = "文件", required = true)
                                    MultipartFile file){
        //返回上传文件的Url路径
        String url = ossService.uploadFile(file);
        return R.ok().message("文件上传成功!").data("url",url);
    }
}
```

### 3、编写OssService

参考SDK中的：==Java ->上传文件 -> 简单上传 -> 流式上传 -> 上传文件流==

```java
public interface OssService {
    String uploadFile(MultipartFile file);
}
```

```java
@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadFile(MultipartFile file) {
        //工具类获取值
        String endPoint = ConstantPropertiesUtil.END_POINT;
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
        //获取文件名称
        String filename = file.getOriginalFilename();

        //由于阿里云OSS对于相同文件名称会进行覆盖,所以我们要对文件名称进行处理:前面加上一个随机数
        //1.在文件名称里面添加随机唯一的值
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        filename = uuid+filename;
        //2.把文件按照日期进行分类
        //2022/2/20/a.jpg
        String datePath = new DateTime().toString("yyyy/MM/dd");
        filename = datePath+ "/" +filename;

        try {
            InputStream inputStream = file.getInputStream();
            // 发送PutObject请求,进行文件上传
            //bucketName: Bucket名称
            //filename: 上传到OSS的文件路径和文件名称 如 /aa/bb/1.jpg
            //inputStream: 文件输入流
            ossClient.putObject(bucketName, filename, inputStream);

            //https://guli-photos.oss-cn-hangzhou.aliyuncs.com/Snipaste_2022-02-10_23-34-41.jpg
            String url = "https://"+bucketName+"."+endPoint+"/"+filename;
            return url;//上传成功,返回url
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            //关闭OSSClient
            ossClient.shutdown();
        }
    }
}
```

### 4、重启oss服务，Swagger中测试文件上传

http://localhost:8002/swagger-ui.html

## 四、Nginx 反向代理服务器

### 1、主要功能

- 请求转发

![image-20220220204753031](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202202202047596.png)

- 负载均衡

![image-20220220204820882](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202202202048232.png)

- 动静分离

tomcat存放动态资源，nginx存放静态资源。

### 2、安装window版的nginx

下载官网：http://nginx.org/en/download.html

将nginx-1.18.0.zip解压到开发目录中,如：E:\soft\nginx-1.18.0
**使用cmd启动nginx** ，运行`nginx.exe`

![image-20220220205427296](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202202202054304.png)

关闭窗口,查看后台进程

![image-20220220205605420](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202202202056618.png)

特点：==使用cmd启动nginx，如果关闭cmd窗口，nginx不会停止的==。

需要使用`nginx.exe -s stop` 进行手动关闭，重启命令`nginx -s reload`

### 3、配置nginx实现请求转发的功能

1、找到nginx配置文件E:\soft\nginx-1.18.0\conf\nginx.conf`

2、在nginx.conf进行配置

- 修改nginx默认端口，把 80 修改 81(避免冲突)

![image-20220220205947192](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202202202059677.png)

- 在http中添加转发规则

```shell
server {
        listen       9001;
        server_name  localhost;

        location ~ /eduservice/ {
            proxy_pass http://localhost:8001;
        }

        location ~ /ossService/ {
           proxy_pass http://localhost:8002;
        }
        #后面每添加一个服务,就配置一个转发
    }
```

3、修改前端请求地址

（1）修改config/dev.env.js

```javascript
BASE_API: '"http://localhost:9001"',
```

（2）重启前端程序

修改配置文件后，需要手动重启前端程序

![image-20220220210357552](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202202202103600.png)

**补充:**通过后台观察,<font color=red>为啥都是两次请求呢?</font>

![image-20220220211022920](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202202202110456.png)



## 五、添加讲师实现头像上传功能(前端)

### 1、在添加讲师页面，创建上传组件，实现上传。

到源代码里面找到组件，复制到前端项目`/src/components`里面

![image-20220220215821658](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202202202158647.png)

### 2、添加讲师页面使用该上传组件

src/views/edu/teacher/add.vue

```javascript
<!-- 讲师头像 -->
<el-form-item label="讲师头像">

    <!-- 头衔缩略图 -->
    <pan-thumb :image="teacher.avatar"/>
    <!-- 文件上传按钮 -->
    <el-button type="primary" icon="el-icon-upload" @click="imagecropperShow=true">更换头像
    </el-button>

    <!--
        v-show：是否显示上传组件
        :key：类似于id，如果一个页面多个图片上传控件，可以做区分
        :url：后台上传的url地址
        @close：关闭上传组件
        @crop-upload-success：上传成功后的回调 
          <input type="file" name="file"/>
    -->
    <image-cropper
                  v-show="imagecropperShow"
                  :width="300"
                  :height="300"
                  :key="imagecropperKey"
                  :url="BASE_API+'/eduoss/fileoss'"
                  field="file"
                  @close="close"
                  @crop-upload-success="cropSuccess"/>
</el-form-item>
```

### 3、js脚本实现上传和图片回显

```javascript
<script>
import teacher from '@/api/edu/teacher'
import ImageCropper from '@/components/ImageCropper'
import PanThumb from '@/components/PanThumb'

export default {
  components: { ImageCropper, PanThumb },
  data() {
    return {
      teacher:{},//这里边写不写属性都可,因为程序会自动把页面绑定的属性添加进去
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
        this.teacher = {}
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
```

### 4、设置默认头像

config/dev.env.js中添加阿里云oss bucket地址

```javascript
OSS_PATH: '"https://guli-photos.oss-cn-hangzhou.aliyuncs.com"'
```

组件中初始化头像默认地址

![image-20220222092003976](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202202220921638.png)

### 5、启动测试即可

![image-20220222092200928](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202202220922283.png)

点击**更换头像**:

![image-20220222092239893](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202202220922080.png)