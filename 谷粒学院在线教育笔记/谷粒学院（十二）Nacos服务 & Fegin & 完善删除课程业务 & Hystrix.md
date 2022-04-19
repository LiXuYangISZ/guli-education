[TOC]

------



# 一、微服务与springcloud

### 1、什么是微服务

（1）微服务是架构风格
（2）把一个项目拆分成独立的多个服务，多个服务是独立运行，每个服务占用独立进程

**我们的项目就是一个微服务项目**

![image-20220309075517797](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202203090755038.png)



### 2、springcloud说明

（1）springcloud并不是一种技术，是很多技术总成，很多框架集合

（2）springcloud里面有很多框架（技术），使用springcloud里面这些框架实现微服务操作

（3）使用springcloud，需要依赖springboot技术

### 3、Spring Cloud相关基础服务组件

- 服务发现——Netflix Eureka （**Nacos**）
- 服务调用——Netflix Feign
- 熔断器——Netflix Hystrix
- 服务网关——Spring Cloud GateWay
- 分布式配置——Spring Cloud Config （**Nacos**）
- 消息总线 —— Spring Cloud Bus （**Nacos**）

### 4、Spring Cloud的版本

Spring Cloud并没有熟悉的数字版本号，而是对应一个开发代号。
![image-20220309075737367](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202203090757578.png)

### 5、Spring Cloud 小版本类表

- SNAPSHOT： 快照版本，随时可能修改

- M： MileStone，M1表示第1个里程碑版本，一般同时标注PRE，表示预览版版。

- SR： Service Release，SR1表示第1个正式版本，一般同时标注GA：(GenerallyAvailable),表示稳定版本。

# 二、服务发现 – 搭建Nacos服务

### 1、Nacos基本概念

（1）Nacos 是阿里巴巴推出来的一个新开源项目，是一个更易于构建云原生应用的动态服务发现、配置管理和服务管理平台。Nacos 致力于帮助您发现、配置和管理微服务。Nacos 提供了一组简单易用的特性集，帮助您快速实现动态服务发现、服务配置、服务元数据及流量管理。Nacos 帮助您更敏捷和容易地构建、交付和管理微服务平台。 Nacos 是构建以“服务”为中心的现代应用架构 (例如微服务范式、云原生范式) 的服务基础设施。

（2）[常见的注册中心：](https://blog.csdn.net/LXYDSF/article/details/122246316)

- Eureka（原生，2.0遇到性能瓶颈，停止维护）

- Zookeeper（支持，专业的独立产品。例如：dubbo）

- Consul（原生，GO语言开发）

- `Nacos`

相对于 Spring Cloud Eureka 来说，Nacos 更强大。==Nacos = Spring Cloud Eureka + Spring Cloud Config==

Nacos 可以与 Spring, Spring Boot, Spring Cloud 集成，并能代替 Spring Cloud Eureka, Spring Cloud Config.

通过 Nacos Server 和 spring-cloud-starter-alibaba-nacos-discovery 实现服务的注册与发现。

（3）Nacos是**以服务为主要服务对象**的中间件，Nacos支持所有主流的服务发现、配置和管理。

Nacos主要提供的大功能：

- 服务发现和服务健康监测

-  动态配置服务

- 动态DNS服务

- 服务及其元数据管理

（4）Nacos结构图

![img](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202203090823666.jpg)

### 2、Nacos下载和安装

（1）下载地址和版本
下载地址：https://github.com/alibaba/nacos/releases

下载版本：nacos-server-1.1.4.tar.gz 或 nacos-server-1.1.4.zip，解压任意目录即可

（2）启动nacos服务

- Linux/Unix/Mac 启动命令

  启动命令：`sh startup.sh -m standalone`

- Windows 启动命令：

  cmd startup.cmd 或者双击startup.cmd运行文件。

访问：http://localhost:8848/nacos  用户名密码：nacos/nacos
![image-20220309082723892](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202203090827784.png)

![image-20220309082807965](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202203090828306.png)



# 三、服务注册（service_edu为例）

`把service-edu微服务注册到注册中心中，service-vod步骤相同`

### 1、在service模块配置pom

```xml
<!--服务注册-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
</dependency>
```

### 2、添加服务配置信息

配置application.properties，在客户端微服务中添加注册Nacos服务的配置信息

```properties
# nacos服务地址
spring.cloud.nacos.discovery.server-addr=192.168.174.128:8848
```

### 3、添加Nacos客户端注解

在客户端微服务启动类中添加注解`@EnableDiscoveryClient`

### 4、启动客户端微服务

启动已注册的微服务，可以在Nacos服务列表中看到被注册的微服务

![image-20220309083659646](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202203090837075.png)



# 四、服务调用 – [Fegin](https://blog.csdn.net/LXYDSF/article/details/122395510#6_OpenFeign_232)

### 1、Feign基本概念

- Feign是Netflix开发的声明式、模板化的HTTP客户端， Feign可以帮助我们更快捷、优雅地调用HTTP API。
- Feign支持多种注解，例如Feign自带的注解或者JAX-RS注解等。
- Spring Cloud对Feign进行了增强，使`Feign支持了Spring MVC注解`，并整合了Ribbon和Eureka，从而让Feign的使用更加方便。
- Spring Cloud Feign是基于`Netflix feign`实现，`整合了Spring Cloud Ribbon和Spring Cloud Hystrix`，除了提供这两者的强大功能外，还提供了一种声明式的Web服务客户端定义的方式。
- Spring Cloud Feign帮助我们定义和实现依赖服务接口的定义。在Spring Cloud feign的实现下，只需要创建一个接口并用注解方式配置它，即可完成服务提供方的接口绑定，简化了在使用Spring Cloud Ribbon时自行封装服务调用客户端的开发量。

### 2、实现服务调用

1、需求---删除课时的同时删除云端视频

2、在service模块添加pom依赖

```xml
<!--服务调用-->
<dependency>
     <groupId>org.springframework.cloud</groupId>
     <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

3、在调用端的启动类添加注解 `@EnableFeignClients`

4、创建包和接口---创建client包

- @FeignClient注解用于指定从哪个服务中调用功能 ，名称与被调用的服务名保持一致。
- @GetMapping注解用于对被调用的微服务进行地址映射
- `@PathVariable注解一定要指定参数名称，否则出错`
- @Component注解防止，在其他位置注入VodClient时idea报错

```java
@FeignClient(value = "service-vod",fallback = VodFileDegradeFeignClient.class)
@Component
public interface VodClient {

    //定义调用方法的路径,一定要写全
    //PathVariable注解一定要指定参数名称,否则出错.
    //根据视频id删除阿里云视频
    @DeleteMapping("/vodService/video/removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable("id") String id);

    @DeleteMapping("/vodService/video/removeMoreAlyVideo")
    public R removeMoreAlyVideo(@RequestParam("videoList") List<String> videoIdList);
}
```

<font color=red>**注意：这里的访问路径是全路径**</font>

5、调用微服务---在调用端中调用client中的方法

**controller**

```java
//删除小节(删除小节时候，同时要把里面的视频删除)
@ApiOperation("删除小节")
@DeleteMapping("delVideo/{id}")
public R delVideo(@PathVariable String id){
    videoService.delVideo(id);
    return R.ok();
}
```

**service**

```java
//删除小节
@Override
public void delVideo(String id) {
    //先删除视频
    EduVideo video = this.baseMapper.selectById(id);
    String videoSourceId = video.getVideoSourceId();
    if(!StringUtils.isEmpty(videoSourceId)){
        client.removeAlyVideo(videoSourceId);
    }
    //删除小节
    this.baseMapper.deleteById(id);
}
```

6、测试

# 五、完善删除课程业务

**需求：----删除课程的同时删除云端视频**

### 1、在service-vod模块的Controller中 创建接口，删除多个视频

```java
//删除多个阿里云视频的方法
//参数为多个视频id
@DeleteMapping("removeMoreAlyVideo")
public R removeMoreAlyVideo(@RequestParam("videoList") List<String> videoIdList){
    vodService.removeMoreAlyVideo(videoIdList);
    return R.ok();
}
```

### 2、编写service 方法

```java
//删除多个阿里云视频
@Override
public void removeMoreAlyVideo(List videoIdList) {
    try {
        DefaultAcsClient client = InitVodClient.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
        DeleteVideoRequest request = new DeleteVideoRequest();
        DeleteVideoResponse response = new DeleteVideoResponse();
        String videoIds = StringUtils.join(videoIdList.toArray(),",");
        //想request中设置视频id
        request.setVideoIds(videoIds);
        response = client.getAcsResponse(request);
    } catch (ClientException e) {
        e.printStackTrace();
        throw new GuLiException(20001, "视频删除失败!");
    }

}
```

### 3、在service-edu中调用 service-vod 接口实现删除多个视频的功能

**VodClient 类**

```java
@DeleteMapping("/vodService/video/removeMoreAlyVideo")
public R removeMoreAlyVideo(@RequestParam("videoList") List<String> videoIdList);
```

**EduCourseController 类**

```java
//删除课程
@DeleteMapping("removeCourse/{id}")
@CacheEvict(value = "course", allEntries=true)
public R removeCourse(@PathVariable String id){
    courseService.removeCourse(id);
    return R.ok();
}
```

**EduCourseServiceImpl类**

```java
//根据id删除课程信息
@Override
public void removeCourse(String id) {
    //1.删除小节
    videoService.removeVideoByCourseId(id);
    //2.删除章节
    chapterService.removeChapterByCourseId(id);
    //3.删除课程描述信息
    courseDescriptionService.removeById(id);
    //4.删除课程信息
    this.baseMapper.deleteById(id);

}
```

**EduVideoServiceImpl类**

```java
//通过课程id删除所有小节
@Override
public void removeVideoByCourseId(String id) {
    //1.删除小节中所有的视频
    //获取所有的id集合
    QueryWrapper <EduVideo> wrapperVideo = new QueryWrapper <>();
    wrapperVideo.eq("course_id", id);
    wrapperVideo.select("video_source_id");
    List <EduVideo> videoList = this.baseMapper.selectList(wrapperVideo);
    List <String> idList = new ArrayList <>();
    for (EduVideo video : videoList) {
        if(!StringUtils.isEmpty(video.getVideoSourceId())){
            idList.add(video.getVideoSourceId());
        }
    }

    //进行删除
    if(idList.size()>0){
        client.removeMoreAlyVideo(idList);
    }

    //根据删除课程对应的小节
    QueryWrapper <EduVideo> wrapper = new QueryWrapper <>();
    wrapper.eq("course_id",id);
    this.baseMapper.delete(wrapper);
}
```

**EduChapterServiceImpl类**

```java
@Override
public void removeChapterByCourseId(String id) {
    QueryWrapper <EduChapter> wrapper = new QueryWrapper <>();
    wrapper.eq("course_id",id);
    this.baseMapper.delete(wrapper);
}
```

### 4、启动服务测试删除功能



# 六、熔断器 – [Hystrix](https://blog.csdn.net/LXYDSF/article/details/122395669)

### 1、Spring Cloud调用接口过程

Spring Cloud 在接口调用上，大致会经过如下几个组件配合：

Feign —>Hystrix —>Ribbon —>Http Client（apache http components 或者 Okhttp） 具体交互流程上，如下图所示：

![img](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202203090911691.jpg)

**（1）接口化请求调用**当调用被`@FeignClient`注解修饰的接口时，在框架内部，将请求转换成Feign的请求实例`feign.Request`，交由Feign框架处理。

**（2）Feign** ：转化请求Feign是一个http请求调用的轻量级框架，可以以Java接口注解的方式调用Http请求，封装了Http调用流程。

**（3）Hystrix**：熔断处理机制 Feign的调用关系，会被Hystrix代理拦截，对每一个Feign调用请求，Hystrix都会将其包装成`HystrixCommand`,参与Hystrix的流控和熔断规则。如果请求判断需要熔断，则Hystrix直接熔断，抛出异常或者使用`FallbackFactory`返回熔断`Fallback`结果；如果通过，则将调用请求传递给`Ribbon`组件。

**（4）Ribbon**：服务地址选择 当请求传递到`Ribbon`之后,`Ribbon`会根据自身维护的服务列表，根据服务的服务质量，如平均响应时间，Load等，结合特定的规则，从列表中挑选合适的服务实例，选择好机器之后，然后将机器实例的信息请求传递给`Http Client`客户端，`HttpClient`客户端来执行真正的Http接口调用；

**（5）HttpClient** ：Http客户端，真正执行Http调用根据上层`Ribbon`传递过来的请求，已经指定了服务地址，则HttpClient开始执行真正的Http请求



### **2**、Hystrix概念

Hystrix 是一个供分布式系统使用，提供延迟和容错功能，保证复杂的分布系统在面临不可避免的失败时，仍能有其弹性。

比如系统中有很多服务，当某些服务不稳定的时候，使用这些服务的用户线程将会阻塞，如果没有隔离机制，系统随时就有可能会挂掉，从而带来很大的风险。SpringCloud使用Hystrix组件提供断路器、资源隔离与自我修复功能。下图表示服务B触发了断路器，阻止了级联失败.

![img](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202203090913388.jpg)

# 七、feign结合Hystrix使用

改造service-edu模块

### 1、在service的pom中添加依赖

```xml
<dependency>
 <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
</dependency>

<!--hystrix依赖，主要是用 @HystrixCommand -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
</dependency>

<!--服务注册-->
<dependency>
     <groupId>org.springframework.cloud</groupId>
     <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
 </dependency>
<!--服务调用-->
<dependency>
     <groupId>org.springframework.cloud</groupId>
     <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

### 2、在配置文件中添加hystrix配置

```properties
#开启熔断机制
feign.hystrix.enabled=true
# 设置hystrix超时时间，默认1000ms
#hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds=6000
```

### 3、在service-edu的client包里面创建熔断器的实现类

```java
@Component
public class VodFileDegradeFeignClient implements VodClient{
    @Override
    public R removeAlyVideo(String id) {
        return R.error().message("删除单个视频出错了");
    }

    @Override
    public R removeMoreAlyVideo(List <String> videoIdList) {
        return R.error().message("删除多个视频出错了");
    }
}
```

### 4、修改VodClient接口的注解

![image-20220309091759259](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202203090918327.png)

### 5、修改EduVideoController 类

![image-20220309091844386](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202203090918517.png)

### 6、使用debug测试熔断器效果

![image-20220228171115114](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202202281711007.png)



------

**<font color=skyblue size=5>如果有收获！！！ 希望老铁们来个三连，点赞、收藏、转发。
创作不易，别忘点个赞，可以让更多的人看到这篇文章，顺便鼓励我写出更好的博客</font>**