import request from '@/utils/request'

export default {
    //查询前两条banner数据
    findAllBanner(){
        return request({
            url:`/cmsservice/bannerfront/findAllBanner`,
            method:'get'
        })
    }
}