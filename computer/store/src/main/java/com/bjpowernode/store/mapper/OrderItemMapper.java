package com.bjpowernode.store.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bjpowernode.store.domain.Order;
import com.bjpowernode.store.domain.OrderItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemMapper extends BaseMapper<Order> {

    Integer insertOrderItem(OrderItem item);

    List<OrderItem> getUserOrderItemByOrderId(String oid);
}
