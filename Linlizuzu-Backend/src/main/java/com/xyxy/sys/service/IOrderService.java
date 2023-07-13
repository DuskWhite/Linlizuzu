package com.xyxy.sys.service;

import com.xyxy.sys.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 吴沈涛
 * @since 2023-06-01
 */
public interface IOrderService extends IService<Order> {

    Integer getCountByUserName(String username);

    Integer getCountByRenterName(String username);

    void updateOrderStatus(int id);

    void changeOrderStatus(Integer stauts,Integer id);
}
