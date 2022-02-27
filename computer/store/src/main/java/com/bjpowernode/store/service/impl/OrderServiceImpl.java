package com.bjpowernode.store.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.bjpowernode.store.domain.*;
import com.bjpowernode.store.mapper.*;
import com.bjpowernode.store.service.OrderService;
import com.bjpowernode.store.service.execption.AccessDeniedException;
import com.bjpowernode.store.service.execption.AddressNotFoundException;
import com.bjpowernode.store.service.execption.InsertException;
import com.bjpowernode.store.service.execption.TestException;
import com.bjpowernode.store.vo.CartVO;
import com.bjpowernode.store.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Autowired
    private OrderItemMapper orderItemMapper;


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
        Cart c = null;
        for (int i = 0; i < cids.length; i++) {
            c = cartMapper.selectCartByCidUid(String.valueOf(cids[i]), uid);
            totalPrice = totalPrice + c.getPrice() * c.getNum();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String oid = uid+simpleDateFormat.format(now);
            order.setOid(oid);
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
            Integer rows1 = orderMapper.insert(order);
            if (c != null) {
                //修改库存
                Product p = productMapper.findById(c.getPid());
                if (p.getNum() - c.getNum() < 0){
                    throw new InsertException("库存不够，请联系客服人员");
                }
                productMapper.editNum(p.getNum(),c.getNum(),c.getPid());
            }
            if (rows1!=1){
                throw new InsertException("插入订单数据异常");
            }
            int count = 0;

       /* for (int i = 0; i < cids.length; i++ ) {
            Cart cart = cartMapper.selectCartByCidUid(String.valueOf(cids[i]), uid);
            Product product = productMapper.findById(cart.getPid());

            // 创建订单商品数据
            OrderItem item = new OrderItem();
            item.setId(oid+product.getId());
            item.setOid(oid);
            item.setUid(uid);
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
            Integer rows2 = orderItemMapper.insertOrderItem(item);
            cartMapper.delete(cids[i].toString(),uid);
            count = count + rows2;
        }
            if (count != cids.length){
            throw new InsertException("订单创建异常");
        }*/

        return order;
    }

    @Override
    public List<OrderVO<OrderItem>> getUserOrders(Integer uid) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Order> list = orderMapper.getOrdersByUid(uid);
        List<OrderVO<OrderItem>> orderVOList = new ArrayList<>();
        List<OrderItem> orderItemrsList = null;
        if (list != null){
            for (Order order:list) {
                orderItemrsList = orderItemMapper.getUserOrderItemByOrderId(order.getOid());
                for (OrderItem orderItem:orderItemrsList) {
                    OrderVO<OrderItem> orderVO = new OrderVO<>();
                    orderVO.setOid(order.getOid());
                    orderVO.setRecvName(order.getRecvName());
                    orderVO.setTime(simpleDateFormat.format(order.getOrderTime()));
                    System.out.println(order.getOrderTime());
                    System.out.println(simpleDateFormat.format(order.getOrderTime()));
                    orderVO.setE(orderItem);
                    orderVOList.add(orderVO);
                }
            }
        }else {
            throw new TestException("暂无订单");
        }
        return orderVOList;
    }

    @Override
    public List<Order> getUserOrder(Integer uid) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        List<Order> order = orderMapper.selectByUid(uid);
        if (order == null ){
            throw new InsertException("该用户暂无订单信息");
        }
        for (Order o:order
             ) {
            o.setTime(simpleDateFormat.format(o.getOrderTime()));
        }

        return order;
    }

    @Override
    public Order creatOrderItem(Integer aid, Integer uid, Integer[] cids, String username) {
        Date now = new Date();
        Order order = new Order();
        List<Order> orders = orderMapper.selectByUidComplete(uid);
        int count = 0;
        for (Order order1: orders) {
            for (int i = 0; i < cids.length; i++ ) {
                Cart cart = cartMapper.selectCartByCidUid(String.valueOf(cids[i]), uid);
                Product product = productMapper.findById(cart.getPid());
                // 创建订单商品数据
                OrderItem item = new OrderItem();
                item.setId(order1.getOid()+product.getId());
                item.setOid(order1.getOid());
                item.setUid(uid);
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
                Integer rows2 = orderItemMapper.insertOrderItem(item);
                cartMapper.delete(cids[i].toString(),uid);
                count = count + rows2;
            }
            if (count != cids.length){
                throw new InsertException("订单创建异常");
            }
            UpdateWrapper<Order> updateWrapper = new UpdateWrapper<>();
            order1.setComplete(1);
            orderMapper.update(order1,updateWrapper);
        }
        return order;
    }
}
