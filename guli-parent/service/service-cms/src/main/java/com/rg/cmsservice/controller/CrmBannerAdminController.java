package com.rg.cmsservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rg.cmsservice.entity.CrmBanner;
import com.rg.cmsservice.entity.vo.BannerQuery;
import com.rg.cmsservice.service.CrmBannerService;
import com.rg.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author lxy
 * @since 2022-03-01
 */
@RestController
@RequestMapping("/cmsservice/banneradmin")
@CrossOrigin
public class CrmBannerAdminController {
    @Autowired
    private CrmBannerService bannerService;

    //查询轮播图列表
    @PostMapping("pageQuery/{current}/{limit}")
    public R pageQuery(@PathVariable("current") Long current, @PathVariable("limit") Long limit,@RequestBody BannerQuery bannerQuery ){
        Page <CrmBanner> page = bannerService.pageQuery(current,limit,bannerQuery);
        System.out.println(page.getTotal());
        System.out.println(page.getSize());
        System.out.println(page);
        return R.ok().data("items",page.getRecords()).data("total",page.getTotal());
    }

    //根据id获取
    @GetMapping("getById/{id}")
    public R getById(@PathVariable("id") String id){
        CrmBanner banner = bannerService.getById(id);
        return R.ok().data("banner",banner);
    }

    //增加一张轮播图
    @PostMapping("addBanner")
    @CacheEvict(value = "banner", allEntries=true)
    public R addBanner(@RequestBody CrmBanner banner){
        boolean flag = bannerService.save(banner);
        if(flag){
            return R.ok();
        }else{
            return R.error();
        }
    }

    //修改轮播图
    @PutMapping("editBanner")
    @CacheEvict(value = "banner", allEntries=true)  //当进行增删改时,清空banner缓存
    public R editBanner(@RequestBody CrmBanner banner){
        boolean flag = bannerService.updateById(banner);
        if(flag){
            return R.ok();
        }else{
            return R.error();
        }
    }

    //删除轮播图
    @DeleteMapping("removeBanner/{id}")
    @CacheEvict(value = "banner", allEntries=true)
    public R removeBanner(@PathVariable("id") String id){
        boolean flag = bannerService.removeById(id);
        if(flag){
            return R.ok();
        }else{
            return R.error();
        }
    }

}

