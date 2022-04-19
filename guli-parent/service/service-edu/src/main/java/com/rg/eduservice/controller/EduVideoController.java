package com.rg.eduservice.controller;


import com.rg.commonutils.R;
import com.rg.eduservice.entity.EduVideo;
import com.rg.eduservice.service.EduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author lxy
 * @since 2022-02-22
 */
@Api(description = "小节管理")
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    //增加小节
    @ApiOperation("添加小节")
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo video){
        boolean flag = videoService.save(video);
        if(flag){
            return R.ok();
        }else{
            return R.error();
        }
    }


    //修改小节
    @ApiOperation("修改小节")
    @PutMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo video){
        boolean flag = videoService.updateById(video);
        if(flag){
            return R.ok();
        }else{
            return R.error();
        }
    }

    //删除小节(删除小节时候，同时要把里面的视频删除)
    @ApiOperation("删除小节")
    @DeleteMapping("delVideo/{id}")
    public R delVideo(@PathVariable String id){
        videoService.delVideo(id);
        return R.ok();
    }

    //查询小节
    @ApiOperation("查询小节")
    @GetMapping("getVideo/{id}")
    public R getVideo(@PathVariable String id){
        EduVideo video = videoService.getById(id);
        return R.ok().data("video",video);
    }
}

