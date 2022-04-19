package com.rg.orderservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rg.commonutils.JwtUtils;
import com.rg.commonutils.R;
import com.rg.orderservice.entity.Order;
import com.rg.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author lxy
 * @since 2022-03-16
 */
@RestController
@RequestMapping("/orderservice/order")
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;

    //1.生成订单的方法
    @PostMapping("createOrder/{courseId}")
    public R saveOrder(@PathVariable String courseId, HttpServletRequest request){
        //创建订单,返回订单号
        String orderNo = orderService.createOrder(courseId, request);
        return R.ok().data("orderId",orderNo);
    }

    //2.根据订单id查询订单信息
    @GetMapping("getOrderInfo/{orderNo}")
    public R getOrderInfo(@PathVariable String orderNo){
        QueryWrapper <Order> wrapper = new QueryWrapper <>();
        wrapper.eq("order_no", orderNo);
        Order order = orderService.getOne(wrapper);
        return R.ok().data("order", order);
    }

    //3.根据课程id和用户id查询订单表中订单状态
    @GetMapping("isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable("courseId") String courseId,@PathVariable("memberId") String memberId){
        QueryWrapper <Order> wrapper = new QueryWrapper <>();
        if(StringUtils.isEmpty(memberId)){
            return false;//如果未登录,则显示的就是立即购买...
        }
        wrapper.eq("course_id",courseId).eq("member_id",memberId).eq("status",1);
        int count = orderService.count(wrapper);
        if(count>0){//已经进行支付
            return true;
        }else{
            return false;
        }
    }

}

