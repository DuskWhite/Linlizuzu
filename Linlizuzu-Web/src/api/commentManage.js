import request from '@/utils/request'
export default {
    getGoodNameById(goodId) {
        return request({
            url: `/comment/changeGood`,
            method: 'get',
            params: {goodId}
        })
    },
    getUserNameById(userId) {
        return request({
            url: `/comment/changeUser`,
            method: 'get',
            params: {userId}
        })
    },
    getCommentList(id) {
        return request({
            url: `/comment/${id}`,
            method: 'get'
        })
    },
    addComment(comment) {
        return request({
            url: '/comment',
            method: 'post',
            data: comment
        });
    },
    getCommentInfo(goodname, ownername, rentername) {
        return request({
            url: '/comment/getAllIdByName',
            method: 'get',
            params: { goodname, ownername, rentername }
        });
    },
    addReputation(score, id) {
        return request({
            url: '/comment/score',
            method: 'put',
            params: { score, id }
        })
    }
}