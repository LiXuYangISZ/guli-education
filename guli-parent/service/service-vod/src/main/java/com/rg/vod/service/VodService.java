package com.rg.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author lxy
 * @version 1.0
 * @Description
 * @date 2022/2/26 15:41
 */
public interface VodService {
    String uploadVideoAly(MultipartFile file);

    void removeAlyVideo(String id);

    void removeMoreAlyVideo(List videoIdList);
}
