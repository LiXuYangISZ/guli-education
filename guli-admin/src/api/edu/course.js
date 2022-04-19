import request from '@/utils/request'

export default {
    //1.添加课程信息
    saveCourseInfo(courseInfo) {
        return request({
            url: `/eduservice/course/saveCourseInfo`,
            method: 'post',
            data: courseInfo
        })
    },
    //查询所有讲师
    getTeacherList() {
        return request({
            url: `/eduservice/teacher/findAll`,
            method: 'get'
        })
    },
    //根据id查询课程信息
    getCourseInfo(courseId) {
        return request({
            url: `/eduservice/course/getCourseInfo/${courseId}`,
            method: 'get'
        })
    },
    //根据id修改课程信息
    updateCourseInfo(courseInfoVo) {
        return request({
            url: `/eduservice/course/updateCourseInfo`,
            method: 'put',
            data: courseInfoVo
        })
    },
    //根据课程id查询课程发布的基本信息
    getPublishCourseInfo(id) {
        return request({
            url: `/eduservice/course/getPublishCourseInfo/${id}`,
            method: 'get'
        })
    },
    //发布课程信息
    publishCourse(id) {
        return request({
            url: `/eduservice/course/publishCourse/${id}`,
            method: 'put'
        })
    },
    //删除课程
    removeCourse(id) {
        return request({
            url: `/eduservice/course/removeCourse/${id}`,
            method: 'delete'
        })
    },
    //分页查询课程
    pageQuery(current, limit, courseQuery) {
        return request({
            url: `/eduservice/course/pageQuery/${current}/${limit}`,
            method: 'post',
            data: courseQuery
        })
    }

}