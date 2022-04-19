package com.rg.orderservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.wxpay.sdk.WXPayUtil;
import com.rg.orderservice.entity.Order;
import com.rg.orderservice.entity.PayLog;
import com.rg.orderservice.mapper.PayLogMapper;
import com.rg.orderservice.service.OrderService;
import com.rg.orderservice.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rg.orderservice.utils.HttpClient;
import com.rg.servicebase.handler.GuLiException;
import org.exolab.castor.dsml.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author lxy
 * @since 2022-03-16
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

    @Autowired
    private OrderService orderService;
    //生成微信二维码
    @Override
    public Map createNative(String orderNo) {
        //1.根据订单号查询订单信息
        QueryWrapper <Order> wrapper = new QueryWrapper <>();
        wrapper.eq("order_no", orderNo);
        Order order = orderService.getOne(wrapper);

        //2.使用map设置生成二维码需要参数
        Map<String,String>  m = new HashMap <>();
        m.put("appid", "wx74862e0dfcf69954");
        m.put("mch_id", "1558950191");
        m.put("nonce_str", WXPayUtil.generateNonceStr()); //微信生成一个随机码,保证每次生成的二维码不一样
        m.put("body", order.getCourseTitle());//课程标题
        m.put("out_trade_no", orderNo);//订单号
        m.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue()+"");//将价格转换成元角分的形式
        m.put("spbill_create_ip", "127.0.0.1");
        m.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify\n");
        m.put("trade_type", "NATIVE");

        //3.发送httpclient请求,传递xml参数,向微信支付提供的固定地址发送请求
        HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
        try {
            //设置xml格式的参数
            client.setXmlParam(WXPayUtil.generateSignedXml(m,"T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);//支持http发送
            //执行post请求发送
            client.post();


            //4.得到发送请求返回结果==>返回内容是使用xml格式返回
            String xml = client.getContent();
            Map <String, String> resultMap = WXPayUtil.xmlToMap(xml);
            Map  map = new HashMap <>();
            map.put("out_trade_no", orderNo);
            map.put("course_id", order.getCourseId());
            map.put("total_fee", order.getTotalFee());
            map.put("result_code", resultMap.get("result_code"));//返回二维码操作状态码
            map.put("code_url", resultMap.get("code_url"));//二维码地址

            return map;

        }catch (Exception e){
            e.printStackTrace();
            throw new GuLiException(20001, "生成二维码失败!");
        }

    }

    //查询订单支付状态
    @Override
    public Map <String, String> queryPayStatus(String orderNo) {
        try {
            //1、封装参数
            Map m = new HashMap<>();
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("out_trade_no", orderNo);
            m.put("nonce_str", WXPayUtil.generateNonceStr());

            //2、设置请求
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();
            //3、返回第三方的数据
            String xml = client.getContent();
            //6、转成Map
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);

            //7、返回
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuLiException(20001, "查询订单状态失败!");
        }

    }

    //添加支付记录和更新订单状态
    @Override
    public void updateOrderStatus(Map <String, String> map) {
        //1.根据订单号查询订单信息
        String orderNo = map.get("out_trade_no");
        //根据订单号查询订单信息
        QueryWrapper <Order> wrapper = new QueryWrapper <>();
        wrapper.eq("order_no", orderNo);
        Order order = orderService.getOne(wrapper);

        //更新订单表中订单状态
        if(order.getStatus().intValue()==1){//如果订单状态显示已经支付,则不再进行更新.
            return;
        }
        order.setStatus(1);//1表示已经支付
        orderService.updateById(order);//进行更新订单状态

        //想支付记录表中添加支付记录
        PayLog payLog = new PayLog();
        payLog.setOrderNo(orderNo);//订单号
        payLog.setPayTime(new Date());//支付时间
        payLog.setPayType(1);//支付类型 1:微信支付  2:支付宝支付
        payLog.setTotalFee(order.getTotalFee());//总金额(分)
        payLog.setTradeState(map.get("trade_state"));//支付状态
        payLog.setTransactionId(map.get("transaction_id"));//交易流水号
        payLog.setAttr(JSONObject.toJSONString(map));//其他属性:map转换成json字符串存进去

        this.baseMapper.insert(payLog);

    }
}
