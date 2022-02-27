package com.bjpowernode.store.domain;

import java.io.Serializable;

@lombok.Data
/** 订单中的商品数据 */
public class OrderItem extends BaseDomain implements Serializable {
    private String id;
    private Integer uid;
    private String oid;
    private Integer pid;
    private String title;
    private String image;
    private Long price;
    private Integer num;

}
