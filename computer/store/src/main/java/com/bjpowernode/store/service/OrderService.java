package com.bjpowernode.store.service;

import com.bjpowernode.store.domain.Order;
import com.bjpowernode.store.domain.OrderItem;
import com.bjpowernode.store.domain.Product;
import com.bjpowernode.store.vo.OrderVO;

import java.util.List;

public interface OrderService {
    Order creat(Integer aid, Integer uid, Integer[] cids,String username);

    List<OrderVO<OrderItem>> getUserOrders(Integer uid);

    List<Order> getUserOrder(Integer uid);

    Order creatOrderItem(Integer aid, Integer uid, Integer[] cids, String username);
}
