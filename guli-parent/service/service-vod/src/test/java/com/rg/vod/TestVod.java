package com.rg.vod;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadFileStreamRequest;
import com.aliyun.vod.upload.resp.UploadFileStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

import java.util.List;

/**
 * @author lxy
 * @version 1.0
 * @Description
 * @date 2022/2/26 12:00
 */
public class TestVod {
    public static void main(String[] args) throws ClientException {
        // testUploadFileStream();
        getPlayAuth();
    }

    //以文件流的方式进行上传
    public static void testUploadFileStream(){
        String accessKeyId = "LTAI5tCNHHMVkeHj1LzRBqkM";
        String accessKeySecret = "4WSvjZmmrGmfCbrlAzIQUflpQmIXR1";
        String title = "6 - What If I Want to Move Faster123.mp4 ";
        String fileName = "F:/自学课程/项目资料/02谷粒学苑(分布式项目)/项目资料/1-阿里云上传测试视频/6 - What If I Want to Move Faster.mp4";
        UploadFileStreamRequest request = new UploadFileStreamRequest(accessKeyId, accessKeySecret, title, fileName);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadFileStreamResponse response = uploader.uploadFileStream(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n"); //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }

    //根据id获取视频播放凭证
    public static void getPlayAuth()throws ClientException{
        //创建初始化对象
        DefaultAcsClient client = InitVodClient.initVodClient("LTAI5tCNHHMVkeHj1LzRBqkM", "4WSvjZmmrGmfCbrlAzIQUflpQmIXR1");

        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

        request.setVideoId("7f35d80d356542d5bd5356cefdfde0f1");

        response = client.getAcsResponse(request);
        System.out.println("palyAuth:"+response.getPlayAuth());
    }

    //根据id获取视频播放地址
    public static void getPlayUrl()throws ClientException{

        //创建初始化对象
        DefaultAcsClient client = InitVodClient.initVodClient("LTAI5tCNHHMVkeHj1LzRBqkM", "4WSvjZmmrGmfCbrlAzIQUflpQmIXR1");
        //创建获取视频地址request和response
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        request.setVideoId("efc88346e5dd45fda5b161c9ddbd0d9d");
        //调用初始化对象里面的方法,传递request,获取数据
        response = client.getAcsResponse(request);

        List <GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        //Base信息 视频名称
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
    }
}
