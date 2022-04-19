package com.rg.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rg.commonutils.R;
import com.rg.eduservice.entity.EduCourse;
import com.rg.eduservice.entity.EduTeacher;
import com.rg.eduservice.service.EduCourseService;
import com.rg.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author lxy
 * @version 1.0
 * @Description
 * @date 2022/3/12 10:40
 */
@RestController
@CrossOrigin
@RequestMapping("/eduservice/teacherfront")
public class TeacherFrontController {

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    //1.分页查询讲师的方法
    @GetMapping("getTeacherFrontList/{page}/{limit}")
    public R getTeacherFrontList(@PathVariable long page,@PathVariable long limit){
        Page <EduTeacher> teacherPage = new Page <>(page,limit);
        Map<String,Object> map=  teacherService.getTeacherFrontList(teacherPage);
        return R.ok().data(map);
    }


    //2.讲师详情的功能
    @GetMapping("getTeacherFrontInfo/{teacherId}")
    public R getTeacherFrontInfo(@PathVariable String teacherId){

        //1.根据讲师id查询讲师基本信息
        EduTeacher eduTeacher = teacherService.getById(teacherId);
        //2.根据讲师id查询讲师的所有课程
        QueryWrapper <EduCourse> wrapper = new QueryWrapper <>();
        wrapper.eq("teacher_id", teacherId);
        List <EduCourse> courseList = courseService.list(wrapper);
        return R.ok().data("teacher",eduTeacher).data("courseList",courseList);
    }

}
