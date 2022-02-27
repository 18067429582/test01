package com.bjpowernode.store.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bjpowernode.store.domain.Order;
import com.bjpowernode.store.domain.OrderItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMapper extends BaseMapper<Order> {
    Integer insertOrder(Order order);


    List<Order> selectByUid(Integer uid);

    List<Order> getOrdersByUid(Integer uid);

    List<Order> selectByUidComplete(Integer uid);
}
