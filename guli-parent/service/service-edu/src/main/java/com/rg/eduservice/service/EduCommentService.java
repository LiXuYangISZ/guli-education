package com.rg.eduservice.service;

import com.rg.eduservice.entity.EduComment;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author lxy
 * @since 2022-03-15
 */
public interface EduCommentService extends IService<EduComment> {

    Map <String,Object> getCommentInfo(long page, long limit, String courseId);

    boolean addComment(HttpServletRequest request,EduComment comment);

}
