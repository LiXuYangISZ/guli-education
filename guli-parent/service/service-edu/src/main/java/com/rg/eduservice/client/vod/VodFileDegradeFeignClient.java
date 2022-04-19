package com.rg.eduservice.client.vod;

import com.rg.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author lxy
 * @version 1.0
 * @Description
 * @date 2022/2/28 17:01
 */
@Component
public class VodFileDegradeFeignClient implements VodClient{
    @Override
    public R removeAlyVideo(String id) {
        return R.error().message("删除单个视频出错了");
    }

    @Override
    public R removeMoreAlyVideo(List <String> videoIdList) {
        return R.error().message("删除多个视频出错了");
    }
}
