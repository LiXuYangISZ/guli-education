package com.rg.ucenterservice.service;

import com.rg.commonutils.R;
import com.rg.ucenterservice.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rg.ucenterservice.entity.vo.LoginVo;
import com.rg.ucenterservice.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author lxy
 * @since 2022-03-08
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(LoginVo loginVo);

    R register(RegisterVo registerVo);

    UcenterMember getOpenIdMember(String openid);
}
