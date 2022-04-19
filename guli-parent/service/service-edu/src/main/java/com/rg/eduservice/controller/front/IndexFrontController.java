package com.rg.eduservice.controller.front;


import com.rg.commonutils.R;
import com.rg.eduservice.entity.EduCourse;
import com.rg.eduservice.entity.EduTeacher;
import com.rg.eduservice.service.EduCourseService;
import com.rg.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lxy
 * @version 1.0
 * @Description 前端查询课程讲师接口
 * @date 2022/3/2 14:39
 */
@CrossOrigin
@RestController
@RequestMapping("/eduservice/index")
public class IndexFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;

    //查询前八条热门课程,前四条讲师记录
    @GetMapping("findCourseAndTeacher")
    public R findCourseAndTeacher(){

        List <EduCourse> courseList  = courseService.findCourse();

        List <EduTeacher> teacherList = teacherService.findTeacher();

        return R.ok().data("courseList",courseList).data("teacherList",teacherList);
    }


}
