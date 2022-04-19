package com.rg.cmsservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lxy
 * @version 1.0
 * @Description Banner查询实体VO
 * @date 2022/3/2 18:09
 */
@Data
public class BannerQuery implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "链接地址")
    private String linkUrl;

    @ApiModelProperty(value = "查询开始时间", example = "2019-01-01 10:10:10")
    private String begin;

    @ApiModelProperty(value = "查询结束时间", example = "2019-12-01 10:10:10")
    private String end;
}
