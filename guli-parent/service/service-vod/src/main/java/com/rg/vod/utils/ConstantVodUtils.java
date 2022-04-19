package com.rg.vod.utils;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author lxy
 * @version 1.0
 * @Description 常量类，读取配置文件application.yml中的配置
 * @date 2022/2/19 16:15
 */
@Component //把属性的设置交给Spring.
public class ConstantVodUtils implements InitializingBean {// InitializingBean:在初始化的时候,该类被执行.

    //从配置文件中读取值,赋值给这些属性
    //注意@value无法给静态属性注入值
    @Value("${aliyun.vod.file.keyid}")
    private String keyId;

    @Value("${aliyun.vod.file.keysecret}")
    private String keySecret;

    //定义公开静态方法
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;

    //当属性值设置完毕后执行该方法.
    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_KEY_ID = keyId;
        ACCESS_KEY_SECRET = keySecret;
    }
}
