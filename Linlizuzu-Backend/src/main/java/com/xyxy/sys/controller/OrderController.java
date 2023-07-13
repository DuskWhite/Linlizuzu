package com.xyxy.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xyxy.common.vo.Result;
import com.xyxy.sys.entity.Order;
import com.xyxy.sys.service.IOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 吴沈涛
 * @since 2023-05-31
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private IOrderService orderService;

    @GetMapping("/all")
    public Result<List<Order>> getAllOrder() {
        List<Order>list = orderService.list();
        return Result.success(list,"查询成功");
    }
    /** 设置需要查询的参数 */
    @GetMapping("/list")
    public Result<Map<String,Object>> getOrderList(@RequestParam(value = "orderNo",required = false) String orderno,
                                                  @RequestParam(value = "renterName",required = false) String rentername,
                                                  @RequestParam(value = "ownerName",required = false) String ownername,
                                                  @RequestParam(value = "pageNo") Long pageNo,
                                                  @RequestParam(value = "pageSize") Long pageSize) {
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasLength(orderno), Order::getOrderNo, orderno);
        wrapper.eq(StringUtils.hasLength(rentername), Order::getRenterName, rentername);
        wrapper.eq(StringUtils.hasLength(ownername), Order::getOwnerName, ownername);
        wrapper.orderByDesc(Order::getOrderId);
        Page<Order> page = new Page<>(pageNo, pageSize);
        orderService.page(page, wrapper);
        Map<String, Object> data = new HashMap<>(16);
        data.put("total", page.getTotal());
        data.put("rows", page.getRecords());
        return Result.success(data);
    }

    @PostMapping
    public Result<?> addOrder(@RequestBody Order order){
        orderService.save(order);
        return Result.success("新增订单成功");
    }

    @PutMapping
    public Result<?> updateOrder(@RequestBody Order order){
        orderService.updateById(order);
        return Result.success("修改订单信息成功");
    }
    /** 根据id查询订单信息 用于修改订单信息 */
    @GetMapping("/{orderId}")
    public Result<Order> getOrderById(@PathVariable("orderId") Integer id){
        Order order = orderService.getById(id);
        return Result.success(order);
    }

    @ApiOperation("记录出租订单数量")
    @GetMapping("count/{username}")
    public Result<?> getCountByUserName(@PathVariable("username") String username){
        Integer count = orderService.getCountByUserName(username);
        return Result.success(count);
    }

    @ApiOperation("记录租借订单数量")
    @GetMapping("Rentercount/{username}")
    public Result<?> getCountByRenterName(@PathVariable("username") String username){
        Integer count = orderService.getCountByRenterName(username);
        return Result.success(count);
    }

    @DeleteMapping("/{orderId}")
    public Result<Order> deleteOrderById(@PathVariable("orderId") Integer id){
        orderService.removeById(id);
        return Result.success("删除订单成功");
    }

    @ApiOperation("修改订单状态")
    @PutMapping("/changeOrderStatus")
    public Result<?> changeOrderStatus(@RequestParam("status")Integer status,@RequestParam("id")Integer id){
        orderService.changeOrderStatus(status,id);
        return Result.success("提交申请成功");
    }
}
