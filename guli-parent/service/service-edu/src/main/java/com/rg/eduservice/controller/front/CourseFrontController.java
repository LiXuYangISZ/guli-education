package com.rg.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rg.commonutils.JwtUtils;
import com.rg.commonutils.R;
import com.rg.commonutils.vo.CourseWebOrder;
import com.rg.eduservice.client.order.OrderClient;
import com.rg.eduservice.entity.EduCourse;
import com.rg.eduservice.entity.chapter.ChapterVo;
import com.rg.eduservice.entity.frontvo.CourseQueryVo;
import com.rg.eduservice.entity.frontvo.CourseWebVo;
import com.rg.eduservice.service.EduChapterService;
import com.rg.eduservice.service.EduCourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author lxy
 * @version 1.0
 * @Description
 * @date 2022/3/12 17:58
 */
@RestController
@CrossOrigin
@RequestMapping("/eduservice/coursefront")
public class CourseFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private OrderClient orderClient;

    //1.条件查询带分页查询课程
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(@PathVariable long page, @PathVariable long limit,
                                @RequestBody(required = false)CourseQueryVo courseQueryVo){
        Page <EduCourse> coursePage = new Page <>(page, limit);
        Map <String,Object> map = courseService.getCourseFrontInfo(coursePage,courseQueryVo);
        //返回分页所有数据
        return R.ok().data(map);
    }

    //2.课程详情的方法
    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId, HttpServletRequest request){
        //根据课程id,编写sql语句查询课程信息
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);

        //根据课程id,查询章节和小节
        List <ChapterVo> chapterVideoList = chapterService.getChapterVideo(courseId);

        //根据课程id和用户id查询当前课程是否已经支付过了
        boolean buyCourse = orderClient.isBuyCourse(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("course",courseWebVo).data("chapterVideoList",chapterVideoList).data("isBuy",buyCourse);
    }

    //根据课程查询课程基本信息
    @GetMapping("getCourseInfoOrder/{courseId}")
    public CourseWebOrder getCourseInfoOrder(@PathVariable("courseId") String courseId){
        EduCourse course = courseService.getById(courseId);
        CourseWebOrder courseWebOrder = new CourseWebOrder();
        BeanUtils.copyProperties(course,courseWebOrder);
        return courseWebOrder;
    }
}
