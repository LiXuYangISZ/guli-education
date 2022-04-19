package com.rg.eduservice.client.ucenter;

import com.rg.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

/**
 * @author lxy
 * @version 1.0
 * @Description
 * @date 2022/3/15 15:34
 */
@FeignClient(value = "service-ucenter",fallback = UcenterImpl.class)
@Component
public interface UcenterClient {

    //根据会员id,查询会员基本信息
    @GetMapping("/ucenterservice/member/getMemberInfoById/{memberId}")
    public Map <String,Object> getMemberInfoById(@PathVariable("memberId") String memberId);
}
