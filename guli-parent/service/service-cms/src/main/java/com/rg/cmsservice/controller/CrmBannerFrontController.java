package com.rg.cmsservice.controller;




import com.rg.cmsservice.entity.CrmBanner;
import com.rg.cmsservice.service.CrmBannerService;
import com.rg.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author lxy
 * @since 2022-03-01
 */
@RestController
@RequestMapping("/cmsservice/bannerfront")
@CrossOrigin
public class CrmBannerFrontController {


    @Autowired
    private CrmBannerService bannerService;

    //查询所有轮播图
    @Cacheable(value = "banner",key="'bannerList'")
    @GetMapping("findAllBanner")
    public R findAllBanner(){
        List <CrmBanner> bannerList = bannerService.findAll();
        return R.ok().data("bannerList", bannerList);
    }


}

