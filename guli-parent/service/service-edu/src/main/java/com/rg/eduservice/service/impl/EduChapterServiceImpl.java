package com.rg.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rg.eduservice.entity.EduChapter;
import com.rg.eduservice.entity.EduVideo;
import com.rg.eduservice.entity.chapter.ChapterVo;
import com.rg.eduservice.entity.chapter.VideoVo;
import com.rg.eduservice.mapper.EduChapterMapper;
import com.rg.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rg.eduservice.service.EduVideoService;
import com.rg.servicebase.handler.GuLiException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author lxy
 * @since 2022-02-22
 */
@Service
@Transactional
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;

    //根据课程id查询课程大纲
    @Override
    public List <ChapterVo> getChapterVideo(String courseId) {
        //1.查询所有章节
        QueryWrapper <EduChapter> chapterWrapper = new QueryWrapper <>();
        chapterWrapper.eq("course_id",courseId);
        List <EduChapter> chapterList = this.baseMapper.selectList(chapterWrapper);

        //2.查询所有的小节
        QueryWrapper <EduVideo> videoWrapper = new QueryWrapper <>();
        videoWrapper.eq("course_id",courseId);
        List <EduVideo> videoList = videoService.list(videoWrapper);

        //定义最终的数据类型
        List <ChapterVo> finalChapterList = new ArrayList <ChapterVo>();

        //3.遍历章节集合,将其封装到List <ChapterVo>
        for (EduChapter chapter : chapterList) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter,chapterVo);
            finalChapterList.add(chapterVo);//将其放入结果集

            List <VideoVo> children = new ArrayList <>();
            //4.遍历小节集合,将其封装到对应的章节中
            for (EduVideo video : videoList) {
                if(video.getChapterId().equals(chapter.getId())){//如果当前小节输入当前章节,则加入该章节
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video,videoVo);
                    children.add(videoVo);
                }
            }
            chapterVo.setChildren(children);//将章节的对应小节设置进去
        }

        return finalChapterList;
    }

    //删除章节
    @Override
    public Boolean delChapter(String chapterId) {
        //1.先查询该章节下是否有小节
        QueryWrapper <EduVideo> wrapper = new QueryWrapper <>();
        wrapper.eq("chapter_id", chapterId);
        int count = videoService.count(wrapper);
        //2.如果有则 无法删除
        if(count>0){
            throw new GuLiException(20001,"清先删除小节,再删除章节!");
        }else{
            //如果没有则进行删除..
            int delete = this.baseMapper.deleteById(chapterId);
            return delete > 0;
        }
    }

    @Override
    public void removeChapterByCourseId(String id) {
        QueryWrapper <EduChapter> wrapper = new QueryWrapper <>();
        wrapper.eq("course_id",id);
        this.baseMapper.delete(wrapper);
    }
}
