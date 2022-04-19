import request from '@/utils/request'

export default {
    //1.条件查询带分页查询课程
    getCourseList(page,limit,searchObj){
        return request({
            url:`/eduservice/coursefront/getFrontCourseList/${page}/${limit}`,
            method:'post',
            data:searchObj
        })
    },
    //2.查询所有课程分类
    getAllSubject(){
        return request({
            url:`/eduservice/subject/getAllSubject`,
            method:'get'
        })
    },
    //3.课程课程基本详情的方法
    getCourseInfo(courseId){
        return request({
            url:`/eduservice/coursefront/getFrontCourseInfo/${courseId}`,
            method:'get'
        })
    }
}