package com.bjpowernode.store.vo;

import java.io.Serializable;

/** 购物车数据的Value Object类 */

@lombok.Data
public class CartVO implements Serializable {
    private Integer cid;
    private Integer uid;
    private String fid;
    private Integer pid;
    private Long price;
    private Integer num;
    private String title;
    private Long realPrice;
    private String image;

}
