import request from '@/utils/request'

export default {
    //根据视频id获取视频凭证
    getPlayAuth(vid){
        return request({
            url:`/vodService/video/getPlayAuth/${vid}`,
            mehtod:'get'
        })
    },
}