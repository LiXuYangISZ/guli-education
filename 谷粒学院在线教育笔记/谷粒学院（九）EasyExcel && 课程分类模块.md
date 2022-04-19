# 

# 一、EasyExcel 简介

### 1、Excel导入导出的应用场景

- 数据导入：减轻录入工作量
- 数据导出：统计信息归档
- 数据传输：异构系统之间数据传输

### 2、EasyExcel特点

Java领域解析、生成Excel比较有名的框架有Apache poi、jxl等。但他们都存在一个严重的问题就是非常的耗内存。如果你的系统并发量不大的话可能还行，但是一旦并发上来后一定会OOM或者JVM频繁的full gc。

EasyExcel是阿里巴巴开源的一个excel处理框架，以==使用简单、节省内存==著称。**EasyExcel能大大减少占用内存**的主要原因是在解析Excel时没有将文件数据一次性全部加载到内存中，而是**从磁盘上一行行读取数据，逐个解析**。

EasyExcel采用一行一行的解析模式，并将一行的解析结果以观察者的模式通知处理(<font color=red>AnalysisEventListener</font>)。

### 3、案例1:EasyExcel进行Excel写操作

<font color=blue>1、pom中引入xml相关依赖</font>

```xml
<dependencies>
    <!-- https://mvnrepository.com/artifact/com.alibaba/easyexcel -->
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>easyexcel</artifactId>
        <version>2.1.1</version>
    </dependency>
    <!-- 因为EasyExcel底层是Poi所以需要引入poi的依赖 -->
    <dependency>
        <groupId>org.apache.poi</groupId>
        <artifactId>poi</artifactId>
        <version>3.17</version>
    </dependency>

    <dependency>
        <groupId>org.apache.poi</groupId>
        <artifactId>poi-ooxml</artifactId>
        <version>3.17</version>
    </dependency>
</dependencies>
```

<font color=blue>2、创建实体类</font>

```java
@Data
public class WriteData {

    //设置excel表头名称
    @ExcelProperty("学生编号")
    private Integer sno;

    @ExcelProperty("学生姓名")
    private String sname;
}
```

<font color=blue>3、实现写操作</font>

```java
public static void testWrite(){
    //实现excel的写操作
    //1.设置写入文件夹地址和excel名称
    String filename = "F:\\write.xlsx";
    //2.调用easyexcel里面的方法实现写操作
    EasyExcel.write(filename, WriteData.class).sheet("学生列表").doWrite(getData());
}

public static List <WriteData> getData(){
    List <WriteData> list = new ArrayList <>();
    for(int i = 0;i < 10;i++){
        String sname = "lucy"+i;
        list.add(new WriteData(i, sname));
    }
    return list;
}
```

### 4、案例2:EasyExcel进行Excel读操作

1、创建实体类并标记对应列关系

```java
@Data
public class ReadData {
    //设置excel表头名称
    @ExcelProperty(index = 0)
    private Integer sno;

    @ExcelProperty(index = 1)
    private String sname;
}
```

2、创建监听器进行excel文件读取

```java
public class ExcelListner extends AnalysisEventListener<ReadData> {
   List<ReadData> list =  new ArrayList<ReadData>();

   //一行一行的去读取
    @Override
    public void invoke(ReadData readData, AnalysisContext analysisContext) {
        System.out.println("****"+readData);
        list.add(readData);
    }

    //读取Excel表头信息
    @Override
    public void invokeHeadMap(Map <Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头信息:"+headMap);
    }

    //读取完成之后执行
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
```

3、进行读操作

```java
public static void testRead(){
    String filename = "F:\\write.xlsx";
    EasyExcel.read(filename,ReadData.class,new ExcelListner()).sheet().doRead();
}
```



# 二、课程分类添加功能(后端)

核心:<font color=red>EasyExcel读取excel内容实现</font>

### 1、引入easyexcel依赖

### 2、使用代码生成器把课程分类代码生成

![image-20220221101800446](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202202211018520.png)

### 3、创建实体类和excel对应关系

```java
@Data
public class SubjectData {
    @ExcelProperty(index = 0)
    private String oneSujectName;

    @ExcelProperty(index = 1)
    private String twoSujectName;
}
```

### 4、编写 EduSubjectController 类

```java
@Api(description = "课程分类")
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

    //添加课程分类
    @ApiOperation(value = "添加课程分类")
    @PostMapping("addSubjects")
    public R addSubjects(MultipartFile file){
        boolean flag = eduSubjectService.saveSubjects(file);
        if(flag){
            return R.ok().message("文件导入成功!");
        }else{
            return R.error().message("文件导入失败!");
        }
    }
}
```

### 5、编写EduSubjectServiceImpl 类

```java
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Autowired
    private EduSubjectService eduSubjectService;

    //添加课程分类
    @Override
    public boolean saveSubjects(MultipartFile file) {

        try {
            InputStream inputStream = file.getInputStream();
            //进行excel文件读取
            EasyExcel.read(inputStream, SubjectData.class, new SubjectExcelListner(eduSubjectService)).sheet().doRead();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }
}
```

### 6、创建读取Excel监听器

```java
public class SubjectExcelListner extends AnalysisEventListener<SubjectData> {
    //因为SubjectExcelListner在EduSubjectServiceImpl每次会被new的形式使用,所以SubjectExcelListner不能交给
    //Spring进行管理,也就不能使用@Autowird或@Resource注解注入对象.
    //但是该类需要调用service中的方法进行数据库操作,该如何使用呢?
    //可以通过构造方法传递参数的形式,使用service对象.

    private EduSubjectService eduSubjectService;

    public SubjectExcelListner(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    public SubjectExcelListner() {}


    //一行一行的去读取
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if(subjectData==null){
            throw new GuLiException(20001,"数据为空!");
        }
        //添加一级分类
        EduSubject oneSubject = this.existOneSubject(subjectData.getOneSubjectName());
        if(oneSubject==null){//没有相同的一级分类
            oneSubject = new EduSubject();
            oneSubject.setTitle(subjectData.getOneSubjectName());
            oneSubject.setParentId("0");
            eduSubjectService.save(oneSubject);
        }

        //获取一级分类id
        String pid = oneSubject.getId();

        //添加二级分类
        EduSubject twoSubject = this.existTwoSubject(subjectData.getTwoSubjectName(), pid);
        if(twoSubject==null){
            twoSubject = new EduSubject();
            twoSubject.setTitle(subjectData.getTwoSubjectName());
            twoSubject.setParentId(pid);
            eduSubjectService.save(twoSubject);
        }

    }


    //读取Excel表头
    @Override
    public void invokeHeadMap(Map <Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头信息:"+headMap);
    }



    //读取完成后的操作
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    //判断一级分类是否重复
    private EduSubject existOneSubject(String name){
        QueryWrapper <EduSubject> wrapper = new QueryWrapper <>();
        wrapper.eq("title", name).eq("parent_id",0);
        EduSubject subject = eduSubjectService.getOne(wrapper);
        return subject;
    }

    //判断二级分类是否重复
    private EduSubject existTwoSubject(String name,String pid){
        QueryWrapper <EduSubject> wrapper = new QueryWrapper <>();
        wrapper.eq("title", name).eq("parent_id", pid);
        EduSubject subject = eduSubjectService.getOne(wrapper);
        return subject;
    }
}
```

### 7、重启oss服务，Swagger中测试文件上传

http://localhost:8001/swagger-ui.html

![image-20220221133317991](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202202211333684.png)

![image-20220221133349820](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202202211333955.png)

# 三、课程分类添加功能(前端)

### 1、添加课程分类路由

**/src/router/index.js:**

```javascript
{
        path: '/subject',
        component: Layout, //布局
        redirect: '/subject/table',
        name: 'SubjectSort',
        meta: { title: '课程分类管理', icon: 'nested' },
        children: [{
                path: 'list',
                name: 'EduSubjectList',
                component: () =>
                    import ('@/views/edu/subject/list'),
                meta: { title: '课程分类列表', icon: 'table' }
            },
            {
                path: 'import',
                name: 'EduSubjectImport',
                component: () =>
                    import ('@/views/edu/subject/import'),
                meta: { title: '导入课程分类', icon: 'tree' }
            }
        ]
    }
```

![2020101215390644](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202202212153980.png)

### 2、创建课程分类页面，修改路由对应的页面路径

![image-20220221215453270](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202202212154763.png)

### 3、在添加课程分类页面实现效果

**编写页面:**

```javascript
<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="信息描述">
        <el-tag type="info">excel模版说明</el-tag>
        <el-tag>
          <i class="el-icon-download"/>
          <a :href="OSS_PATH + '/excel/template.xlsx'">点击下载模版</a>
        </el-tag>

      </el-form-item>

      <el-form-item label="选择Excel">
        <el-upload
          ref="upload"
          :auto-upload="false"
          :on-success="fileUploadSuccess"
          :on-error="fileUploadError"
          :disabled="importBtnDisabled"
          :limit="1"
          :action="BASE_API+'/eduservice/subject/addSubjects'"
          name="file"
          accept="application/vnd.ms-excel">
          <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
          <el-button
            :loading="false"
            style="margin-left: 10px;"
            size="small"
            type="success"
            @click="submitUpload">{{ fileUploadBtnText }}</el-button>
        </el-upload>
      </el-form-item>
    </el-form>
  </div>
</template>
```

**编写js方法:**

```javascript
<script>
export default {

  data() {
    return {
      BASE_API: process.env.BASE_API, // 接口API地址
      OSS_PATH: process.env.OSS_PATH, // 阿里云OSS地址
      fileUploadBtnText: '上传到服务器', // 按钮文字
      importBtnDisabled: false, // 按钮是否禁用,
      loading: false
    }
  },
  created() {
    
  },
  methods: {
    //上传成功调用的方法
    fileUploadSuccess(response){
      if (response.success === true) {
        this.fileUploadBtnText = '导入成功'
        this.loading = false
        this.$message({
            type: 'success',
            message: response.message
        })
      } 
      //跳转到列表页面
      this.$router.push({path:'/subject/list'})
    },
    //上传失败调用的方法
    fileUploadError(response){
      if(response.success === false){
        this.fileUploadBtnText = '导入失败'
        this.loading = false
        this.$message({
            type: 'error',
            message: response.message
        })
      }
      //跳转到列表页面
      this.$router.push({path:'/subject/list'})
    },
    //点击按钮上传文件到接口
    submitUpload(){
      this.importBtnDisabled = true //上传按钮禁用
      this.loading = true
      this.$refs.upload.submit()
    }
  }
}
</script>
```

### 4、启动服务测试

![20201012155631568](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202202212158739.png)

# 四、课程分类列表(后端）

### 1、根据返回数据创建对应实体类

```java
//一级分类
@Data
public class OneSubject {
    private String id;
    private String title;

    //一个一级分类里面有多个二级分类
    private List<TwoSubject> children = new ArrayList<>();
}
```

```java
//二级分类
@Data
public class TwoSubject {
    private String id;
    private String title;
}
```

**返回数据格式为：**

![image-20220221220317125](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202202212203463.png)

### 2、编写Controller类

```java
@ApiOperation(value = "查询所有课程分类")
@GetMapping("getAllSubject")
public R getAllSubject(){
    List <OneSubject> oneSubjectList =  eduSubjectService.getAllSubject();
    return R.ok().data("list",oneSubjectList);
}
```

### 3、编写Service类

```java
//查询所有的课程分类==>树形
    @Override
    public List <OneSubject> getAllSubject() {
        //1.查询所有一级分类  parent_id=0
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<EduSubject>();
        wrapper.eq("parent_id",0);
        List <EduSubject> oneSubjectList = this.baseMapper.selectList(wrapper);

        //2.查询所有二级分类 parent_id!=0
        wrapper = new QueryWrapper<EduSubject>();
        wrapper.ne("parent_id",0);
        List <EduSubject> twoSubjectList = this.baseMapper.selectList(wrapper);

        //定义最终的返回类型
        List <OneSubject> finalSubjectList = new ArrayList <OneSubject>();

        ///3 封装一级分类
        //查询出来所有一级分类list集合集合，得到每一个一级分类对象，回去每一个一级分类对象值，
        //封装到要求的list集合里面  List<OneSubject> findSubjectList
        //List<EduSubject> ==> List <OneSubject> finalSubjectList
        for (EduSubject subject : oneSubjectList) {
            //将oneSubjectList封装到finalSubjectList
            OneSubject oneSubject = new OneSubject();
            //以下两种方法都可
            BeanUtils.copyProperties(subject,oneSubject);
            // oneSubject.setId(subject.getId());
            // oneSubject.setTitle(subject.getTitle());



            //在一级分类循环遍历查询所有的二级分类
            //创建list集合封装每一个一级分类的二级分类
            List <TwoSubject> children = new ArrayList <>();
            for (EduSubject eduSubject : twoSubjectList) {
                TwoSubject twoSubject = new TwoSubject();
                if (eduSubject.getParentId().equals(subject.getId())){
                    BeanUtils.copyProperties(eduSubject,twoSubject);
                    children.add(twoSubject);
                }
            }
            oneSubject.setChildren(children);

            finalSubjectList.add(oneSubject);//加入结果集  该语句放在76行之后也可以想想看为啥???

        }
        return finalSubjectList;
    }
```

### 4、使用swagger进行测试

......

# 五、课程分类列表(前端)

### 1、编写前端接口 

```javascript
import request from '@/utils/request'

export default {
    //查询所有的课程分类
    getAllSubject() {
        return request({
            url: `/eduservice/subject/getAllSubject`,
            method: 'get'
        })
    }
}
```

### 2、参考tree模块把前端整合出来

```javascript
<template>
  <div class="app-container">
    <el-input v-model="filterText" placeholder="Filter keyword" style="margin-bottom:30px;" />

    <el-tree
      ref="tree2"
      :data="data2"
      :props="defaultProps"
      :filter-node-method="filterNode"
      class="filter-tree"
      default-expand-all
    />

  </div>
</template>
```

### 3、前端接口调用

```javascript
<script>
import subject from '@/api/edu/subject' 
export default {


  data() {
    return {
      filterText: '',
      data2: [],
      defaultProps: {
        children: 'children',
        label: 'title'
      }
    }
  },
  watch: {
    filterText(val) {
      this.$refs.tree2.filter(val)
    }
  },

  created() {
    //页面加载完成后调用
    this.getSubjectList()
  },

  methods: {
    getSubjectList(){
        subject.getAllSubject().then(response=>{
          this.data2 = response.data.list;
        })
    },
    filterNode(value, data) {
      if (!value) return true
      return data.title.toLowerCase().indexOf(value) !== -1 //搜索不区分大小写
    }
  }
}
</script>
```

### 4、启动项目服务测试

![image-20220221221626643](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202202212216982.png)