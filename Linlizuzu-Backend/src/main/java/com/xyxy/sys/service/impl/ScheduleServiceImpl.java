package com.xyxy.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xyxy.sys.entity.Order;
import com.xyxy.sys.entity.User;
import com.xyxy.sys.mapper.UserMapper;
import com.xyxy.sys.service.IOrderService;
import com.xyxy.sys.service.IScheduleService;
import com.xyxy.sys.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class ScheduleServiceImpl extends ServiceImpl<UserMapper, User> implements IScheduleService {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IUserService userService;

    @Override
    public void checkOrder(List<Order> List) {
        int count=0;
        for (Order order : List) {
            LocalDateTime overTime = order.getOverTime();
            LocalDateTime nowTime = LocalDateTime.now();
            LocalDateTime finalTime = overTime.plusDays(2);
            int status = order.getOrderStatus();
            int id = order.getOrderId();
            String renter = order.getRenterName();
//            当前时间超过订单结束时间，则改变订单状态
            if (nowTime.isAfter(overTime) & status == 1) {
                count += 1;
                orderService.updateOrderStatus(id);

            }
        }
        System.out.println("本次修改了"+count+"条订单");
    }

    @Override
    public void updateReputation(List<Order> List) {
        int count=0;
        for (Order order : List) {
            LocalDateTime overTime = order.getOverTime();
            LocalDateTime nowTime = LocalDateTime.now();
            LocalDateTime finalTime = overTime.plusDays(2);
            int status = order.getOrderStatus();
            String renter = order.getRenterName();
//            当前时间超过订单结束时间，则改变订单状态
//            当前时间已经超过订单时间结束时间两天并且仍未归还，则扣除信誉分
            if (nowTime.isAfter(finalTime) & status == 3){
                userService.updateReputation(renter);
            }
        }
        System.out.println("本次修改了"+count+"条信誉");
    }
}
