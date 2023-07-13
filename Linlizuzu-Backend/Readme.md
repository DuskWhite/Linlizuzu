# 邻里租租接口文档
[toc]

## 1	环境变量

### Inferred Url
| 参数名 | 字段值 |
| ------ | ------ |
|baseUrl|http://localhost:9999|


## 2	物品租赁平台接口文档

##### 说明
> 



##### 联系方式
- **联系人：**DuskWhite
- **邮箱：**1057160644@qq.com
- **网址：**/https://github.com/DuskWhite/Linlizuzu/

##### 文档版本
```
1.0
```


## 3	评论接口列表

## 3.1	addComment

> POST  /comment
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| content|string||false||
| goodId|int32||false||
| id|int32||false||
| orderId|int32||false||
| ownerId|int32||false||
| publishTime|string|yyyy-MM-dd HH:mm:ss|false||
| renterId|int32||false||
| score|int32||false||
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
| message|string||false||

##### 接口描述
> 




## 3.2	转换goodid为name

> GET  /comment/changeGood
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|goodId||goodId|
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
| message|string||false||

##### 接口描述
> 




## 3.3	转换userid为name

> GET  /comment/changeUser
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|userId||userId|
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
| message|string||false||

##### 接口描述
> 




## 3.4	getCommentInfo

> GET  /comment/getAllIdByName
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|goodname||goodname|
|ownername||ownername|
|rentername||rentername|
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
| message|string||false||

##### 接口描述
> 




## 3.5	根据评价星级添加信誉分

> PUT  /comment/score
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|score||score|
|id||id|
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
| message|string||false||

##### 接口描述
> 




## 3.6	根据被评价用户id获取所有评价

> GET  /comment/{id}
### 地址参数（Path Variable）
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|id||id|
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
| message|string||false||

##### 接口描述
> 




## 4	Menu Controller

## 4.1	查询所有菜单数据

> GET  /menu
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|array[object]||false||
|⇥ children|array||false||
|⇥ component|string||false||
|⇥ hidden|boolean||false||
|⇥ icon|string||false||
|⇥ isLeaf|string||false||
|⇥ menuId|int32||false||
|⇥ meta|object||false||
|⇥ name|string||false||
|⇥ parentId|int32||false||
|⇥ path|string||false||
|⇥ redirect|string||false||
|⇥ title|string||false||
| message|string||false||

##### 接口描述
> 




## 5	个人空间接口列表

## 5.1	addFile

> POST  /zone/upload
### 响应体
● 200 响应数据格式：JSON

##### 接口描述
> 




## 6	用户接口列表

## 6.1	添加新用户

> POST  /user
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| avatar|string||false||
| balance|number||false||
| deleted|int32||false||
| email|string||false||
| id|int32||false||
| password|string||false||
| phone|string||false||
| reputation|int32||false||
| roleIdList|array[int32]||false||
| status|int32||false||
| username|string||false||
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
| message|string||false||

##### 接口描述
> 




## 6.2	修改用户信息

> PUT  /user
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| avatar|string||false||
| balance|number||false||
| deleted|int32||false||
| email|string||false||
| id|int32||false||
| password|string||false||
| phone|string||false||
| reputation|int32||false||
| roleIdList|array[int32]||false||
| status|int32||false||
| username|string||false||
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
| message|string||false||

##### 接口描述
> 




## 6.3	用户余额充值

> PUT  /user/addbalance
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|username||username|
|changenum||changenum|
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
| message|string||false||

##### 接口描述
> 




## 6.4	getAllUser

> GET  /user/all
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|array[object]||false||
|⇥ avatar|string||false||
|⇥ balance|number||false||
|⇥ deleted|int32||false||
|⇥ email|string||false||
|⇥ id|int32||false||
|⇥ password|string||false||
|⇥ phone|string||false||
|⇥ reputation|int32||false||
|⇥ roleIdList|array[int32]||false||
|⇥ status|int32||false||
|⇥ username|string||false||
| message|string||false||

##### 接口描述
> 




## 6.5	修改用户头像

> PUT  /user/avatar
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| avatar|string||false||
| balance|number||false||
| deleted|int32||false||
| email|string||false||
| id|int32||false||
| password|string||false||
| phone|string||false||
| reputation|int32||false||
| roleIdList|array[int32]||false||
| status|int32||false||
| username|string||false||
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
| message|string||false||

##### 接口描述
> 




## 6.6	订单生成时余额充值与扣除

> PUT  /user/balance
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|rentername||rentername|
|ownername||ownername|
|changenum||changenum|
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
| message|string||false||

##### 接口描述
> 




## 6.7	getUserInfo

> GET  /user/info
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|token||token|
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
| message|string||false||

##### 接口描述
> 




## 6.8	列表查询

> GET  /user/list
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|username||username|
|phone||phone|
|pageNo||pageNo|
|pageSize||pageSize|
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
| message|string||false||

##### 接口描述
> 




## 6.9	用户登录

> POST  /user/login
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| avatar|string||false||
| balance|number||false||
| deleted|int32||false||
| email|string||false||
| id|int32||false||
| password|string||false||
| phone|string||false||
| reputation|int32||false||
| roleIdList|array[int32]||false||
| status|int32||false||
| username|string||false||
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
| message|string||false||

##### 接口描述
> 




## 6.10	用户注销

> POST  /user/logout
### 请求头
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|X-Token||X-Token|
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
| message|string||false||

##### 接口描述
> 




## 6.11	获取商品主人的用户头像

> GET  /user/name/{username}
### 地址参数（Path Variable）
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|username||username|
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|string||false||
| message|string||false||

##### 接口描述
> 




## 6.12	修改用户密码

> PUT  /user/password
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| avatar|string||false||
| balance|number||false||
| deleted|int32||false||
| email|string||false||
| id|int32||false||
| password|string||false||
| passwordNew|string||false||
| passwordNow|string||false||
| passwordOld|string||false||
| passwordSure|string||false||
| phone|string||false||
| reputation|int32||false||
| roleIdList|array[int32]||false||
| status|int32||false||
| userName|string||false||
| username|string||false||
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
| message|string||false||

##### 接口描述
> 




## 6.13	用户注册

> POST  /user/register
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| avatar|string||false||
| balance|number||false||
| deleted|int32||false||
| email|string||false||
| id|int32||false||
| password|string||false||
| phone|string||false||
| reputation|int32||false||
| roleIdList|array[int32]||false||
| status|int32||false||
| username|string||false||
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
| message|string||false||

##### 接口描述
> 




## 6.14	用户名查询用户

> GET  /user/username/{username}
### 地址参数（Path Variable）
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|username||username|
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
|⇥ avatar|string||false||
|⇥ balance|number||false||
|⇥ deleted|int32||false||
|⇥ email|string||false||
|⇥ id|int32||false||
|⇥ password|string||false||
|⇥ phone|string||false||
|⇥ reputation|int32||false||
|⇥ roleIdList|array[int32]||false||
|⇥ status|int32||false||
|⇥ username|string||false||
| message|string||false||

##### 接口描述
> 




## 6.15	getUserById

> GET  /user/{id}
### 地址参数（Path Variable）
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|id||id|
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
|⇥ avatar|string||false||
|⇥ balance|number||false||
|⇥ deleted|int32||false||
|⇥ email|string||false||
|⇥ id|int32||false||
|⇥ password|string||false||
|⇥ phone|string||false||
|⇥ reputation|int32||false||
|⇥ roleIdList|array[int32]||false||
|⇥ status|int32||false||
|⇥ username|string||false||
| message|string||false||

##### 接口描述
> 




## 6.16	删除用户

> DELETE  /user/{id}
### 地址参数（Path Variable）
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|id||id|
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
|⇥ avatar|string||false||
|⇥ balance|number||false||
|⇥ deleted|int32||false||
|⇥ email|string||false||
|⇥ id|int32||false||
|⇥ password|string||false||
|⇥ phone|string||false||
|⇥ reputation|int32||false||
|⇥ roleIdList|array[int32]||false||
|⇥ status|int32||false||
|⇥ username|string||false||
| message|string||false||

##### 接口描述
> 




## 7	Order Controller

## 7.1	addOrder

> POST  /order
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| goodName|string||false||
| orderAmountTotal|number||false||
| orderId|int32||false||
| orderNo|string||false||
| orderStatus|int32||false||
| overTime|string|yyyy-MM-dd HH:mm:ss|false||
| ownerName|string||false||
| rentTime|string|yyyy-MM-dd HH:mm:ss|false||
| renterName|string||false||
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
| message|string||false||

##### 接口描述
> 




## 7.2	updateOrder

> PUT  /order
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| goodName|string||false||
| orderAmountTotal|number||false||
| orderId|int32||false||
| orderNo|string||false||
| orderStatus|int32||false||
| overTime|string|yyyy-MM-dd HH:mm:ss|false||
| ownerName|string||false||
| rentTime|string|yyyy-MM-dd HH:mm:ss|false||
| renterName|string||false||
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
| message|string||false||

##### 接口描述
> 




## 7.3	记录租借订单数量

> GET  /order/Rentercount/{username}
### 地址参数（Path Variable）
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|username||username|
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
| message|string||false||

##### 接口描述
> 




## 7.4	getAllOrder

> GET  /order/all
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|array[object]||false||
|⇥ goodName|string||false||
|⇥ orderAmountTotal|number||false||
|⇥ orderId|int32||false||
|⇥ orderNo|string||false||
|⇥ orderStatus|int32||false||
|⇥ overTime|string|yyyy-MM-dd HH:mm:ss|false||
|⇥ ownerName|string||false||
|⇥ rentTime|string|yyyy-MM-dd HH:mm:ss|false||
|⇥ renterName|string||false||
| message|string||false||

##### 接口描述
> 




## 7.5	修改订单状态

> PUT  /order/changeOrderStatus
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|status||status|
|id||id|
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
| message|string||false||

##### 接口描述
> 




## 7.6	记录出租订单数量

> GET  /order/count/{username}
### 地址参数（Path Variable）
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|username||username|
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
| message|string||false||

##### 接口描述
> 




## 7.7	getOrderList

> GET  /order/list
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|orderNo||orderNo|
|renterName||renterName|
|ownerName||ownerName|
|pageNo||pageNo|
|pageSize||pageSize|
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
| message|string||false||

##### 接口描述
> 




## 7.8	getOrderById

> GET  /order/{orderId}
### 地址参数（Path Variable）
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|orderId||orderId|
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
|⇥ goodName|string||false||
|⇥ orderAmountTotal|number||false||
|⇥ orderId|int32||false||
|⇥ orderNo|string||false||
|⇥ orderStatus|int32||false||
|⇥ overTime|string|yyyy-MM-dd HH:mm:ss|false||
|⇥ ownerName|string||false||
|⇥ rentTime|string|yyyy-MM-dd HH:mm:ss|false||
|⇥ renterName|string||false||
| message|string||false||

##### 接口描述
> 




## 7.9	deleteOrderById

> DELETE  /order/{orderId}
### 地址参数（Path Variable）
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|orderId||orderId|
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
|⇥ goodName|string||false||
|⇥ orderAmountTotal|number||false||
|⇥ orderId|int32||false||
|⇥ orderNo|string||false||
|⇥ orderStatus|int32||false||
|⇥ overTime|string|yyyy-MM-dd HH:mm:ss|false||
|⇥ ownerName|string||false||
|⇥ rentTime|string|yyyy-MM-dd HH:mm:ss|false||
|⇥ renterName|string||false||
| message|string||false||

##### 接口描述
> 




## 8	Role Controller

## 8.1	addRole

> POST  /role
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| menuIdList|array[int32]||false||
| roleDesc|string||false||
| roleId|int32||false||
| roleName|string||false||
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
| message|string||false||

##### 接口描述
> 




## 8.2	updateRole

> PUT  /role
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| menuIdList|array[int32]||false||
| roleDesc|string||false||
| roleId|int32||false||
| roleName|string||false||
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
| message|string||false||

##### 接口描述
> 




## 8.3	getAllRole

> GET  /role/all
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|array[object]||false||
|⇥ menuIdList|array[int32]||false||
|⇥ roleDesc|string||false||
|⇥ roleId|int32||false||
|⇥ roleName|string||false||
| message|string||false||

##### 接口描述
> 




## 8.4	getRoleList

> GET  /role/list
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|roleName||roleName|
|pageNo||pageNo|
|pageSize||pageSize|
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
| message|string||false||

##### 接口描述
> 




## 8.5	getRoleById

> GET  /role/{id}
### 地址参数（Path Variable）
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|id||id|
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
|⇥ menuIdList|array[int32]||false||
|⇥ roleDesc|string||false||
|⇥ roleId|int32||false||
|⇥ roleName|string||false||
| message|string||false||

##### 接口描述
> 




## 8.6	deleteRoleById

> DELETE  /role/{id}
### 地址参数（Path Variable）
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|id||id|
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
|⇥ menuIdList|array[int32]||false||
|⇥ roleDesc|string||false||
|⇥ roleId|int32||false||
|⇥ roleName|string||false||
| message|string||false||

##### 接口描述
> 




## 9	商品接口列表

## 9.1	addGood

> POST  /good
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| deleted|int32||false||
| goodname|string||false||
| id|int32||false||
| img|string||false||
| price|number||false||
| status|int32||false||
| username|string||false||
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
| message|string||false||

##### 接口描述
> 




## 9.2	updateGood

> PUT  /good
### 请求体(Request Body)
| 参数名称 | 数据类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| deleted|int32||false||
| goodname|string||false||
| id|int32||false||
| img|string||false||
| price|number||false||
| status|int32||false||
| username|string||false||
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
| message|string||false||

##### 接口描述
> 




## 9.3	getMarketGoodList

> GET  /good/Marketlist
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|goodname||goodname|
|status||status|
|pageNo||pageNo|
|pageSize||pageSize|
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
| message|string||false||

##### 接口描述
> 




## 9.4	getAllGood

> GET  /good/all
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|array[object]||false||
|⇥ deleted|int32||false||
|⇥ goodname|string||false||
|⇥ id|int32||false||
|⇥ img|string||false||
|⇥ price|number||false||
|⇥ status|int32||false||
|⇥ username|string||false||
| message|string||false||

##### 接口描述
> 




## 9.5	getGoodList

> GET  /good/list
### 请求参数(Query Param)
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|goodname||goodname|
|username||username|
|pageNo||pageNo|
|pageSize||pageSize|
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
| message|string||false||

##### 接口描述
> 




## 9.6	getGoodById

> GET  /good/{id}
### 地址参数（Path Variable）
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|id||id|
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
|⇥ deleted|int32||false||
|⇥ goodname|string||false||
|⇥ id|int32||false||
|⇥ img|string||false||
|⇥ price|number||false||
|⇥ status|int32||false||
|⇥ username|string||false||
| message|string||false||

##### 接口描述
> 




## 9.7	deleteGoodById

> DELETE  /good/{id}
### 地址参数（Path Variable）
| 参数名称 | 默认值 | 描述 |
| ------ | ------ | ------ |
|id||id|
### 响应体
● 200 响应数据格式：JSON
| 参数名称 | 类型 | 默认值 | 不为空 | 描述 |
| ------ | ------ | ------ | ------ | ------ |
| code|int32||false||
| data|object||false||
|⇥ deleted|int32||false||
|⇥ goodname|string||false||
|⇥ id|int32||false||
|⇥ img|string||false||
|⇥ price|number||false||
|⇥ status|int32||false||
|⇥ username|string||false||
| message|string||false||

##### 接口描述
> 



