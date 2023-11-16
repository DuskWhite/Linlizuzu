package com.xyxy.sys.entity;



import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 
 * @TableName zu_msg
 */
@TableName(value ="zu_msg")
@Data
public class ZuMsg implements Serializable {
    /**
     * 消息ID
     */
    @TableId(type = IdType.AUTO)
    private Integer msgId;

    /**
     * 消息内容
     */
    private String msg;

    /**
     * 发送者ID
     */
    private Integer senderId;

    /**
     * 接收者ID
     */
    private Integer receiverId;

    /**
     * 消息修改时间
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}