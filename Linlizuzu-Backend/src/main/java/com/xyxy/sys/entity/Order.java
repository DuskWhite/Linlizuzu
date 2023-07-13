package com.xyxy.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author 吴沈涛
 * @since 2023-06-01
 */
@TableName("x_order")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "order_id", type = IdType.AUTO)
    private Integer orderId;

    private String orderNo;

    private String ownerName;

    private String renterName;

    private String goodName;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime rentTime;
//  转换时间格式 去除LocalDateTime尾部的Z
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime overTime;

    private Integer orderStatus;

    private Double orderAmountTotal;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
    public String getRenterName() {
        return renterName;
    }

    public void setRenterName(String renterName) {
        this.renterName = renterName;
    }
    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }
    public LocalDateTime getRentTime() {
        return rentTime;
    }

    public void setRentTime(LocalDateTime rentTime) {
        this.rentTime = rentTime;
    }
    public LocalDateTime getOverTime() {
        return overTime;
    }

    public void setOverTime(LocalDateTime overTime) {
        this.overTime = overTime;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Double getOrderAmountTotal() {
        return orderAmountTotal;
    }

    public void setOrderAmountTotal(Double orderAmountTotal) {
        this.orderAmountTotal = orderAmountTotal;
    }

    @Override
    public String toString() {
        return "Order{" +
            "orderId=" + orderId +
            ", orderNo=" + orderNo +
            ", ownerName=" + ownerName +
            ", renterName=" + renterName +
            ", goodName=" + goodName +
            ", rentTime=" + rentTime +
            ", overTime=" + overTime +
            ", orderStatus=" + orderStatus +
            ", orderAmountTotal=" + orderAmountTotal +
        "}";
    }
}
