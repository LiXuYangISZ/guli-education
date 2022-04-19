package com.rg.eduservice.controller;

import com.rg.commonutils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @author lxy
 * @version 1.0
 * @Description
 * @date 2022/2/14 14:47
 */
@Api(description = "管理员登录")
@RestController
@RequestMapping("/eduservice/user")
@CrossOrigin //解决跨域问题
public class EduLoginController {
    //login功能
    @ApiOperation("登录获取token")
        @PostMapping("login")
    public R login(){
        return R.ok().data("token", "admin");
    }

    //info
    @ApiOperation("获取管理员信息")
    @GetMapping("info")
    public R info(){
        return R.ok().data("roles", "[admin]").data("name", "zhangsan").data("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
