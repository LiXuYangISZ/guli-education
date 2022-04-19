package com.rg.eduservice.client.order;

import com.rg.eduservice.client.vod.VodFileDegradeFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author lxy
 * @version 1.0
 * @Description
 * @date 2022/3/17 17:07
 */
@FeignClient(value = "service-order",fallback = OrderClientImpl.class)
@Component
public interface OrderClient {
    @GetMapping("/orderservice/order/isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable("courseId") String courseId, @PathVariable("memberId") String memberId);
}
