package com.rg.oss.controller;

import com.rg.commonutils.R;
import com.rg.oss.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.crypto.Data;

/**
 * @author lxy
 * @version 1.0
 * @Description
 * @date 2022/2/19 18:45
 */
@Api(description="阿里云文件管理")
@CrossOrigin //跨域
@RestController
@RequestMapping("/ossService/file")
public class OssController {

    @Autowired
    private OssService ossService;

    @ApiOperation("上传头像文件")
    @PostMapping("upload")
    public R uploadFile(@ApiParam(name = "file", value = "文件", required = true)
                                    MultipartFile file){
        //返回上传文件的Url路径
        String url = ossService.uploadFile(file);
        return R.ok().message("文件上传成功!").data("url",url);
    }
}
