import request from '@/utils/request'

export default {
  getorderList(searchModel) {
    return request({
      url: '/order/list',
      method: 'get',
      params: {
        pageNo: searchModel.pageNo,
        pageSize: searchModel.pageSize,
        ownerName: searchModel.ownerName,
        orderNo: searchModel.orderNo,
        renterName: searchModel.renterName,
      }
    })
  },
  getorderListByOwner(searchModel) {
    return request({
      url: '/order/list',
      method: 'get',
      params: {
        pageNo: searchModel.pageNo,
        pageSize: searchModel.pageSize,
        ownerName: searchModel.ownerName,
      }
    })
  },
  // 根据出租者查询
  getorderListByRenter(searchModel) {
    return request({
      url: '/order/list',
      method: 'get',
      params: {
        pageNo: searchModel.pageNo,
        pageSize: searchModel.pageSize,
        renterName: searchModel.renterName
      }
    })
  },
  addorder(order) {
    return request({
      url: '/order',
      method: 'post',
      data: order
    })
  },
  updateorder(order) {
    return request({
      url: '/order',
      method: 'put',
      data: order
    })
  },
  changeOrderStatus(status,id){
    return request({
      url:'/order/changeOrderStatus',
      method:'put',
      params:{
        status,
        id
      }
    })
  },
  saveorder(order){
    if(order.orderId == null && order.orderId == undefined){
      return this.addorder(order)
    }
      return this.updateorder(order)
  },
  getorderById(id) {
    return request({
      url: `/order/${id}`,
      method: 'get'
    })
  },
  getCountByRenterName(username) {
    return request({
      url: `/order/Rentercount/${username}`,
      method: 'get'
    })
  },
  getCountByUserName(username) {
    return request({
      url: `/order/count/${username}`,
      method: 'get'
    })
  },
  deleteorderById(id) {
    return request({
      url: `/order/${id}`,
      method: 'delete'
    })
  }
}
