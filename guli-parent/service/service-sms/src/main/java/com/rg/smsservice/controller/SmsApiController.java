package com.rg.smsservice.controller;

import com.rg.commonutils.R;
import com.rg.smsservice.utils.RandomUtil;
import com.rg.smsservice.service.SmsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author lxy
 * @version 1.0
 * @Description
 * @date 2022/3/8 14:47
 */
@RestController
@RequestMapping("/smsservice/sms")
@CrossOrigin
public class SmsApiController {


    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private SmsService smsService;

    //阿里云发送短信
    @GetMapping("sendMsgByAly/{phone}")
    public R sendMsgByAly(@PathVariable String phone){
        //生成随机值,传递到阿里云进行发送
        String code = RandomUtil.getSixBitRandom();
        Map <String, Object> param = new HashMap <>();
        param.put("code", code);
        //调用service进行短信发送
        boolean isSend = smsService.sendMsgAly(param, phone);
        if(isSend){
            return R.ok();
        }else{
            return R.error().message("短信发送失败");
        }
    }

    //使用国阳云发送短信
    @GetMapping("sendMsgByGyy/{phone}")
    public R sendMsgByGyy(@PathVariable String phone){
        //从redis中获取验证码,如果获取则直接返回 根据现实情况,用户可以多次发送.
        // String code = redisTemplate.opsForValue().get(phone);
        // if(!StringUtils.isEmpty(code)){
        //     return R.ok();
        // }
        //2.如果redis获取不到,进行阿里云发送

        //生成随机值,传递到阿里云进行发送
        String code = RandomUtil.getSixBitRandom();
        //调用service进行短信发送
        boolean isSend = smsService.sendMsgGyy(code, phone);
        if(isSend){
            //发送成功,把发送成功验证码放入到redis中
            //设置有效时间
            redisTemplate.opsForValue().set(phone, code, 30, TimeUnit.MINUTES);
            return R.ok();
        }else{
            return R.error().message("短信发送失败");
        }
    }
}
