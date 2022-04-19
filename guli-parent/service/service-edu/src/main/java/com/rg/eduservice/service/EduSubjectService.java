package com.rg.eduservice.service;

import com.rg.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rg.eduservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author lxy
 * @since 2022-02-21
 */
public interface EduSubjectService extends IService<EduSubject> {


    boolean saveSubjects(MultipartFile file);

    List<OneSubject> getAllSubject();
}
