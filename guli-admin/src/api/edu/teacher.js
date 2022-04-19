import request from '@/utils/request'

export default {
    //查询讲师列表(分页) current:当前页  limit:每页记录数 teacherQuery:条件对象
    getTeacherListPage(current, limit, teacherQuery) {
        return request({
            // url: '/eduservice/teacher/pageQuery/'+current+'/'+limit,
            url: `/eduservice/teacher/pageQuery/${current}/${limit}`,
            method: 'post',
            //teacherQuery条件对象,后端使用RequestBody获取对象
            //data表示把对象转为json进行传递到接口里面
            data: teacherQuery
        })
    },
    //删除讲师
    removeById(id) {
        return request({
            url: `/eduservice/teacher/removeById/${id}`,
            method: 'delete'
        })
    },
    //添加
    addTeacher(teacher) {
        return request({
            url: `/eduservice/teacher/addTeacher`,
            method: 'post',
            //data表示把对象转为json进行传递到接口里面
            data: teacher
        })
    },
    //通过id获取用户信息(用于修改用户信息前的数据回显操作)
    getById(id) {
        return request({
            url: `/eduservice/teacher/getById/${id}`,
            method: 'get',
        })
    },
    //根据id修改讲师
    updateById(teacher) {
        return request({
            url: `/eduservice/teacher/updateById`,
            method: 'put',
            data: teacher
        })
    }
}