package com.rg.ucenterservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rg.commonutils.JwtUtils;
import com.rg.commonutils.R;
import com.rg.servicebase.handler.GuLiException;
import com.rg.ucenterservice.entity.UcenterMember;
import com.rg.ucenterservice.entity.vo.LoginVo;
import com.rg.ucenterservice.entity.vo.RegisterVo;
import com.rg.ucenterservice.mapper.UcenterMemberMapper;
import com.rg.ucenterservice.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rg.ucenterservice.utils.MD5Utils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author lxy
 * @since 2022-03-08
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    //用户登录
    @Override
    public String login(LoginVo loginVo) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        //判断用户名密码,是否为空
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw new GuLiException(20001, "用户名或密码不能为空!");
        }
        //去数据库查询
        QueryWrapper <UcenterMember> wrapper = new QueryWrapper <>();
        wrapper.eq("mobile",mobile);
        UcenterMember ucenterMember = this.baseMapper.selectOne(wrapper);

        //判断UCenterMember是否为空
        if(ucenterMember==null){//没有这个手机号
            throw new GuLiException(20001,"用户名或密码错误!");
        }

        //判断密码是否相等
        if(!MD5Utils.md5(password).equals(ucenterMember.getPassword())){
            throw new GuLiException(20001,"用户名或密码错误!");
        }

        //判断是否被禁用
        if(ucenterMember.getIsDisabled()){
            throw new GuLiException(20001, "您的账号暂时被冻结,请联系管理员!");
        }

        //登录成功
        //生成token字符串,使用jwt工具类
        String jwtToken = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getMobile());
        return jwtToken;
    }

    //用户注册
    @Override
    public R register(RegisterVo registerVo) {
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();

        //判断是否为空
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) || StringUtils.isEmpty(code) || StringUtils.isEmpty(nickname)){
            return R.error().message("数据格式不正确!!");
        }
        //判断验证码是否正确
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if(!code.equals(redisCode)){
            return R.error().message("验证码无效或已过期,请重新发送验证码!");
        }
        //判断手机号是否已经注册
        QueryWrapper <UcenterMember> wrapper = new QueryWrapper <>();
        wrapper.eq("mobile",mobile);
        Integer count = this.baseMapper.selectCount(wrapper);
        if(count > 0){
            return R.error().message("手机号已被注册!");
        }

        //判断昵称是否重复
        wrapper = new QueryWrapper <UcenterMember>();
        wrapper.eq("nickname",nickname);
        count = this.baseMapper.selectCount(wrapper);
        if(count > 0){
            return R.error().message("昵称 昵称已被使用,换一个吧");
        }
        //进行数据插入
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5Utils.md5(password));
        member.setIsDeleted(false);//用户不禁用
        member.setAvatar("https://guli-photos.oss-cn-hangzhou.aliyuncs.com/FatDog.jpg");
        this.baseMapper.insert(member);
        return R.ok();
    }


    //根据openid获取用户信息
    @Override
    public UcenterMember getOpenIdMember(String openid) {
        QueryWrapper <UcenterMember> wrapper = new QueryWrapper <>();
        wrapper.eq("openid", openid);
        UcenterMember member = this.baseMapper.selectOne(wrapper);
        return member;
    }
}
