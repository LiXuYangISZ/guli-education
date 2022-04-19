package com.rg.eduservice.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lxy
 * @version 1.0
 * @Description 课程发布
 * @date 2022/2/25 14:14
 */
@Data
public class CoursePublishVo implements Serializable {
    private static final long serialVersionUID = 1L;

    //课程标题
    private String title;
    //课程封面
    private String cover;
    //课时数
    private Integer lessonNum;
    //一级分类
    private String subjectLevelOne;
    //二级分类
    private String subjectLevelTwo;
    //讲师名称
    private String teacherName;
    //课程价格
    private String price;//只用于显示
}
