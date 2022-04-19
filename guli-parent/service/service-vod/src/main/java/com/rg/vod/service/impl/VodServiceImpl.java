package com.rg.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.rg.servicebase.handler.GuLiException;
import com.rg.vod.service.VodService;
import com.rg.vod.utils.ConstantVodUtils;
import com.rg.vod.utils.InitVodClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * @author lxy
 * @version 1.0
 * @Description
 * @date 2022/2/26 15:41
 */
@Service
@Transactional
public class VodServiceImpl implements VodService {
    @Override
    public String uploadVideoAly(MultipartFile file) {

        String accessKeyId = ConstantVodUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantVodUtils.ACCESS_KEY_SECRET;
        String title = file.getOriginalFilename().substring(0,file.getOriginalFilename().lastIndexOf("."));//上传之后显示名称
        String fileName = file.getOriginalFilename();//上传文件原始名称
        try {
            InputStream inputStream = file.getInputStream();//上传文件流
            UploadStreamRequest request = new UploadStreamRequest(accessKeyId, accessKeySecret, title, fileName, inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            String videoId = response.getVideoId();
            return videoId;
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuLiException(20001, "文件上传失败!");
        }
    }

    //根据id删除阿里云上的视频
    @Override
    public void removeAlyVideo(String id) {
        try {
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //
            DeleteVideoRequest request = new DeleteVideoRequest();
            DeleteVideoResponse response = new DeleteVideoResponse();
            //想request中设置视频id
            request.setVideoIds(id);
            response = client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuLiException(20001, "视频删除失败!");
        }
    }

    //删除多个阿里云视频
    @Override
    public void removeMoreAlyVideo(List videoIdList) {
        try {
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            DeleteVideoResponse response = new DeleteVideoResponse();
            String videoIds = StringUtils.join(videoIdList.toArray(),",");
            //想request中设置视频id
            request.setVideoIds(videoIds);
            response = client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuLiException(20001, "视频删除失败!");
        }

    }
}
