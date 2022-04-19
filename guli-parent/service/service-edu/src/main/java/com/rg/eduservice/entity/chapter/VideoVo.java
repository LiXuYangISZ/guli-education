package com.rg.eduservice.entity.chapter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lxy
 * @version 1.0
 * @Description
 * @date 2022/2/22 20:53
 */
@Data
public class VideoVo {
    private String id;

    private String title;

    private Boolean isFree;

    @ApiModelProperty(value = "云端视频资源")
    private String videoSourceId;
}
