<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- /课程列表 开始 -->
    <section class="container">
      <header class="comm-title">
        <h2 class="fl tac">
          <span class="c-333">全部课程</span>
        </h2>
      </header>
      <section class="c-sort-box">
        <section class="c-s-dl">
          <dl>
            <dt>
              <span class="c-999 fsize14">课程类别</span>
            </dt>
            <dd class="c-s-dl-li">
              <ul class="clearfix">
                <li>
                  <a title="全部" href="#" @click="searchAll(-1)" :class="{active:oneIndex==-1}">全部</a>
                </li>
                <li v-for="(item,index) in subjectNestedList" :key="item.id" @click="searchOne(item.id,index)" :class="{active:oneIndex==index}">
                  <a :title="item.title" href="#">{{item.title}}</a>
                </li>
               
              </ul>
            </dd>
          </dl>
          <dl>
            <dt>
              <span class="c-999 fsize14"></span>
            </dt>
            <dd class="c-s-dl-li">
              <ul class="clearfix">
                <li v-for="(item,index) in subSubjectList" :key="index" @click="searchTwo(item.id,index)"
                :class="{active2:twoIndex==index}">
                  <a :title="item.title" href="#" >{{item.title}}</a>
                </li>
              </ul>
            </dd>
          </dl>
          <div class="clear"></div>
        </section>
        <div class="js-wrap">
          <section class="fr">
            <span class="c-ccc">
              <i class="c-master f-fM">1</i>/
              <i class="c-666 f-fM">1</i>
            </span>
          </section>
          <section class="fl">
            <ol class="js-tap clearfix">
              <li :class="{'current bg-orange':buyCountSort!=''}">
                <a title="销量" href="javascript:void(0);" @click="searchBuyCount()">销量
                <span :class="{hide:buyCountSort=='1'}" v-if="buyCountSort!=''">↑</span>
                <span :class="{hide:buyCountSort=='2'}" v-if="buyCountSort!=''">↓</span>
                </a>
              </li>
              <li :class="{'current bg-orange':gmtCreateSort!=''}">
                <a title="最新" href="javascript:void(0);" @click="searchGmtCreate()">最新
                <span :class="{hide:gmtCreateSort=='1'}" v-if="gmtCreateSort!=''">↑</span>
                <span :class="{hide:gmtCreateSort=='2'}" v-if="gmtCreateSort!=''">↓</span>
                
                </a>
              </li>
              <li :class="{'current bg-orange':priceSort!=''}">
                <a title="价格" href="javascript:void(0);" @click="searchPrice()">价格&nbsp;
                  <span :class="{hide:priceSort=='1'}" v-if="priceSort!=''">↑</span>
                  <span :class="{hide:priceSort=='2'}" v-if="priceSort!=''">↓</span>
                </a>
              </li>
            </ol>
          </section>
        </div>
        <div class="mt40">
          <!-- /无数据提示 开始-->
          <section class="no-data-wrap" v-if="data.total == 0">
            <em class="icon30 no-data-ico">&nbsp;</em>
            <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span>
          </section>
          <!-- /无数据提示 结束-->
          <article class="comm-course-list" v-if="data.total > 0">
            <ul class="of" id="bna">
              <li v-for="item in data.items" :key="item.id">
                <div class="cc-l-wrap">
                  <section class="course-img">
                    <img :src="item.cover" class="img-responsive" :alt="item.title">
                    <div class="cc-mask">
                      <a :href="'/course/'+item.id" title="开始学习" class="comm-btn c-btn-1">开始学习</a>
                    </div>
                  </section>
                  <h3 class="hLh30 txtOf mt10">
                    <a :href="'/course/'+item.id" :title="item.title" class="course-title fsize18 c-333">{{item.title}}</a>
                  </h3>
                  <section class="mt10 hLh20 of">

                     <span class="fr jgTag bg-green" v-if="Number(item.price) === 0">
                          <i class="c-fff fsize12 f-fA">免费</i>
                        </span>
                        <span class="fr jgTag bg-green" v-else>
                           <i class="c-fff fsize12 f-fA"> ￥{{item.price}}</i>
                        </span>

                   
                    <span class="fl jgAttr c-ccc f-fA">
                      <i class="c-999 f-fA">{{item.buyCount}}人学习</i>
                      |
                      <i class="c-999 f-fA">9634评论</i>
                    </span>
                  </section>
                </div>
              </li>
              
            </ul>
            <div class="clear"></div>
          </article>
        </div>
        <!-- 公共分页 开始 -->
        <div>
          <div class="paging">
            <!-- undisable这个class是否存在，取决于数据属性hasPrevious -->
            <a
              :class="{undisable: !data.hasPrevious}"
              href="#"
              title="首页"
              @click.prevent="gotoPage(1)">首</a>
            <a
              :class="{undisable: !data.hasPrevious}"
              href="#"
              title="前一页"
              @click.prevent="gotoPage(data.current-1)">&lt;</a>
            <a
              v-for="page in data.pages"
              :key="page"
              :class="{current: data.current == page, undisable: data.current == page}"
              :title="'第'+page+'页'"
              href="#"
              @click.prevent="gotoPage(page)">{{ page }}</a>
            <a
              :class="{undisable: !data.hasNext}"
              href="#"
              title="后一页"
              @click.prevent="gotoPage(data.current+1)">&gt;</a>
            <a
              :class="{undisable: !data.hasNext}"
              href="#"
              title="末页"
              @click.prevent="gotoPage(data.pages)">末</a>
            <div class="clear"/>
          </div>
        </div>
        <!-- 公共分页 结束 -->
      </section>
    </section>
    <!-- /课程列表 结束 -->
  </div>
</template>
<script>
import courseApi from '@/api/course'
export default {
  data() {
    return {
      page:1,
      data:{},
      subjectNestedList: [], // 一级分类列表
      subSubjectList: [], // 二级分类列表
      searchObj: {}, // 查询表单对象
      oneIndex:-1,
      twoIndex:-1,
      buyCountSort:"",  //1是降序 2是升序  不选则不排序
      gmtCreateSort:"",
      priceSort:""
    }
  },
  created() {
    this.initCourseList()
    this.initSubject()
  },
  methods: {
    //1.查询第一页数据
    initCourseList(){
      courseApi.getCourseList(1,8,this.searchObj).then(response=>{
        this.data = response.data.data
      })
    },
    //2.查询所有一级分类 和所有二级分类
    initSubject(){
      courseApi.getAllSubject().then(response=>{
        this.subjectNestedList = response.data.data.list
        this.subSubjectList = []//先清空所有的二级分类
        // this.subSubjectList=this.subjectNestedList[0].children

        for(var i = 0;i < this.subjectNestedList.length; i++){
          this.subSubjectList.push(...this.subjectNestedList[i].children)
        }
      })
    },
    //3.分页跳转
    gotoPage(page){
      courseApi.getCourseList(page,8,this.searchObj).then(response=>{
        this.data = response.data.data
      })
    },
    //4.单击一级分类,显示二级分类
    searchOne(subjectParentId,index){
      this.oneIndex = index
      //相关值的初始化
      this.twoIndex = -1
      this.searchObj.subjectId = ""
      this.subSubjectList = []//清空二级分类
      //把一级分类点击id,赋值给searchObj
      this.searchObj.subjectParentId = subjectParentId
      //点击单个一级分类进行条件查询
      this.gotoPage(1)
      for(var i = 0;i < this.subjectNestedList.length; i++){
        var oneSubject = this.subjectNestedList[i]
        if(subjectParentId == oneSubject.id){
          //从一级分类里面获取对应的二级分类
          this.subSubjectList = oneSubject.children
        }
      }
    },
    //5.单击二级分类,进行查询
    searchTwo(subjectId,index){
      this.twoIndex = index
      // this.searchObj.subjectParentId = ""//一级分类搜索清空 这里不需要情况,因为
      this.searchObj.subjectId = subjectId
      this.gotoPage(1)
    },
    //6.点击全部分类,查询全部
    searchAll(index){
      this.oneIndex  = index
      this.twoIndex = -1
      //进行查询全部
      this.searchObj = {}
      this.gotoPage(1)
      this.subSubjectList  = []//清空二级,填充所有二级分类
      for(var i = 0;i < this.subjectNestedList.length; i++){
          this.subSubjectList.push(...this.subjectNestedList[i].children)
        }
    },
    //7.根据销量排序
    searchBuyCount(){
      //设置对应变量值,为了样式生效
      if(this.buyCountSort==""){
        this.buyCountSort="1"
      }else if(this.buyCountSort=="1"){
        this.buyCountSort="2"
      }else if(this.buyCountSort=="2"){
        this.buyCountSort = "1"
      }
      console.log("this.buyCountSort:"+this.buyCountSort);
      this.gmtCreateSort = ""
      this.priceSort = ""
      //把值赋值到searchObj
      this.searchObj.buyCountSort = this.buyCountSort
      this.searchObj.gmtCreateSort = this.gmtCreateSort
      this.searchObj.priceSort = this.priceSort
      //调用方法 进行搜索
      this.gotoPage(1)
    },
    searchGmtCreate(){
      //设置对应变量值,为了样式生效
      if(this.gmtCreateSort==""){
        this.gmtCreateSort="1"
      }else if(this.gmtCreateSort=="1"){
        this.gmtCreateSort="2"
      }else if(this.gmtCreateSort=="2"){
        this.gmtCreateSort = "1"
      }
      console.log("this.gmtCreateSort:"+this.gmtCreateSort);
      this.buyCountSort = ""
      this.priceSort = ""
      //把值赋值到searchObj
      this.searchObj.buyCountSort = this.buyCountSort
      this.searchObj.gmtCreateSort = this.gmtCreateSort
      this.searchObj.priceSort = this.priceSort
      //调用方法 进行搜索
      this.gotoPage(1)
    },
    searchPrice(){
       //设置对应变量值,为了样式生效
       if(this.priceSort==""){
        this.priceSort="1"
      }else if(this.priceSort=="1"){
        this.priceSort="2"
      }else if(this.priceSort=="2"){
        this.priceSort = "1"
      }
      console.log("this.priceSort:"+this.priceSort);
      this.buyCountSort = ""
      this.gmtCreateSort = ""
      //把值赋值到searchObj
      this.searchObj.buyCountSort = this.buyCountSort
      this.searchObj.gmtCreateSort = this.gmtCreateSort
      this.searchObj.priceSort = this.priceSort
      //调用方法 进行搜索
      this.gotoPage(1)
    }
    
  },
};
</script>

</script>
<style scoped>
  .active {
    background: #68CB9B;
  }
  .active2 {
    background: #00cc7e;
  }
  .hide {
    display: none;
  }
  .show {
    display: block;
  }
</style>