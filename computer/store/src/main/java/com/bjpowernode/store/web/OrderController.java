package com.bjpowernode.store.web;

import com.bjpowernode.store.domain.Order;
import com.bjpowernode.store.domain.OrderItem;
import com.bjpowernode.store.domain.Product;
import com.bjpowernode.store.domain.User;
import com.bjpowernode.store.service.OrderService;
import com.bjpowernode.store.util.JsonResult;
import com.bjpowernode.store.vo.OrderConfim;
import com.bjpowernode.store.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController extends BaseController {
    @Autowired
    private OrderService orderService;
    @RequestMapping("/creat")
    public JsonResult<Order> creat(Integer aid, Integer[] cids, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Integer uid = user.getUid();
        String username = user.getUsername();
        Order data = orderService.creat(aid,uid,cids,username);
        return new JsonResult<>(OK,data);
    }

    @RequestMapping("/creatOrderItem")
    public JsonResult<Order> creatOrderItem(String oid ,String cids, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Integer cid[] = new Integer[100];
        for (int i = 3; i < cids.split("&").length; i++) {
            cid[i-3] = Integer.parseInt((cids.split("&")[i]).split("=")[1]);
        }
        Integer uid = user.getUid();
        String username = user.getUsername();
        Order data = orderService.creatOrderItem(uid,cid,username,oid);
//        return new JsonResult<>(OK,data);
        return new JsonResult<>(OK);
    }

    @RequestMapping("/show")
    public JsonResult<Void> showOrder(HttpSession session) {
        User user = (User) session.getAttribute("user");
        Integer uid = user.getUid();
        return new JsonResult<>(OK);
    }

    @RequestMapping("/showOrders")
    public JsonResult<List> showOrders(HttpSession session) {
        User user = (User) session.getAttribute("user");
        Integer uid = user.getUid();
        List<OrderVO<OrderItem>> list = orderService.getUserOrders(uid);
        return new JsonResult<List>(OK,list);
    }

    @RequestMapping("/showAddress")
    public JsonResult<List> showAddress(HttpSession session) {
        User user = (User) session.getAttribute("user");
        Integer uid = user.getUid();
        List<Order> order = orderService.getUserOrder(uid);
        return new JsonResult<List>(OK,order);
    }

    @RequestMapping("/getOrderStatus")
    public JsonResult<List> getOrderStatus(String id , HttpSession session) {
//        System.out.println(id);
        User user = (User) session.getAttribute("user");
        Integer uid = user.getUid();
        List<OrderConfim<Order,Product,OrderItem>> order = orderService.getOrderStatus(id);
        return new JsonResult<List>(OK,MESSAGE,order);
    }


    @RequestMapping("/ConfirmGoods")
    public JsonResult<Order> ConfirmGoods(String id) {
        System.out.println(new BigDecimal(id).toPlainString());
        orderService.ConfirmGoods(id);
        return new JsonResult<>(OK);
    }

}
