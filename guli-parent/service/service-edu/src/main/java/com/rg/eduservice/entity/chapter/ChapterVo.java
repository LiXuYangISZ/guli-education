package com.rg.eduservice.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lxy
 * @version 1.0
 * @Description
 * @date 2022/2/22 20:52
 */
@Data
public class ChapterVo {

    private String id;

    private String title;

    private List <VideoVo> children = new ArrayList <>();
}
