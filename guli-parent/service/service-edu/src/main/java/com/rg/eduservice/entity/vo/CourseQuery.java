package com.rg.eduservice.entity.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rg.eduservice.entity.EduCourse;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lxy
 * @version 1.0
 * @Description
 * @date 2022/2/25 21:41
 */
@Data
public class CourseQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    //一级分类id
    private String subjectParentId;

    //二级分类id
    private String subjectId;

    //课程名称
    private String title;

    //讲师id
    private String teacherId;


}
