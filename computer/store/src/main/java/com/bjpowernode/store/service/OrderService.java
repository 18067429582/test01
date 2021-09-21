package com.bjpowernode.store.service;

import com.bjpowernode.store.domain.Order;

public interface OrderService {
    Order creat(Integer aid, Integer uid, Integer[] cids,String username);
}
