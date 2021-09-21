package com.bjpowernode.store.service.impl;

import com.bjpowernode.store.domain.*;
import com.bjpowernode.store.mapper.AddressMapper;
import com.bjpowernode.store.mapper.CartMapper;
import com.bjpowernode.store.mapper.OrderMapper;
import com.bjpowernode.store.mapper.ProductMapper;
import com.bjpowernode.store.service.OrderService;
import com.bjpowernode.store.service.execption.AccessDeniedException;
import com.bjpowernode.store.service.execption.AddressNotFoundException;
import com.bjpowernode.store.service.execption.InsertException;
import com.bjpowernode.store.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductMapper productMapper;
    @Override
    @Transactional
    public Order creat(Integer aid, Integer uid, Integer[] cids,String username) {
        // 创建当前时间对象
        Date now = new Date();
        Address address = addressMapper.findByAid(aid);
        if (address==null){
            throw new AddressNotFoundException("地址未找到");
        }
        if (!address.getUid().equals(uid)){
            throw new AccessDeniedException("非法数据异常");
        }
        address.setProvinceCode(null);
        address.setCityCode(null);
        address.setAreaCode(null);
        address.setCreatedUser(null);
        address.setCreatedTime(null);
        address.setModifiedUser(null);
        address.setModifiedTime(null);
        Order order = new Order();
        long totalPrice = 0;
        for (int i = 0; i < cids.length; i++) {
            Cart c = cartMapper.selectCartByCidUid(String.valueOf(cids[i]), uid);
            totalPrice = totalPrice + c.getPrice() * c.getNum();
        }
            order.setUid(address.getUid());
            order.setRecvName(address.getName());
            order.setRecvProvince(address.getProvinceName());
            order.setRecvCity(address.getCityName());
            order.setRecvArea(address.getAreaName());
            order.setRecvPhone(address.getPhone());
            order.setRecvAddress(address.getAddress());
            order.setTotalPrice(totalPrice);
            // 补全数据：status
            order.setStatus(0);
            // 补全数据：下单时间
            order.setOrderTime(now);
            // 补全数据：日志
            order.setCreatedUser(username);
            order.setCreatedTime(now);
            order.setModifiedUser(username);
            order.setModifiedTime(now);
            // 插入订单数据
            Integer rows1 = orderMapper.insertOrder(order);
            if (rows1!=1){
                throw new InsertException("插入订单数据异常");
            }
        for (int i = 0; i < cids.length; i++ ) {
            Cart cart = cartMapper.selectCartByCidUid(String.valueOf(cids[i]), uid);
            Product product = productMapper.findById(cart.getPid());
            // 创建订单商品数据
            OrderItem item = new OrderItem();
            // 补全数据：setOid(order.getOid())
            item.setOid(order.getOid());
            // 补全数据：pid, title, image, price, num
            item.setPid(cart.getPid());
            item.setTitle(product.getTitle());
            item.setImage(product.getImage());
            item.setPrice(product.getPrice()*cart.getNum());
            item.setNum(cart.getNum());
            // 补全数据：4项日志
            item.setCreatedUser(username);
            item.setCreatedTime(now);
            item.setModifiedUser(username);
            item.setModifiedTime(now);
            // 插入订单商品数据
            Integer rows2 = orderMapper.insertOrderItem(item);
        }
        return order;
    }
}
