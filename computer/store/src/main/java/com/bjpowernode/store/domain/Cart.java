package com.bjpowernode.store.domain;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Objects;

/** 购物车数据的实体类 */
@TableName("t_cart")
public class Cart extends BaseDomain implements Serializable {
    private Integer cid;
    private Integer uid;
    private Integer pid;
    private Long price;
    private Integer num;
    private Integer complete;

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getComplete() {
        return complete;
    }

    public void setComplete(Integer complete) {
        this.complete = complete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(cid, cart.cid) && Objects.equals(uid, cart.uid) && Objects.equals(pid, cart.pid) && Objects.equals(price, cart.price) && Objects.equals(num, cart.num) && Objects.equals(complete, cart.complete);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cid, uid, pid, price, num, complete);
    }
}
