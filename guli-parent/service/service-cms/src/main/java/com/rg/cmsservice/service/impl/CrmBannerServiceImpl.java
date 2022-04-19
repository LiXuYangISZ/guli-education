package com.rg.cmsservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rg.cmsservice.entity.CrmBanner;
import com.rg.cmsservice.entity.vo.BannerQuery;
import com.rg.cmsservice.mapper.CrmBannerMapper;
import com.rg.cmsservice.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author lxy
 * @since 2022-03-01
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    //查询所有banner
    @Override
    public List <CrmBanner> findAll() {
        //根据sort排列,显示前两条记录
        QueryWrapper <CrmBanner> wrapper = new QueryWrapper <>();
        wrapper.orderByDesc("sort").last("limit 2");//last:拼接sql语句
        List <CrmBanner> bannerList = this.baseMapper.selectList(wrapper);
        return bannerList;
    }


    //分页查询带条件
    @Override
    public Page <CrmBanner> pageQuery(Long current, Long limit, BannerQuery bannerQuery) {
        Page <CrmBanner> page = new Page <>(current, limit);
        QueryWrapper <CrmBanner> wrapper = new QueryWrapper <>();
        String title = bannerQuery.getTitle();
        String linkUrl = bannerQuery.getLinkUrl();
        String begin = bannerQuery.getBegin();
        String end = bannerQuery.getEnd();

        if(!StringUtils.isEmpty(title)){
            wrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(linkUrl)){
            wrapper.like("link_url",linkUrl);
        }

        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create", begin);
        }

        if(!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create", end);
        }
        //按照sort降序
        wrapper.orderByDesc("sort");

        this.baseMapper.selectPage(page, wrapper);
        return page;
    }
}
