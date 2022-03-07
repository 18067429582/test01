package com.bjpowernode.store.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.bjpowernode.store.StoreApplication;
import com.bjpowernode.store.domain.*;
import com.bjpowernode.store.mapper.*;
import com.bjpowernode.store.service.OrderService;
import com.bjpowernode.store.service.execption.AccessDeniedException;
import com.bjpowernode.store.service.execption.AddressNotFoundException;
import com.bjpowernode.store.service.execption.InsertException;
import com.bjpowernode.store.service.execption.TestException;
import com.bjpowernode.store.vo.CartVO;
import com.bjpowernode.store.vo.OrderConfim;
import com.bjpowernode.store.vo.OrderVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.PushbackInputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
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


    private static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class.getName());

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

    /**
     * 获取用户商品订单详情
     * @param uid
     * @return
     */
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
//                    System.out.println(order.getOrderTime());
//                    System.out.println(simpleDateFormat.format(order.getOrderTime()));
                    orderVO.setE(orderItem);
                    orderVOList.add(orderVO);
                }
            }
        }else {
            throw new TestException("暂无订单");
        }
        return orderVOList;
    }

    /**
     * 获取用户订单
     * @param uid
     * @return
     */
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

    /**
     * 创建用户订单
     * @param oid
     * @param uid
     * @param cids
     * @param username
     * @return
     */
    @Override
    public Order creatOrderItem(Integer uid, Integer[] cids, String username,String oid) {
        Date now = new Date();
        Order order = new Order();
//        List<Order> orders = orderMapper.selectByUidComplete(uid);
        Order orders = orderMapper.selectByOid(oid);
//        System.out.println(oid);
        int count = 0;
            for (int i = 0; i < cids.length; i++ ) {
               if (cids[i] != null){
                   //System.out.println(cids[i]);
                   Cart cart = cartMapper.selectCartByCidUid(String.valueOf(cids[i]), uid);
                   //System.out.println(cart.getPid());
                   //System.out.println(productMapper.findById(cart.getPid()));
                   Product product = productMapper.findById(cart.getPid());
                   // 创建订单商品数据
                   OrderItem item = new OrderItem();
                   item.setId(orders.getOid()+product.getId().toString().substring(5,8));
                   item.setOid(orders.getOid());
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
            }
//            if (count != cids.length){
//                throw new InsertException("订单创建异常");
//            }
//            UpdateWrapper<Order> updateWrapper = new UpdateWrapper<>();
//            order1.setComplete(1);
//            orderMapper.update(order1,updateWrapper);
//            orderMapper.updateComplete(order1.getOid());
        log.info("用户订单商品详情创建成功");
        return order;
    }

    @Override
    public List<OrderConfim<Order, Product,OrderItem>> getOrderStatus(String id) {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<OrderConfim<Order, Product,OrderItem>> list = new ArrayList<>();
        OrderConfim<Order, Product,OrderItem> orderProductOrderConfim = new OrderConfim<>();
        OrderItem orderItem = orderItemMapper.selectById(id);
        Order order = null;
        Product product = null;
        if (id != null && id != ""){
            order = orderMapper.selectByOid(orderItem.getOid());
            product = productMapper.findById(orderItem.getPid());
//            order.setOrderTime(simpleDateFormat.format(order.getOrderTime()));
            orderProductOrderConfim.setE(order);
            orderProductOrderConfim.setP(product);
            orderProductOrderConfim.setI(orderItem);
            list.add(orderProductOrderConfim);
        }
        return list;
    }


    /**
     * 确认收货
     * @param id
     */
    @Override
    public void ConfirmGoods(String id) {
        if (id != null){
            orderItemMapper.updataStatus(id);
            OrderItem orderItem = orderItemMapper.selectById(id);
            if (orderItem != null ){
                Integer count = orderItemMapper.selectByOidAndflag(orderItem.getOid());
                if (count == 0){
                    orderMapper.updateComplete(orderItem.getOid());
                }
            }
        }
    }

//    public static void main(String[] args) throws UnsupportedEncodingException {
//        System.out.println(Base64.getEncoder().encodeToString("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAriGJWV7Wbz9GiK6nxNFtR+Q0HMSc3cYQnmixd/8AeSbVMGJZ12xh9ZdRCm+voYibT+dRQaxvl3hDd7MfnJkaiB94HDmNkkuh9zMwXjRP5d7MAZbRYpAc7KZSx63mbvnKwbGIFjeWBs6Z0615LXxCLEhQVWUUTn+n26llUzf9UWLi4bVTc+ZZmjgV6UKhC9vXl+Ph+Xul632Oyx2R4+kYuQV1Lc74GblgsdAj43kFKhXHqwXxf6J2irRn03cWizdsgleeWTmEjjI90Py0D/1TbEX12BcLPvfmemmhJhMHli99WJaHfsOtdPErXBIdY+WHhsFzEYUOyaAiteyq0QXIhQIDAQAB".getBytes("utf-8")));
//        System.out.println(new String(Base64.getDecoder().decode("TUlJQklqQU5CZ2txaGtpRzl3MEJBUUVGQUFPQ0FROEFNSUlCQ2dLQ0FRRUFyaUdKV1Y3V2J6OUdpSzZueE5GdFIrUTBITVNjM2NZUW5taXhkLzhBZVNiVk1HSloxMnhoOVpkUkNtK3ZvWWliVCtkUlFheHZsM2hEZDdNZm5Ka2FpQjk0SERtTmtrdWg5ek13WGpSUDVkN01BWmJSWXBBYzdLWlN4NjNtYnZuS3diR0lGamVXQnM2WjA2MTVMWHhDTEVoUVZXVVVUbituMjZsbFV6ZjlVV0xpNGJWVGMrWlptamdWNlVLaEM5dlhsK1BoK1h1bDYzMk95eDJSNCtrWXVRVjFMYzc0R2JsZ3NkQWo0M2tGS2hYSHF3WHhmNkoyaXJSbjAzY1dpemRzZ2xlZVdUbUVqakk5MFB5MEQvMVRiRVgxMkJjTFB2Zm1lbW1oSmhNSGxpOTlXSmFIZnNPdGRQRXJYQklkWStXSGhzRnpFWVVPeWFBaXRleXEwUVhJaFFJREFRQUI="), StandardCharsets.UTF_8));
//    }
}
