package com.xyxy.sys.mapper;

import com.xyxy.sys.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 吴沈涛
 * @since 2023-06-01
 */
public interface OrderMapper extends BaseMapper<Order> {

    Integer getCountByUserName(String username);

    Integer getCountByRenterName(String username);

    void updateOrderStatus(int id);

    void changeOrderStatus(Integer status,Integer id);
}
