package com.rg.smsservice.service;

import java.util.Map;

/**
 * @author lxy
 * @version 1.0
 * @Description
 * @date 2022/3/8 14:47
 */
public interface SmsService {
    boolean sendMsgAly(Map<String, Object> param, String phone);

    boolean sendMsgGyy(String code, String phone);
}
