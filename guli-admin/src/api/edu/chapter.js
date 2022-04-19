import request from '@/utils/request'

export default {
    //根据课程id查询章节和小节
    getChapterVideo(courseId) {
        return request({
            url: `/eduservice/chapter/getChapterVideo/${courseId}`,
            method: 'get'
        })
    },
    //根据章节id查询章节.
    getChapter(chapterId) {
        return request({
            url: `/eduservice/chapter/getChapter/${chapterId}`,
            method: 'get'
        })
    },
    //根据id更新章节
    updateChapter(chapter) {
        return request({
            url: `/eduservice/chapter/updateChapter`,
            method: 'put',
            data: chapter
        })
    },
    //根据id删除章节
    delChapter(id) {
        return request({
            url: `/eduservice/chapter/delChapter/${id}`,
            method: 'delete'
        })
    },
    addChapter(chapter) {
        return request({
            url: `/eduservice/chapter/addChapter`,
            method: 'post',
            data: chapter
        })
    }

}