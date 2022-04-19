import request from '@/utils/request'

export default {
    //查询前两条banner数据
    findCourseAndTeacher(){
        return request({
            url:`/eduservice/index/findCourseAndTeacher`,
            method:'get'
        })
    }
}