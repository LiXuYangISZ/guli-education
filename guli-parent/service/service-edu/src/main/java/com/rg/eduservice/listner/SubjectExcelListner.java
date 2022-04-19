package com.rg.eduservice.listner;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rg.eduservice.entity.EduSubject;
import com.rg.eduservice.entity.excel.SubjectData;
import com.rg.eduservice.service.EduSubjectService;
import com.rg.servicebase.handler.GuLiException;

import java.util.Map;

/**
 * @author lxy
 * @version 1.0
 * @Description
 * @date 2022/2/21 10:29
 */
public class SubjectExcelListner extends AnalysisEventListener<SubjectData> {
    //因为SubjectExcelListner在EduSubjectServiceImpl每次会被new的形式使用,所以SubjectExcelListner不能交给
    //Spring进行管理,也就不能使用@Autowird或@Resource注解注入对象.
    //但是该类需要调用service中的方法进行数据库操作,该如何使用呢?
    //可以通过构造方法传递参数的形式,使用service对象.

    private EduSubjectService eduSubjectService;

    public SubjectExcelListner(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    public SubjectExcelListner() {}


    //一行一行的去读取
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if(subjectData==null){
            throw new GuLiException(20001,"数据为空!");
        }
        //添加一级分类
        EduSubject oneSubject = this.existOneSubject(subjectData.getOneSubjectName());
        if(oneSubject==null){//没有相同的一级分类
            oneSubject = new EduSubject();
            oneSubject.setTitle(subjectData.getOneSubjectName());
            oneSubject.setParentId("0");
            eduSubjectService.save(oneSubject);
        }

        //获取一级分类id
        String pid = oneSubject.getId();

        //添加二级分类
        EduSubject twoSubject = this.existTwoSubject(subjectData.getTwoSubjectName(), pid);
        if(twoSubject==null){
            twoSubject = new EduSubject();
            twoSubject.setTitle(subjectData.getTwoSubjectName());
            twoSubject.setParentId(pid);
            eduSubjectService.save(twoSubject);
        }

    }


    //读取Excel表头
    @Override
    public void invokeHeadMap(Map <Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头信息:"+headMap);
    }



    //读取完成后的操作
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    //判断一级分类是否重复
    private EduSubject existOneSubject(String name){
        QueryWrapper <EduSubject> wrapper = new QueryWrapper <>();
        wrapper.eq("title", name).eq("parent_id",0);
        EduSubject subject = eduSubjectService.getOne(wrapper);
        return subject;
    }

    //判断二级分类是否重复
    private EduSubject existTwoSubject(String name,String pid){
        QueryWrapper <EduSubject> wrapper = new QueryWrapper <>();
        wrapper.eq("title", name).eq("parent_id", pid);
        EduSubject subject = eduSubjectService.getOne(wrapper);
        return subject;
    }
}
