package com.rg.eduservice.service;

import com.rg.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rg.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author lxy
 * @since 2022-02-22
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideo(String courseId);

    Boolean delChapter(String id);

    void removeChapterByCourseId(String id);
}
