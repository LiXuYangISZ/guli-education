package com.rg.eduservice.controller;


import com.rg.commonutils.R;
import com.rg.eduservice.entity.subject.OneSubject;
import com.rg.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author lxy
 * @since 2022-02-21
 */
@Api(description = "课程分类")
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

    //添加课程分类
    @ApiOperation(value = "添加课程分类")
    @PostMapping("addSubjects")
    public R addSubjects(MultipartFile file){
        boolean flag = eduSubjectService.saveSubjects(file);
        if(flag){
            return R.ok().message("文件导入成功!");
        }else{
            return R.error().message("文件导入失败!");
        }
    }

    @ApiOperation(value = "查询所有课程分类")
    @GetMapping("getAllSubject")
    public R getAllSubject(){
        List <OneSubject> oneSubjectList =  eduSubjectService.getAllSubject();
        return R.ok().data("list",oneSubjectList);
    }




}

