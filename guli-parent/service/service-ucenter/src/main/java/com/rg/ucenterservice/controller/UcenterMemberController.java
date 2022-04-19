package com.rg.ucenterservice.controller;


import com.rg.commonutils.JwtUtils;
import com.rg.commonutils.R;
import com.rg.commonutils.vo.UcenterMemberOrder;
import com.rg.ucenterservice.entity.UcenterMember;
import com.rg.ucenterservice.entity.vo.LoginVo;
import com.rg.ucenterservice.entity.vo.RegisterVo;
import com.rg.ucenterservice.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author lxy
 * @since 2022-03-08
 */
@RestController
@RequestMapping("/ucenterservice/member")
@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;

    //用户登录
    @PostMapping("login")
    public R login(@RequestBody LoginVo loginVo){
        //返回token,使用jwt生成
        String token = memberService.login(loginVo);
        return R.ok().data("token",token);
    }

    //用户注册
    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo){

        R result = memberService.register(registerVo);
        return result;
    }

    //根据token获取用户信息
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember member = memberService.getById(memberId);
        return R.ok().data("userInfo", member);
    }

    //根据会员id,查询会员基本信息
    @GetMapping("getMemberInfoById/{memberId}")
    public Map <String,Object> getMemberInfoById(@PathVariable("memberId") String memberId){
        UcenterMember member = memberService.getById(memberId);
        Map <String, Object> map = new HashMap <>();
        map.put("nickname",member.getNickname());
        map.put("avatar",member.getAvatar());
        return map;
    }

    @GetMapping("getUserInfoOrder/{memberId}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable("memberId") String memberId){
        UcenterMember member = memberService.getById(memberId);
        UcenterMemberOrder memberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(member,memberOrder);
        return memberOrder;
    }



}

