package com.bjpowernode.store.vo;

import java.io.Serializable;

/** 购物车数据的Value Object类 */

@lombok.Data
public class OrderVO<E> implements Serializable {
    private String oid;
    private Integer uid;
    private String recvName;
    private String time;
    private E e;

}
