package com.rg.eduservice.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rg.commonutils.JwtUtils;
import com.rg.commonutils.R;
import com.rg.eduservice.client.ucenter.UcenterClient;
import com.rg.eduservice.entity.EduComment;
import com.rg.eduservice.entity.vo.CommentVo;
import com.rg.eduservice.mapper.EduCommentMapper;
import com.rg.eduservice.service.EduCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rg.servicebase.handler.GuLiException;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author lxy
 * @since 2022-03-15
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {

    @Autowired
    private UcenterClient ucenter;

    //根据课程id,分页查询课程评论
    @Override
    public Map <String,Object> getCommentInfo(long page, long limit, String courseId) {
        //查询
        QueryWrapper <EduComment> wrapper = new QueryWrapper <>();
        //判断course是否为空
        if(StringUtils.isEmpty(courseId)){
            throw new GuLiException(20001, "课程id为空!");
        }else{
            wrapper.eq("course_id", courseId);
        }
        Page <EduComment> commentPage = new Page <>(page,limit);

        wrapper.orderByDesc("gmt_create");
        this.baseMapper.selectPage(commentPage,wrapper);

        List <EduComment> records = commentPage.getRecords();
        // 将List <EduComment>转换成 List <CommentVo>进行返回
        List<CommentVo> commentVoList  = new ArrayList <CommentVo>();

        for (EduComment record : records) {
            CommentVo commentVo = new CommentVo();
            String memberId = record.getMemberId();
            Map<String,Object> map= ucenter.getMemberInfoById(memberId);
            String nickname = (String) map.get("nickname");
            String avatar = (String) map.get("avatar");
            commentVo.setNickname(nickname);
            commentVo.setAvatar(avatar);
            try {
                BeanUtils.copyProperties(commentVo,record);
            } catch (Exception e) {
                e.printStackTrace();
            }
            commentVoList.add(commentVo);
        }
        System.out.println(commentVoList);
        long current = commentPage.getCurrent();
        long pages = commentPage.getPages();
        long size = commentPage.getSize();
        long total = commentPage.getTotal();
        boolean hasNext = commentPage.hasNext();
        boolean hasPrevious = commentPage.hasPrevious();

        Map <String, Object> map = new HashMap <>();
        map.put("items", commentVoList);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    //添加评论
    @Override
    public boolean addComment(HttpServletRequest request,EduComment comment) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if(StringUtils.isEmpty(memberId)){
            throw new GuLiException(20001, "清先登录再进行评论!");
        }
        comment.setMemberId(memberId);
        int insert = this.baseMapper.insert(comment);
        return insert > 0;
    }
}
