package com.bjpowernode.store.mapper;

import com.bjpowernode.store.domain.Order;
import com.bjpowernode.store.domain.OrderItem;

public interface OrderMapper {
    Integer insertOrder(Order order);

    Integer insertOrderItem(OrderItem item);
}
