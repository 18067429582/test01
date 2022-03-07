package com.bjpowernode.store.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/** 订单数据的实体类 */
@lombok.Data
@TableName("t_order")
public class Order extends BaseDomain implements Serializable {

    @TableId(type = IdType.UUID)
    private String oid;
    private Integer uid;
    private String recvName;
    private String recvPhone;
    private String recvProvince;
    private String recvCity;
    private String recvArea;
    private String recvAddress;
    private Long totalPrice;
    private Integer status;
    private Date orderTime;
    private Date payTime;
    private String time;
    private Integer complete;
    private Integer pid;

}
