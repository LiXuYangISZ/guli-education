package com.rg.eduservice.service;

import com.rg.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author lxy
 * @since 2022-02-22
 */
public interface EduVideoService extends IService<EduVideo> {

    void removeVideoByCourseId(String id);

    void delVideo(String id);
}
