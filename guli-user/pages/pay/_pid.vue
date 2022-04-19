<template>
  <div class="cart py-container">
    <!--主内容-->
    <div class="checkout py-container  pay">
      <div class="checkout-tit">
        <h4 class="fl tit-txt"><span class="success-icon"></span><span class="success-info">订单提交成功，请您及时付款！订单号：{{payObj.out_trade_no}}</span>
        </h4>
        <span class="fr"><em class="sui-lead">应付金额：</em><em class="orange money">￥{{payObj.total_fee}}</em></span>
        <div class="clearfix"></div>
      </div>
      <div class="checkout-steps">
        <div class="fl weixin">微信支付</div>
        <div class="fl sao">
          <p class="red">请使用微信扫一扫。</p>
          <div class="fl code">
            <!-- <img id="qrious" src="~/assets/img/erweima.png" alt=""> -->
            <!-- <qriously value="weixin://wxpay/bizpayurl?pr=R7tnDpZ" :size="338"/> -->
            <qriously :value="payObj.code_url" :size="338"/>
            <div class="saosao">
              <p>请使用微信扫一扫</p>
              <p>扫描二维码支付</p>
            </div>

          </div>

        </div>
        <div class="clearfix"></div>
        <!-- <p><a href="pay.html" target="_blank">> 其他支付方式</a></p> -->
        
      </div>
    </div>
  </div>
</template>

<script>
import ordersApi from '@/api/orders'
export default {
    asyncData({params, error}) {
       return ordersApi.createNative(params.pid).then(response=>{
           console.log(response.data.data)
           return {
               payObj:response.data.data
           } 
       })
    },
    data() {
        return {
            timer:''//定时器
        }
    },
    //每个三秒调用一次查询订单的支付状态方法
    mounted() {//页面渲染完毕后执行
        this.timer = setInterval(()=>{
            this.queryOrderStatus(this.payObj.out_trade_no)
        },3000);
    },
    methods: {
        queryOrderStatus(orderNo){
            ordersApi.queryPayStatus(orderNo).then(response=>{
                if(response.data.success){
                    clearInterval(this.timer)//支付成功,清空定时器
                    //支付成功
                    this.$message({
                        type:'success',
                        message:'支付成功!'
                    })
                    //跳转至课程详情页面
                    this.$router.push({path:'/course/'+this.payObj.course_id})
                }
            })
        }
    },

}
</script>