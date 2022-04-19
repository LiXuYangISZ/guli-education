package com.rg.eduservice.controller;


import com.rg.commonutils.R;
import com.rg.eduservice.entity.EduChapter;
import com.rg.eduservice.entity.chapter.ChapterVo;
import com.rg.eduservice.service.EduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author lxy
 * @since 2022-02-22
 */
@Api(description = "课程大纲")
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService chapterService;

    //课程大纲列表,根据课程id进行查询
    @ApiOperation("根据课程id查询章节和小节")
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId){
        List<ChapterVo>  chapterVoList = chapterService.getChapterVideo(courseId);
        return R.ok().data("list",chapterVoList);
    }

    //查询章节
    @GetMapping("getChapter/{id}")
    @ApiOperation("查询章节通过id")
    public R getChapter(@PathVariable String id){
        EduChapter chapter = chapterService.getById(id);
        return R.ok().data("chapter",chapter);
    }

    //添加章节
    @PostMapping("addChapter")
    @ApiOperation("添加章节")
    public R addChapter(@RequestBody EduChapter chapter){
        boolean save = chapterService.save(chapter);
        if(save){
            return R.ok();
        }else{
            return R.error();
        }
    }

    //修改章节
    @ApiOperation("修改章节")
    @PutMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter chapter){
        boolean update = chapterService.updateById(chapter);
        if(update){
            return R.ok();
        }else{
            return R.error();
        }
    }

    //删除章节
    @DeleteMapping("delChapter/{id}")
    public R delChapter(@PathVariable String id){
        Boolean flag = chapterService.delChapter(id);
        if (flag){
            return R.ok();
        }else{
            return R.error();
        }
    }

}

