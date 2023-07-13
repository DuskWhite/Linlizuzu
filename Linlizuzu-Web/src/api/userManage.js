import request from '@/utils/request'

export default {
  getUserList(searchModel) {
    return request({
      url: '/user/list',
      method: 'get',
      params: {
        pageNo: searchModel.pageNo,
        pageSize: searchModel.pageSize,
        username: searchModel.username,
        phone: searchModel.phone
      }
    })
  },
  addUser(user) {
    return request({
      url: '/user',
      method: 'post',
      data: user
    })
  },
  registerUser(user) {
    return request({
      url: '/user/register',
      method: 'post',
      data: user
    })
  },
  updateUser(user) {
    return request({
      url: '/user',
      method: 'put',
      data: user
    })
  },
  updatePassword(user) {
    return request({
      url: '/user/password',
      method: 'put',
      data: user
    })
  },
  updateAvatar(user) {
    return request({
      url: '/user/avatar',
      method: 'put',
      data: user
    })
  },
  getUserAvatarByName(username){
    return request({
      url: `/user/name/${username}`,
      method: 'get'
    })
  },
  getUserByName(username){
    return request({
      url: `/user/username/${username}`,
      method: 'get'
    })
  },
  updateUserBalance(username,changenum) {
    return request({
      url: '/user/addbalance',
      method: 'put',
      params: {username,changenum}
    })
  },
  updateGoodBalance(rentername,ownername,changenum) {
    return request({
      url: '/user/balance',
      method: 'put',
      params: {
        rentername,
        ownername,
        changenum
      }  
    })
  },
  saveUser(user){
    if(user.id == null && user.id == undefined){
      return this.addUser(user)
    }
      return this.updateUser(user)
  },
  getUserById(id) {
    return request({
      url: `/user/${id}`,
      method: 'get'
    })
  },
  deleteUserById(id) {
    return request({
      url: `/user/${id}`,
      method: 'delete'
    })
  }
}
