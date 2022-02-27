package com.bjpowernode.store.domain;

import java.io.Serializable;
import java.util.Objects;

/** 收藏数据的实体类 */
public class Favority extends BaseDomain implements Serializable {
    private String fid;
    private Integer uid;
    private Integer pid;
    private Integer num;


    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
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

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Favority favority = (Favority) o;
        return Objects.equals(fid, favority.fid) &&
                Objects.equals(uid, favority.uid) &&
                Objects.equals(pid, favority.pid) &&
                Objects.equals(num, favority.num);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fid, uid, pid, num);
    }

    @Override
    public String toString() {
        return "Favority{" +
                "fid='" + fid + '\'' +
                ", uid=" + uid +
                ", pid=" + pid +
                ", num=" + num +
                '}';
    }
}
