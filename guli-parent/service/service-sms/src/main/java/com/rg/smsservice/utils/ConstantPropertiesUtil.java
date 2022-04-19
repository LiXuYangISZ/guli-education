package com.rg.smsservice.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author lxy
 * @version 1.0
 * @Description 国阳云短信服务相关配置
 * @date 2022/3/8 15:29
 */
@Component
public class ConstantPropertiesUtil implements InitializingBean {

    @Value("${gyyun.sms.appCode}")
    private String appCode;

    @Value("${gyyun.sms.smsSignId}")
    private String smsSignid;

    @Value("${gyyun.sms.templateId}")
    private String templateId;

    public static String APP_CODE;
    public static String SMS_SIGNID;
    public static String TEMPLATE_ID;

    @Override
    public void afterPropertiesSet() throws Exception {
        APP_CODE = appCode;
        SMS_SIGNID = smsSignid;
        TEMPLATE_ID = templateId;
    }
}
