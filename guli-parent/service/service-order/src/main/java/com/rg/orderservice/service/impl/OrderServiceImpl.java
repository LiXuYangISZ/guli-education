package com.rg.orderservice.service.impl;

import com.rg.commonutils.JwtUtils;
import com.rg.commonutils.vo.CourseWebOrder;
import com.rg.commonutils.vo.UcenterMemberOrder;
import com.rg.orderservice.client.EduClient;
import com.rg.orderservice.client.UcenterClient;
import com.rg.orderservice.entity.Order;
import com.rg.orderservice.mapper.OrderMapper;
import com.rg.orderservice.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rg.orderservice.utils.OrderNoUtil;
import com.rg.servicebase.handler.GuLiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author lxy
 * @since 2022-03-16
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private EduClient eduClient;

    @Autowired
    private UcenterClient ucenterClient;

    //生成订单
    @Override
    public String createOrder(String courseId, HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if(StringUtils.isEmpty(memberId)){
            throw new GuLiException(20001, "请先登录再进行购买!");
        }
        //1.通过远程调用根据用户id获取用户信息
        CourseWebOrder courseInfoOrder = eduClient.getCourseInfoOrder(courseId);
        //2.通过远程调用根据课程id获取课程信息
        UcenterMemberOrder userInfoOrder = ucenterClient.getUserInfoOrder(memberId);
        Order order = new Order();
        order.setCourseId(courseId);
        order.setCourseCover(courseInfoOrder.getCover());
        order.setCourseTitle(courseInfoOrder.getTitle());
        order.setTeacherName(courseInfoOrder.getTeacherName());
        order.setTotalFee(courseInfoOrder.getPrice());
        order.setMemberId(memberId);
        order.setMobile(userInfoOrder.getMobile());
        order.setNickname(userInfoOrder.getNickname());
        order.setOrderNo(OrderNoUtil.getOrderNo());

        order.setStatus(0);//订单状态:0未支付,1:已支付
        order.setPayType(1);//支付类型 1:微信支付 2:支付宝支付

        this.baseMapper.insert(order);

        return order.getOrderNo();
    }
}
