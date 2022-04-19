package com.rg.ucenterservice.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lxy
 * @version 1.0
 * @Description 登录数据封装VO
 * @date 2022/3/8 22:15
 */
@Data
@ApiModel(value = "登录对象",description = "登录对象")
public class LoginVo {
    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "密码")
    private String password;
}
