package com.xyxy.sys.service;

import com.xyxy.sys.entity.Order;

import java.util.List;

public interface IScheduleService {


    void checkOrder(List<Order> List);

    void updateReputation(List<Order> list);
}
