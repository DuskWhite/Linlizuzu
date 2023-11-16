import request from '@/utils/request'

export default{
  getMessages(senderId,receiverId){
    return request({
      url: '/message/history',
      method: 'get',
      params:{
        senderId:senderId,
        receiverId:receiverId
      }
    });
  }
}