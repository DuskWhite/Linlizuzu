package com.xyxy.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 吴沈涛
 * @since 2023-05-19
 */
@TableName("zu_good")
public class Good implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String goodname;

    private Double price;

    private String img;


    private Integer deleted;

    private String username;

    private Integer status;

    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }
    public String getGoodname() {
        return goodname;
    }

    public void setGoodname(String goodname) {
        this.goodname = goodname;
    }
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
    public String getUsername() {
        return username;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Good{" +
                "id=" + id +
                ", goodname='" + goodname + '\'' +
                ", price=" + price +
                ", img='" + img + '\'' +
                ", deleted=" + deleted +
                ", username='" + username + '\'' +
                ", status=" + status +
                '}';
    }
}
