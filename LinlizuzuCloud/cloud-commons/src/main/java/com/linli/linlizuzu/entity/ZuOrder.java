package com.linli.linlizuzu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName zu_order
 */
@TableName(value ="zu_order")
@Data
public class ZuOrder implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer orderId;

    /**
     * 
     */
    private String orderNo;

    /**
     * 
     */
    private String ownerName;

    /**
     * 
     */
    private String renterName;

    /**
     * 
     */
    private String goodName;

    /**
     * 
     */
    private Date rentTime;

    /**
     * 
     */
    private Date overTime;

    /**
     * 
     */
    private Integer orderStatus;

    /**
     * 
     */
    private Double orderAmountTotal;

    /**
     * 
     */
    private Integer delete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ZuOrder other = (ZuOrder) that;
        return (this.getOrderId() == null ? other.getOrderId() == null : this.getOrderId().equals(other.getOrderId()))
            && (this.getOrderNo() == null ? other.getOrderNo() == null : this.getOrderNo().equals(other.getOrderNo()))
            && (this.getOwnerName() == null ? other.getOwnerName() == null : this.getOwnerName().equals(other.getOwnerName()))
            && (this.getRenterName() == null ? other.getRenterName() == null : this.getRenterName().equals(other.getRenterName()))
            && (this.getGoodName() == null ? other.getGoodName() == null : this.getGoodName().equals(other.getGoodName()))
            && (this.getRentTime() == null ? other.getRentTime() == null : this.getRentTime().equals(other.getRentTime()))
            && (this.getOverTime() == null ? other.getOverTime() == null : this.getOverTime().equals(other.getOverTime()))
            && (this.getOrderStatus() == null ? other.getOrderStatus() == null : this.getOrderStatus().equals(other.getOrderStatus()))
            && (this.getOrderAmountTotal() == null ? other.getOrderAmountTotal() == null : this.getOrderAmountTotal().equals(other.getOrderAmountTotal()))
            && (this.getDelete() == null ? other.getDelete() == null : this.getDelete().equals(other.getDelete()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getOrderId() == null) ? 0 : getOrderId().hashCode());
        result = prime * result + ((getOrderNo() == null) ? 0 : getOrderNo().hashCode());
        result = prime * result + ((getOwnerName() == null) ? 0 : getOwnerName().hashCode());
        result = prime * result + ((getRenterName() == null) ? 0 : getRenterName().hashCode());
        result = prime * result + ((getGoodName() == null) ? 0 : getGoodName().hashCode());
        result = prime * result + ((getRentTime() == null) ? 0 : getRentTime().hashCode());
        result = prime * result + ((getOverTime() == null) ? 0 : getOverTime().hashCode());
        result = prime * result + ((getOrderStatus() == null) ? 0 : getOrderStatus().hashCode());
        result = prime * result + ((getOrderAmountTotal() == null) ? 0 : getOrderAmountTotal().hashCode());
        result = prime * result + ((getDelete() == null) ? 0 : getDelete().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", orderId=").append(orderId);
        sb.append(", orderNo=").append(orderNo);
        sb.append(", ownerName=").append(ownerName);
        sb.append(", renterName=").append(renterName);
        sb.append(", goodName=").append(goodName);
        sb.append(", rentTime=").append(rentTime);
        sb.append(", overTime=").append(overTime);
        sb.append(", orderStatus=").append(orderStatus);
        sb.append(", orderAmountTotal=").append(orderAmountTotal);
        sb.append(", delete=").append(delete);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}