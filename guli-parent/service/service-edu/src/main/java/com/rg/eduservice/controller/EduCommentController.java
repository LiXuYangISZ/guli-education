package com.rg.eduservice.controller;


import com.rg.commonutils.R;
import com.rg.eduservice.client.ucenter.UcenterClient;
import com.rg.eduservice.entity.EduComment;
import com.rg.eduservice.service.EduCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author lxy
 * @since 2022-03-15
 */
@RestController
@RequestMapping("/eduservice/comment")
@CrossOrigin
public class EduCommentController {

    @Autowired
    private EduCommentService commentService;

    @Autowired
    private UcenterClient ucenter;

    //根据课程id分页查询课程评论
    @GetMapping("getCommentPage/{page}/{limit}/{courseId}")
    public R getCommentPage(@PathVariable long page,@PathVariable long limit,@PathVariable String courseId){
        Map<String,Object> map = commentService.getCommentInfo(page,limit,courseId);
        return R.ok().data(map);
    }

    //添加评论
    @PostMapping("addComment")
    public R addComment(HttpServletRequest request, @RequestBody EduComment comment){
        boolean flag = commentService.addComment(request, comment);
        if(flag){
            return R.ok().message("评论添加成功!");
        }else {
            return R.error().message("评论添加失败!");
        }

    }



}

