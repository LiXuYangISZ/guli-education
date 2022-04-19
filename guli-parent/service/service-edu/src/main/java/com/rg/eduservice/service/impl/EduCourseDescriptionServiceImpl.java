package com.rg.eduservice.service.impl;

import com.rg.eduservice.entity.EduCourseDescription;
import com.rg.eduservice.mapper.EduCourseDescriptionMapper;
import com.rg.eduservice.service.EduCourseDescriptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 课程简介 服务实现类
 * </p>
 *
 * @author lxy
 * @since 2022-02-22
 */
@Service
@Transactional
public class EduCourseDescriptionServiceImpl extends ServiceImpl<EduCourseDescriptionMapper, EduCourseDescription> implements EduCourseDescriptionService {

}
