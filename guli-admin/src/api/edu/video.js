import request from '@/utils/request'

export default {
    //添加小节
    addVideo(video) {
        return request({
            url: `/eduservice/video/addVideo`,
            method: 'post',
            data: video
        })
    },
    //修改小节
    updateVideo(video) {
        return request({
            url: `/eduservice/video/updateVideo`,
            method: 'put',
            data: video
        })
    },
    //删除小节
    delVideo(id) {
        return request({
            url: `/eduservice/video/delVideo/${id}`,
            method: 'delete'
        })
    },
    //查询小节通过id
    getVideo(id) {
        return request({
            url: `/eduservice/video/getVideo/${id}`,
            method: 'get'
        })
    },
    //根据id删除视频
    removeAlyVideo(id) {
        return request({
            url: `/vodService/video/removeAlyVideo/${id}`,
            method: 'delete'
        })
    }
}