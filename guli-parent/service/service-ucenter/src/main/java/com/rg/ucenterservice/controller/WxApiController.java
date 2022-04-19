package com.rg.ucenterservice.controller;

import com.google.gson.Gson;
import com.rg.commonutils.JwtUtils;
import com.rg.servicebase.handler.GuLiException;
import com.rg.ucenterservice.entity.UcenterMember;
import com.rg.ucenterservice.service.UcenterMemberService;
import com.rg.ucenterservice.utils.ConstantWxUtils;
import com.rg.ucenterservice.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lxy
 * @version 1.0
 * @Description
 * @date 2022/3/10 22:22
 */
@CrossOrigin
@Controller
@RequestMapping("/api/ucenter/wx")
public class WxApiController {

    @Autowired
    private UcenterMemberService memberService;

    //1.生成微信扫描二维码
    @GetMapping("login")
    public String getWxCode(HttpSession session) {

        //固定地址,后面直接拼接参数
        // String url = "https://open.weixin.qq.com/" +
        //              "connect/qrconnect?appid="+ ConstantWxUtils.WX_OPEN_APP_ID+"&response_type=code";

        //微信开放平台授权baseUrl %s相当于?代表占位符
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        //对redirect_rul进行URLEncode编码
        String redirectUrl = ConstantWxUtils.WX_OPEN_REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        String url = String.format(
                baseUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                redirectUrl,
                "atguigu"
        );

        return "redirect:" + url;
    }

    //2.调用扫描人信息,添加数据
    @GetMapping("callback")
    public String callback(String code, String state) {

        //1.获取code,临时票据,类似于验证码

        //2.拿着code请求 微信固定的地址,得到两个值 access_token和open_id
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";

        //拼接参数 id 秘钥  和code 值
        String accessTokenUrl = String.format(
                baseAccessTokenUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                ConstantWxUtils.WX_OPEN_APP_SECRET,
                code
        );

        //请求这个拼接好的地址,得到两个值 access_token和open_id
        //使用httpclient发送请求,得到返回结果

        String accessTokenInfo = null;

        try {
            accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuLiException(20001, "获取access_token失败");
        }
        // accessTokenInfo字符串获取两个值 access_token和openid
        // 方法:把accessTokenInfo字符串转成map集合,根据map里面key获取对应值
        // 使用json转换工具 Gson
        Gson gson = new Gson();
        Map mapAccessToken = gson.fromJson(accessTokenInfo, HashMap.class);
        String access_token = (String) mapAccessToken.get("access_token");
        String openid = (String) mapAccessToken.get("openid");


        //查询数据库中是否有该微信用户
        UcenterMember member = memberService.getOpenIdMember(openid);
        if (member == null) {

            //3.拿着得到的access_token和openid,再去请求微信提供的固定地址,获取到扫描人的信息
            //访问微信的资源服务器，获取用户信息
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";
            String userInfoUrl = String.format(
                    baseUserInfoUrl,
                    access_token,
                    openid
            );

            //发送请求
            String userInfo = null;
            try {
                 userInfo= HttpClientUtils.get(userInfoUrl);
            }catch (Exception e){
                e.printStackTrace();
                throw new GuLiException(20001, "获取用户信息失败");
            }


            Map mapUserInfo = gson.fromJson(userInfo, HashMap.class);
            String nickname = (String) mapUserInfo.get("nickname");
            //Java中如何将Double转为Integer  gson默认会将integer转为double对象.
            Double sexDouble = (Double) mapUserInfo.get("sex");
            Integer sex = sexDouble.intValue();
            String headImgUrl = (String) mapUserInfo.get("headimgurl");


            //向数据库中插入一条用户数据
            member = new UcenterMember();
            member.setOpenid(openid);
            member.setNickname(nickname);
            member.setAvatar(headImgUrl);
            member.setSex(sex);
            memberService.save(member);
        }

        //使用JWT根据member对象生成token字符串
        String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());

        //最后返回首页面
        return "redirect:http://localhost:3000?token=" + jwtToken;
    }
}
