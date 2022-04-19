package com.rg.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rg.commonutils.R;
import com.rg.eduservice.entity.EduCourse;
import com.rg.eduservice.entity.vo.CourseInfoVo;
import com.rg.eduservice.entity.vo.CoursePublishVo;
import com.rg.eduservice.entity.vo.CourseQuery;
import com.rg.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author lxy
 * @since 2022-02-22
 */
@Api(description = "课程管理")
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    @ApiOperation("添加课程基本信息")
    @PostMapping("saveCourseInfo")
    @CacheEvict(value = "course", allEntries=true)
    public R saveCourseInfo(@ApiParam(name = "CourseInfoForm", value = "课程基本信息", required = true)
            @RequestBody CourseInfoVo courseInfoVo){
        //返回课程id,为了跳转到课程大纲使用
        String courseId = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId", courseId);
    }

    //根据课程id查询课程信息
    @ApiOperation("查询课程信息")
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId){//@PathVariable可以省略嘛???
        CourseInfoVo courseInfoVo =  courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo",courseInfoVo);
    }

    //根据课程id修改课程信息
    @ApiOperation("修改课程信息")
    @PutMapping("updateCourseInfo")
    @CacheEvict(value = "course", allEntries=true)
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    //根据课程id查询课程的发布信息
    @ApiOperation("查询课程发布信息")
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id){
        CoursePublishVo courseInfo =  courseService.getPublishCourseInfo(id);
        return R.ok().data("courseInfo",courseInfo);
    }

    //课程发布
    @PutMapping("publishCourse/{id}")
    @CacheEvict(value = "course", allEntries=true)
    public R publishCourse(@PathVariable String id){
        EduCourse course = new EduCourse();
        course.setId(id);
        course.setStatus("Normal");
        boolean update = courseService.updateById(course);
        if(update){
            return R.ok();
        }else{
            return R.error();
        }
    }

    //删除课程
    @DeleteMapping("removeCourse/{id}")
    @CacheEvict(value = "course", allEntries=true)
    public R removeCourse(@PathVariable String id){
        courseService.removeCourse(id);
        return R.ok();
    }

    //分页查询课程
    @PostMapping("pageQuery/{current}/{limit}")
    public R pageQuery(@PathVariable Integer current,
                       @PathVariable Integer limit,
                       @RequestBody CourseQuery courseQuery)
    {
        Page<EduCourse> page =  courseService.pageQuery(current,limit,courseQuery);
        return R.ok().data("total",page.getTotal()).data("list",page.getRecords());
    }

}

