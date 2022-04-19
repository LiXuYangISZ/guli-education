package com.rg.eduservice.mapper;

import com.rg.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rg.eduservice.entity.frontvo.CourseWebVo;
import com.rg.eduservice.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author lxy
 * @since 2022-02-22
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    public CoursePublishVo getPublishCourseInfo(String id);

    public CourseWebVo getBaseCourseInfo(String courseId);
}
