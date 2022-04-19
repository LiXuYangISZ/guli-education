package com.rg.orderservice.service;

import com.rg.orderservice.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author lxy
 * @since 2022-03-16
 */
public interface OrderService extends IService<Order> {

    String createOrder(String courseId, HttpServletRequest request);

}
