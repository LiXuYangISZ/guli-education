package com.rg.orderservice.client;

import com.rg.commonutils.vo.UcenterMemberOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author lxy
 * @version 1.0
 * @Description
 * @date 2022/3/16 16:00
 */
@FeignClient(value = "service-ucenter")
@Component
public interface UcenterClient {
    @GetMapping("/ucenterservice/member/getUserInfoOrder/{memberId}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable("memberId") String memberId);
}
