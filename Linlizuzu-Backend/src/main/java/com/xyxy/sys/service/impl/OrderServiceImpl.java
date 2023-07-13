package com.xyxy.sys.service.impl;

import com.xyxy.sys.entity.Order;
import com.xyxy.sys.mapper.OrderMapper;
import com.xyxy.sys.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 吴沈涛
 * @since 2023-06-01
 */


@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Resource
    private OrderMapper orderMapper;

    @Override
    public Integer getCountByUserName(String username) {
        return orderMapper.getCountByUserName(username);
    }

    @Override
    public Integer getCountByRenterName(String username) {
        return orderMapper.getCountByRenterName(username);
    }

    @Override
    public void updateOrderStatus(int id) {
        orderMapper.updateOrderStatus(id);
    }

    @Override
    public void changeOrderStatus(Integer status, Integer id) {
        orderMapper.changeOrderStatus(status,id);
    }
}
