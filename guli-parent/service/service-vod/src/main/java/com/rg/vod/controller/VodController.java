package com.rg.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.rg.commonutils.R;
import com.rg.servicebase.handler.GuLiException;
import com.rg.vod.service.VodService;
import com.rg.vod.utils.ConstantVodUtils;
import com.rg.vod.utils.InitVodClient;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author lxy
 * @version 1.0
 * @Description
 * @date 2022/2/26 15:40
 */
@RestController
@RequestMapping("/vodService/video")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

    @PostMapping("uploadVideoAly")
    public R uploadVideoAly(@RequestBody MultipartFile file){
        String videoId = vodService.uploadVideoAly(file);
        return R.ok().data("videoId",videoId);
    }

    @DeleteMapping("removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable("id") String id){
        vodService.removeAlyVideo(id);
        return R.ok();
    }

    //删除多个阿里云视频的方法
    //参数为多个视频id
    @DeleteMapping("removeMoreAlyVideo")
    public R removeMoreAlyVideo(@RequestParam("videoList") List<String> videoIdList){
        vodService.removeMoreAlyVideo(videoIdList);
        return R.ok();
    }

    //根据视频id获取视频凭证
    @GetMapping("getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id){
        try {
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(id);
            GetVideoPlayAuthResponse response  = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return R.ok().data("playAuth", playAuth);
        }catch (Exception e){
            throw new GuLiException(20001, "获取凭证失败");
        }
    }




}
