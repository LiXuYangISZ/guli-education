import request from '@/utils/request'

export default {
    //分页查询banner
    pageQuery(current,limit,bannerQuery) {
        return request({
            url: `/cmsservice/banneradmin/pageQuery/${current}/${limit}`,
            method: 'post',
            data:bannerQuery
        })
    },
    //添加banner
    addBanner(banner){
        return request({
            url:`/cmsservice/banneradmin/addBanner`,
            method:`post`,
            data:banner
        })
    },
    //修改banner
    editBanner(banner){
        return request({
            url:`/cmsservice/banneradmin/editBanner`,
            method:`put`,
            data:banner
        })
    },
    //删除banner
    removeBanner(id){
        return request({
            url:`/cmsservice/banneradmin/removeBanner/${id}`,
            method:`delete`
        })
    },
    //通过id进行查询
    getById(id){
        return request({
            url:`/cmsservice/banneradmin/getById/${id}`,
            method:'get'
        })
    }
}