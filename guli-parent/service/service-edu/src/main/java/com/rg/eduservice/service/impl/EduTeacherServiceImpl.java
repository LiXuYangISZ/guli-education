package com.rg.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rg.eduservice.entity.EduCourse;
import com.rg.eduservice.entity.EduTeacher;
import com.rg.eduservice.entity.vo.TeacherQuery;
import com.rg.eduservice.mapper.EduTeacherMapper;
import com.rg.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author lxy
 * @since 2022-02-05
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {


    //查询前四条讲师记录(前台页面)
    @Override
    @Cacheable(value = "teacher",key="'teacherList'")
    public List <EduTeacher> findTeacher() {
        QueryWrapper <EduTeacher> teacherWrapper = new QueryWrapper <>();
        teacherWrapper.orderByDesc("sort");
        teacherWrapper.last("limit 4");
        List <EduTeacher> teacherList = this.baseMapper.selectList(teacherWrapper);
        return teacherList;
    }

    @Override

    public Page <EduTeacher> getEduTeacherPage(Integer current, Integer limit, TeacherQuery teacherQuery) {
        Page <EduTeacher> page = new Page <>(current, limit);
        //构建条件
        QueryWrapper <EduTeacher> wrapper = new QueryWrapper <>();
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        //mybatis我们学过动态sql,这里我们可以使用MP简化操作
        if(!StringUtils.isEmpty(name)){
            wrapper.like("name", name);
        }
        if(!StringUtils.isEmpty(level)){
            wrapper.eq("level", level);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create", begin);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create", end);
        }

        wrapper.orderByDesc("gmt_create");//按照日期进行排序,后添加到放在前面

        this.baseMapper.selectPage(page, wrapper);//分页查询
        return page;
    }

    //查询讲师列表 带分页
    @Override
    public Map <String, Object> getTeacherFrontList(Page <EduTeacher> page) {
        QueryWrapper <EduTeacher> wrapper = new QueryWrapper <>();
        wrapper.orderByAsc("sort");
        this.baseMapper.selectPage(page, wrapper);

        List <EduTeacher> records = page.getRecords();
        long current = page.getCurrent();
        long pages = page.getPages();
        long size = page.getSize();
        long total = page.getTotal();
        boolean hasNext = page.hasNext();
        boolean hasPrevious = page.hasPrevious();

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
}
