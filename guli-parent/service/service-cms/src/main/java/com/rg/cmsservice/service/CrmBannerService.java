package com.rg.cmsservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rg.cmsservice.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rg.cmsservice.entity.vo.BannerQuery;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author lxy
 * @since 2022-03-01
 */
public interface CrmBannerService extends IService<CrmBanner> {

    List<CrmBanner> findAll();


    //分页查询带条件
    Page<CrmBanner> pageQuery(Long current, Long limit, BannerQuery bannerQuery);

}
