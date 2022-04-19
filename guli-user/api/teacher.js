import request from '@/utils/request'

export default {
    //分页查询讲师的方法
    getTeacheList(page,limit){
        return request({
            url:`/eduservice/teacherfront/getTeacherFrontList/${page}/${limit}`,
            mehtod:'get'
        })
    },

    //根据id获取讲师
    getTeacherInfo(teacherId){
        return request({
            url:`/eduservice/teacherfront/getTeacherFrontInfo/${teacherId}`,
            mehtod:'get'
        })
    }

}