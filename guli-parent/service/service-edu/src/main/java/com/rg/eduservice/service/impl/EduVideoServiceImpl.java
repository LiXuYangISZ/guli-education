package com.rg.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rg.commonutils.R;
import com.rg.eduservice.client.vod.VodClient;
import com.rg.eduservice.entity.EduVideo;
import com.rg.eduservice.mapper.EduVideoMapper;
import com.rg.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rg.servicebase.handler.GuLiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author lxy
 * @since 2022-02-22
 */
@Service
@Transactional
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    VodClient client;

    //通过课程id删除所有小节
    @Override
    public void removeVideoByCourseId(String id) {
        //1.删除小节中所有的视频
        //获取所有的id集合
        QueryWrapper <EduVideo> wrapperVideo = new QueryWrapper <>();
        wrapperVideo.eq("course_id", id);
        wrapperVideo.select("video_source_id");
        List <EduVideo> videoList = this.baseMapper.selectList(wrapperVideo);
        List <String> idList = new ArrayList <>();
        for (EduVideo video : videoList) {
            if(!StringUtils.isEmpty(video.getVideoSourceId())){
                idList.add(video.getVideoSourceId());
            }
        }

        //进行删除
        if(idList.size()>0){
            client.removeMoreAlyVideo(idList);
        }

        //根据删除课程对应的小节
        QueryWrapper <EduVideo> wrapper = new QueryWrapper <>();
        wrapper.eq("course_id",id);
        this.baseMapper.delete(wrapper);
    }

    //删除小节
    @Override
    public void delVideo(String id) {
        //先删除视频
        EduVideo video = this.baseMapper.selectById(id);
        String videoSourceId = video.getVideoSourceId();
        if(!StringUtils.isEmpty(videoSourceId)){
            client.removeAlyVideo(videoSourceId);
            R result = client.removeAlyVideo(videoSourceId);
            if(result.getCode()==20001){
                throw new GuLiException(20001, "删除视频失败,熔断器执行...");
            }
        }
        //删除小节
        this.baseMapper.deleteById(id);
    }
}
