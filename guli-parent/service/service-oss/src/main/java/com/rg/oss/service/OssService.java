package com.rg.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author lxy
 * @version 1.0
 * @Description
 * @date 2022/2/19 18:45
 */
public interface OssService {
    String uploadFile(MultipartFile file);
}
