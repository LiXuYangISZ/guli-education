package com.rg.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rg.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rg.eduservice.entity.frontvo.CourseQueryVo;
import com.rg.eduservice.entity.frontvo.CourseWebVo;
import com.rg.eduservice.entity.vo.CourseInfoVo;
import com.rg.eduservice.entity.vo.CoursePublishVo;
import com.rg.eduservice.entity.vo.CourseQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author lxy
 * @since 2022-02-22
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo getPublishCourseInfo(String id);

    void removeCourse(String id);

    Page<EduCourse> pageQuery(Integer current, Integer limit, CourseQuery courseQuery);

    List<EduCourse> findCourse();


    Map<String, Object> getCourseFrontInfo(Page<EduCourse> coursePage, CourseQueryVo courseQueryVo);

    CourseWebVo getBaseCourseInfo(String courseId);

}
