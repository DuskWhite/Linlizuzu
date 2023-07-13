import request from '@/utils/request'

export default {
  getMarketGoodList(searchModel) {
    return request({
      url: '/good/Marketlist',
      method: 'get',
      params: {
        pageNo: searchModel.pageNo,
        pageSize: searchModel.pageSize,
        goodname: searchModel.goodname,
        username: searchModel.username,
        status: searchModel.status
      }
    })
  },
  getGoodList(searchModel) {
    return request({
      url: '/good/list',
      method: 'get',
      params: {
        pageNo: searchModel.pageNo,
        pageSize: searchModel.pageSize,
        goodname: searchModel.goodname,
        username: searchModel.username,
      }
    })
  },
  getAllGood(){
    return request({
      url:'/good/all',
      method:'get',
    })
  },
  addGood(good) {
    return request({
      url: '/good',
      method: 'post',
      data: good
    })
  },
  updateGood(good) {
    return request({
      url: '/good',
      method: 'put',
      data: good
    })
  },
  saveGood(good){
    if(good.id == null && good.id == undefined){
      return this.addGood(good)
    }
      return this.updateGood(good)
  },
  getGoodById(id) {
    return request({
      url: `/good/${id}`,
      method: 'get'
    })
  },
  deleteGoodById(id) {
    return request({
      url: `/good/${id}`,
      method: 'delete'
    })
  }
}
