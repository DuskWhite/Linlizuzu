package com.xyxy.sys.controller;

import com.xyxy.common.vo.Result;
import com.xyxy.sys.entity.Order;
import com.xyxy.sys.service.IOrderService;
import com.xyxy.sys.service.IScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
    @author 吴沈涛
 */
@Slf4j
@Component
public class ScheduleController {
    @Autowired
    private IScheduleService iScheduleService;

    @Autowired
    private IOrderService orderService;
/**     关于@Schedule注解参数
     fixedRate：计划任务的执行频率，以上一次的起始时间来计算下一次的起始时间，以毫秒为单位
     fixedDelay：计划任务的执行间隔，以上一次的结束时间来计算下一次的起始时间，以毫秒为单位
     cron：使用1个字符串作为值，此字符串是一个表达式，由6~7部分组成，各部分使用空格分隔
     -- 在cron中的配置值，各部分表示的意义，从左至右分别是：秒 分 时 日 月 周 [年]
     -- 各部分值都可以使用通配符
     -- 使用星号作为通配符：表示任意值
     -- 使用问号作为通配符：表示不关心此值，只能用于“日”和“周”
     -- 例如："56 34 12 13 4 ? 2023" 表示 >> 2023年4月13日 12:34:56执行此计划任务，无视当天星期几*/
    @Scheduled(fixedRate = 5 * 60 * 1000)
    public void checkOrder() {
        log.debug("检测订单超时");
        List<Order>list = orderService.list();
        iScheduleService.checkOrder(list);
    }

    @Scheduled(fixedDelay = 60 * 60 * 1000)
    public void updateReputation() {
        log.debug("扣除超时用户信誉分");
        List<Order>list = orderService.list();
        iScheduleService.updateReputation(list);
    }

}
