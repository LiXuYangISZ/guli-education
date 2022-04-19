package com.rg.orderservice.client;

import com.rg.commonutils.vo.CourseWebOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author lxy
 * @version 1.0
 * @Description
 * @date 2022/3/16 15:59
 */
@FeignClient(value = "service-edu")
@Component
public interface EduClient {

    @GetMapping("/eduservice/coursefront/getCourseInfoOrder/{courseId}")
    public CourseWebOrder getCourseInfoOrder(@PathVariable("courseId") String courseId);
}
