package com.rg.eduservice.client.vod;

import com.rg.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author lxy
 * @version 1.0
 * @Description
 * @date 2022/2/28 14:38
 */
@FeignClient(value = "service-vod",fallback = VodFileDegradeFeignClient.class)
@Component
public interface VodClient {

    //定义调用方法的路径,一定要写全
    //PathVariable注解一定要指定参数名称,否则出错.
    //根据视频id删除阿里云视频
    @DeleteMapping("/vodService/video/removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable("id") String id);

    //根据视频id删除多个阿里云视频
    @DeleteMapping("/vodService/video/removeMoreAlyVideo")
    public R removeMoreAlyVideo(@RequestParam("videoList") List<String> videoIdList);
}
