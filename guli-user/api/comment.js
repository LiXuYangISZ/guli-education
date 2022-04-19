import request from '@/utils/request'

export default {
    //通过课程id分页查询课程评论
    getCommentInfo(page,limit,courseId){
        return request({
            url:`/eduservice/comment/getCommentPage/${page}/${limit}/${courseId}`,
            method:'get'
        })
    },
    //添加课程评论
    addComment(comment){
        return request({
            url:`/eduservice/comment/addComment`,
            method:'post',
            data:comment
        })
    }
}