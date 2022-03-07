package com.bjpowernode.store.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bjpowernode.store.domain.Order;
import com.bjpowernode.store.domain.OrderItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemMapper{

    Integer insertOrderItem(OrderItem item);

    List<OrderItem> getUserOrderItemByOrderId(String oid);


    OrderItem selectById(String id);

    Integer selectByOidAndflag(String oid);

    void updataStatus(String id);
}
