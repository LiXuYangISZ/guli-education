package com.rg.smsservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.rg.commonutils.MailUtils;
import com.rg.smsservice.service.SmsService;
import com.rg.smsservice.utils.ConstantPropertiesUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author lxy
 * @version 1.0
 * @Description
 * @date 2022/3/8 14:48
 */
@Service
public class SmsServiceImpl implements SmsService {
    //通过阿里云发送短信
    @Override
    public boolean sendMsgAly(Map <String, Object> param, String phone) {
        if(StringUtils.isEmpty(phone)) return false;

        DefaultProfile profile =
                DefaultProfile.getProfile("default", "LTAIq6nIPY09VROj", "FQ7UcixT9wEqMv9F35nORPqKr8XkTF");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "我的谷粒在线教育网站");
        request.putQueryParameter("TemplateCode", "templateCode");
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));

        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return response.getHttpResponse().isSuccess();
        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }

    //使用国阳云发送短信
    @Override
    public boolean sendMsgGyy(String code, String phone) {
        try {
            //真正的短信发送 用于上线

            // SmsUtil.sendMessage(ConstantPropertiesUtil.APP_CODE,phone,code,"30",ConstantPropertiesUtil.SMS_SIGNID,ConstantPropertiesUtil.TEMPLATE_ID);
            // 太贵了,先用邮箱代替.
            MailUtils.sendMail("2422737092@qq.com", "[谷粒学院] 验证码: \n"+code+"您正在进行注册,若非本人操作,请勿泄露.30分钟内有效.", "谷粒学院验证码");
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
