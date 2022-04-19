package com.rg.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rg.eduservice.entity.EduCourse;
import com.rg.eduservice.entity.EduCourseDescription;
import com.rg.eduservice.entity.EduTeacher;
import com.rg.eduservice.entity.frontvo.CourseQueryVo;
import com.rg.eduservice.entity.frontvo.CourseWebVo;
import com.rg.eduservice.entity.vo.CourseInfoVo;
import com.rg.eduservice.entity.vo.CoursePublishVo;
import com.rg.eduservice.entity.vo.CourseQuery;
import com.rg.eduservice.mapper.EduCourseMapper;
import com.rg.eduservice.service.EduChapterService;
import com.rg.eduservice.service.EduCourseDescriptionService;
import com.rg.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rg.eduservice.service.EduVideoService;
import com.rg.servicebase.handler.GuLiException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author lxy
 * @since 2022-02-22
 */
@Service
@Transactional //开启事务管理
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService courseDescriptionService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private EduVideoService videoService;

    //添加课程基本信息
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //1.添加课程基本信息信息到课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int insert = this.baseMapper.insert(eduCourse);
        if(insert==0){
            throw new GuLiException(20001, "添加课程失败!");
        }

        //2.添加课程简介信息到简介表
        String id = eduCourse.getId();//获得添加之后的课程id
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo,eduCourseDescription);
        eduCourseDescription.setId(id);//因为两张表是一对一关系,所以id也应该相同
        boolean save = courseDescriptionService.save(eduCourseDescription);
        if(save==false){
            throw new GuLiException(20001, "添加课程失败!");
        }
        return id;
    }

    //通过id查询课程信息
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        //1.查询课程信息
        EduCourse course = this.baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(course,courseInfoVo);
        //2.查询课程描述信息
        EduCourseDescription courseDescription = courseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());

        return courseInfoVo;
    }

    //修改课程信息
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //1.修改课程表
        EduCourse course = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,course);
        int update1 = this.baseMapper.updateById(course);
        if(update1==0){
            throw new GuLiException(20001, "修改失败!");
        }
        //2.修改课程描述表
        EduCourseDescription courseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo,courseDescription);
        boolean flag = courseDescriptionService.updateById(courseDescription);
        if(!flag){
            throw new GuLiException(20001, "修改失败!");
        }
    }

    //根据id查询课程发布信息
    @Override
    public CoursePublishVo getPublishCourseInfo(String id) {
        CoursePublishVo coursePublishVo = this.baseMapper.getPublishCourseInfo(id);
        return coursePublishVo;
    }

    //根据id删除课程信息
    @Override
    public void removeCourse(String id) {
        //1.删除小节
        videoService.removeVideoByCourseId(id);
        //2.删除章节
        chapterService.removeChapterByCourseId(id);
        //3.删除课程描述信息
        courseDescriptionService.removeById(id);
        //4.删除课程信息
        this.baseMapper.deleteById(id);

    }

    //对课程进行分页查询
    @Override
    public Page <EduCourse> pageQuery(Integer current, Integer limit, CourseQuery courseQuery) {
        Page <EduCourse> page = new Page <>(current,limit);
        QueryWrapper <EduCourse> wrapper = new QueryWrapper <>();
        String title = courseQuery.getTitle();
        String subjectId = courseQuery.getSubjectId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String teacherId = courseQuery.getTeacherId();
        //判空,构造查询条件
        if(!StringUtils.isEmpty(title)){
            wrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(subjectId)){
            wrapper.eq("subject_id",subjectId);
        }
        if(!StringUtils.isEmpty(subjectParentId)){
            wrapper.eq("subject_parent_id",subjectParentId);
        }
        if(!StringUtils.isEmpty(teacherId)){
            wrapper.eq("teacher_id",teacherId);
        }
        wrapper.orderByDesc("gmt_create");
        //进行分页查询
        this.baseMapper.selectPage(page, wrapper);//分页查询后自动封装到page中
        return page;
    }

    //查询前8条热门课程(前台页面)
    @Override
    @Cacheable(value = "course",key="'courseList'")
    public List <EduCourse> findCourse() {
        //查询前8条热门课程 按照view_count,buy_count排序
        QueryWrapper <EduCourse> courseWrapper = new QueryWrapper <>();
        courseWrapper.orderByDesc("view_count","buy_count");
        courseWrapper.last("limit 8");
        // this.baseMapper.select
        List <EduCourse> courseList = this.baseMapper.selectList(courseWrapper);

        return courseList;
    }

    //条件查询带分页
    @Override
    public Map <String, Object> getCourseFrontInfo(Page <EduCourse> coursePage, CourseQueryVo courseQueryVo) {
        QueryWrapper <EduCourse> wrapper = new QueryWrapper <>();
        String buyCountSort = courseQueryVo.getBuyCountSort();
        String gmtCreateSort = courseQueryVo.getGmtCreateSort();
        String priceSort = courseQueryVo.getPriceSort();
        String subjectParentId = courseQueryVo.getSubjectParentId();
        String subjectId = courseQueryVo.getSubjectId();
        if(!StringUtils.isEmpty(buyCountSort)){
            if(buyCountSort.equals("1")){//1是降序
                wrapper.orderByDesc("buy_count");
            }else if(buyCountSort.equals("2")){//2是升序
                wrapper.orderByAsc("buy_count");
            }
        }
        if(!StringUtils.isEmpty(gmtCreateSort)){
            if(gmtCreateSort.equals("1")){//1是降序
                wrapper.orderByDesc("gmt_create");
            }else if(gmtCreateSort.equals("2")){//2是升序
                wrapper.orderByAsc("gmt_create");
            }
        }
        if(!StringUtils.isEmpty(priceSort)){
            if(priceSort.equals("1")){//1是降序
                wrapper.orderByDesc("price");
            }else if(priceSort.equals("2")){//2是升序
                wrapper.orderByAsc("price");
            }
        }
        if(!StringUtils.isEmpty(subjectParentId)){
            wrapper.eq("subject_parent_id", subjectParentId);
        }
        if(!StringUtils.isEmpty(subjectId)){
            wrapper.eq("subject_id", subjectId);
        }

        this.baseMapper.selectPage(coursePage, wrapper);

        List <EduCourse> records = coursePage.getRecords();
        long current = coursePage.getCurrent();
        long pages = coursePage.getPages();
        long size = coursePage.getSize();
        long total = coursePage.getTotal();
        boolean hasNext = coursePage.hasNext();
        boolean hasPrevious = coursePage.hasPrevious();

        Map <String, Object> map = new HashMap <>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);


        return map;
    }

    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return this.baseMapper.getBaseCourseInfo(courseId);
    }
}
