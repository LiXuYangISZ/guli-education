package com.rg.orderservice.service;

import com.rg.orderservice.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author lxy
 * @since 2022-03-16
 */
public interface PayLogService extends IService<PayLog> {

    //生成支付二维码
    Map createNative(String orderNo);

    //查询订单支付状态
    Map<String, String> queryPayStatus(String orderNo);

    //添加支付记录和更新订单状态
    void updateOrderStatus(Map<String, String> map);
}
