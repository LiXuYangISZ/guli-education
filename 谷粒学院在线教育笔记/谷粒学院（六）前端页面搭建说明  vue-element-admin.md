# 一、vue-element-admin

### 1、简介

vue-element-admin是基于element-ui 的一套后台管理系统集成方案。

功能：https://panjiachen.github.io/vue-element-admin-site/zh/guide/#功能

GitHub地址：https://github.com/PanJiaChen/vue-element-admin

项目在线预览：https://panjiachen.gitee.io/vue-element-admin

### 2、安装

1、下载模板框架压缩文件

这里我们使用171KB的 vue-admin-template-master.zip

![image-20220214152619162](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202202141526145.png)

2、将文件解压到工作区里面

3、通过vscode的终端打开解压文件夹，进行依赖安装

```bash
# 安装依赖
npm install
```

![image-20220214153303562](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202202141533938.png)

4、启动下载好依赖项目

![image-20220214153137419](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202202141531883.png)

# 二、前端页面环境说明

### 1、前端框架入口

![image-20220214153909170](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202202141539658.png)

### 2、前端页面框架模板技术介绍

==**vue-admin-template 模板 = vue + element-ui**==

### 3、目录结构介绍

- build目录：放项目构建的脚本文件
- config目录：全局配置
- node_modules目录：项目依赖模块
- src：项目源代码
- static：静态资源
- package.jspon：项目信息和依赖配置

```bash
config
├── index.js  // 修改useEslint:true，值修改为false
└── dev.env.js // 修改访问后端接口地址
```

```bash
src
├── api // 各种接口，定义调用方法
├── assets // 图片等资源 
├── components // 各种公共组件，非公共组件在各自view下维护 
├── icons // 图标 
├── router // 路由表 
├── store // 存储 
├── styles // 各种样式文件 
├── utils // 公共工具，非公共工具，在各自view下维护 
├── views // 各种layout，具体的页面，均以.vue结尾
├── App.vue //***项目顶层组件*** 
├── main.js //***项目入口文件***
└── permission.js //认证入口
```

# 三、项目的创建和基本配置

### 1、创建项目

将vue-admin-template-master重命名为guli-admin

### 2、修改项目信息

package.json

```json
{
    "name": "guli-admin",
    ......
    "description": "谷粒学院后台管理系统",
    "author": "DaLiZi <2422737092@qq.com>",
    ......
}
```

### 3、修改端口号

config/index.js中修改

```json
port: 9528
```

### 4、登录页修改

src/views/login/index.vue（登录组件）

```html
 //第4行
<h3 class="title">谷粒学院后台管理系统</h3>
...
//第28行
<el-button :loading="loading" type="primary" style="width:100%;" @click.native.prevent="handleLogin">
    登录
</el-button>
```

### 5、页面零星修改

1、标题

index.html（项目的html入口）

```html
<title>谷粒学院后台管理系统</title>
```

修改后热部署功能，浏览器自动刷新

2、国际化设置

打开 src/main.js（<font color=red>项目的js入口</font>），第7行，修改语言为 <font color=red>zh-CN</font>，使用中文语言环境，例如：日期时间组件

```js
import locale from 'element-ui/lib/locale/lang/zh-CN' // lang i18n
```

3、icon

复制 favicon.ico 到根目录

4、导航栏文字

src/views/layout/components（当前项目的布局组件）
src/views/layout/components/Navbar.vue

```vue
//13行
<el-dropdown-item>
    首页
</el-dropdown-item>
//17行
<span style="display:block;" @click="logout">退出</span>
```

5、面包屑文字

src/components（可以在很多项目中复用的通用组件）
src/components/Breadcrumb/index.vue

```js
meta: { title: '首页' } //38行
```

# 四、把系统登录功能改造本地（模拟登录）

### 1、修改系统登录默认地址

修改config/dev.env.js文件:

![image-20220214161731252](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202202172352316.png)

### 2、分析登录调用的两个方法

通过查看`/src/store/modules/user.js` 和 `/src/api/login.js`文件发现：

- login()  返回token. GET方式
- info()  返回roles、name、avatar头像。 POST方式

### 3、开发后端登录接口

```java
@Api(description = "管理员登录")
@RestController
@RequestMapping("/eduservice/user")
@CrossOrigin //解决跨域问题
public class EduLoginController {
    //login功能
    @ApiOperation("登录获取token")
    @PostMapping("login")
    public R login(){
        return R.ok().data("token", "admin");
    }

    //info
    @ApiOperation("获取管理员信息")
    @GetMapping("info")
    public R info(){
        return R.ok().data("roles", "[admin]").data("name", "zhangsan").data("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
```

### 4、修改/src/api/login.js中本地接口路径

![image-20220214163143574](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202202141631491.png)

### 5、进行测试

![image-20220214222924960](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202202142229203.png)

==跨域问题==：通过一个地址去访问另外一个地址，这个过程中如果有三个地址任何一个不一样

```tex
访问协议  http    https
ip地址  	192.18.1.1   172.11.11.11
端口号    9528     8001
```

解决方式:

- 在后端接口controller添加注解`@CrossOrigin`
- 使用SpringCloud GatWay解决(后面讲解)

# 五、框架使用过程

### 1、添加路由

![image-20220215150151883](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202202151501086.png)

![image-20220215150433511](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202202151504971.png)

### 2、点击某个路由，显示路由对应页面内容

![image-20220215150556341](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202202151505160.png)

![image-20220215150630005](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202202151506033.png)

### 3、在api文件夹创建js文件，定义接口地址和参数

![image-20220215150808391](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202202151508312.png)

### 4、在创建vue页面引入js文件，调用方法实现功能

![image-20220215151242880](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202202151512172.png)

```js
引入 import user from '.....'

data:{
},
created(){
},
methods:{
}
```

### 5、使用element-ui显示数据内容。

![image-20220215151411768](https://blog-photos-lxy.oss-cn-hangzhou.aliyuncs.com/img/202202151514776.png)