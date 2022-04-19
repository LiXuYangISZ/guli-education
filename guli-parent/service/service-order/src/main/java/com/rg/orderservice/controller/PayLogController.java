package com.rg.orderservice.controller;


import com.rg.commonutils.R;
import com.rg.orderservice.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author lxy
 * @since 2022-03-16
 */
@RestController
@RequestMapping("/orderservice/paylog")
@CrossOrigin
public class PayLogController {

    @Autowired
    private PayLogService payLogService;

    //1.生成微信支付二维码接口
    @GetMapping("createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo){
        //返回信息,包含二维码信息,还有其他需要的信息
        Map map = payLogService.createNative(orderNo);
        System.out.println("*****生成支付二维码的map:"+map);
        return R.ok().data(map);
    }

    //2.查询订单支付状态
    @GetMapping("queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo){
        Map<String,String> map = payLogService.queryPayStatus(orderNo);
        System.out.println("*****订单支付状态的map:"+map);
        if(map==null){
            return R.error().message("支付出错了");
        }
        //如果map里面不为空,则通过map获取订单状态:已支付,未支付
        if(map.get("trade_state").equals("SUCCESS")){//支付成功
            //添加记录到支付表,更新订单表订单状态
            payLogService.updateOrderStatus(map);
            return R.ok().message("支付成功!");
        }
        return R.ok().code(25000).message("支付中...");
    }
}

