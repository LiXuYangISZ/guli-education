package com.rg.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rg.eduservice.entity.EduCourse;
import com.rg.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rg.eduservice.entity.vo.TeacherQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author lxy
 * @since 2022-02-05
 */
public interface EduTeacherService extends IService<EduTeacher> {


    List<EduTeacher> findTeacher();

    Page<EduTeacher> getEduTeacherPage(Integer current, Integer limit, TeacherQuery teacherQuery);

    Map<String, Object> getTeacherFrontList(Page<EduTeacher> page);
}
