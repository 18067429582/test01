package com.bjpowernode.store.vo;

import java.io.Serializable;


@lombok.Data
public class OrderVO<E> implements Serializable {
    private String id;
    private String oid;
    private Integer uid;
    private String recvName;
    private String time;
    private E e;

}
