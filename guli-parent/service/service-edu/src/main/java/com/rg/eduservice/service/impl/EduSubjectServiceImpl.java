package com.rg.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rg.eduservice.entity.EduSubject;
import com.rg.eduservice.entity.excel.SubjectData;
import com.rg.eduservice.entity.subject.OneSubject;
import com.rg.eduservice.entity.subject.TwoSubject;
import com.rg.eduservice.listner.SubjectExcelListner;
import com.rg.eduservice.mapper.EduSubjectMapper;
import com.rg.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author lxy
 * @since 2022-02-21
 */
@Service
@Transactional
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Autowired
    private EduSubjectService eduSubjectService;

    //添加课程分类
    @Override
    public boolean saveSubjects(MultipartFile file) {

        try {
            InputStream inputStream = file.getInputStream();
            //进行excel文件读取
            EasyExcel.read(inputStream, SubjectData.class, new SubjectExcelListner(eduSubjectService)).sheet().doRead();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    //查询所有的课程分类==>树形
    @Override
    public List <OneSubject> getAllSubject() {
        //1.查询所有一级分类  parent_id=0
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<EduSubject>();
        wrapper.eq("parent_id",0);
        List <EduSubject> oneSubjectList = this.baseMapper.selectList(wrapper);

        //2.查询所有二级分类 parent_id!=0
        wrapper = new QueryWrapper<EduSubject>();
        wrapper.ne("parent_id",0);
        List <EduSubject> twoSubjectList = this.baseMapper.selectList(wrapper);

        //定义最终的返回类型
        List <OneSubject> finalSubjectList = new ArrayList <OneSubject>();

        ///3 封装一级分类
        //查询出来所有一级分类list集合集合，得到每一个一级分类对象，回去每一个一级分类对象值，
        //封装到要求的list集合里面  List<OneSubject> findSubjectList
        //List<EduSubject> ==> List <OneSubject> finalSubjectList
        for (EduSubject subject : oneSubjectList) {
            //将oneSubjectList封装到finalSubjectList
            OneSubject oneSubject = new OneSubject();
            //以下两种方法都可
            BeanUtils.copyProperties(subject,oneSubject);
            // oneSubject.setId(subject.getId());
            // oneSubject.setTitle(subject.getTitle());



            //在一级分类循环遍历查询所有的二级分类
            //创建list集合封装每一个一级分类的二级分类
            List <TwoSubject> children = new ArrayList <>();
            for (EduSubject eduSubject : twoSubjectList) {
                TwoSubject twoSubject = new TwoSubject();
                if (eduSubject.getParentId().equals(subject.getId())){
                    BeanUtils.copyProperties(eduSubject,twoSubject);
                    children.add(twoSubject);
                }
            }
            oneSubject.setChildren(children);

            finalSubjectList.add(oneSubject);//加入结果集  该语句放在76行之后也可以想想看为啥???

        }
        return finalSubjectList;
    }

}
