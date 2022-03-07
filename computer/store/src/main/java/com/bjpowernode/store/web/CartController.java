package com.bjpowernode.store.web;

import com.bjpowernode.store.domain.Cart;
import com.bjpowernode.store.domain.User;
import com.bjpowernode.store.service.CartService;
import com.bjpowernode.store.util.JsonResult;
import com.bjpowernode.store.vo.CartVO;
import com.bjpowernode.store.vo.Vo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("cart")
public class CartController extends BaseController {
    @Autowired
    private CartService cartService;

    @RequestMapping("/add_cart")
    public JsonResult<Void> getHotList(Cart cart, HttpSession session) {
        User user = (User) session.getAttribute("user");
        cart.setUid(user.getUid());
        cart.setCreatedUser(user.getUsername());
        cart.setCreatedTime(new Date());
        cart.setModifiedTime(new Date());
        cart.setModifiedUser(user.getUsername());
        cartService.addToCart(cart);
        return new JsonResult<>(OK);
    }


    @RequestMapping("/showProductList")
    public JsonResult<List<CartVO>> getProductList(HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<CartVO> data = cartService.getProductLists(session);
        return new JsonResult<List<CartVO>>(OK, data);
    }

    @RequestMapping("/addProductNum")
    public JsonResult<List<CartVO>> addProductNum(Integer pid,HttpSession session) {
        User user = (User) session.getAttribute("user");
        cartService.addProductNum(pid,user.getUid());
        return new JsonResult<List<CartVO>>(OK);
    }
    @RequestMapping("/reduceProductNum")
    public JsonResult<Void> reduceProductNum(Integer pid,HttpSession session) {
        User user = (User) session.getAttribute("user");
        cartService.reduceProductNum(pid,user.getUid());
        return new JsonResult<Void>(OK);
    }

    @RequestMapping("/delCartItem")
    public JsonResult<Void> delCartItem(Integer pid,Integer cid) {
        cartService.delCartItem(pid,cid);
        return new JsonResult<Void>(OK);
    }

    @GetMapping("/orderList")
    public JsonResult<List<CartVO>> getVOByCids(Integer[] cids, HttpSession session) {
        //System.out.println(cids);
        // 从Session中获取uid
        User user = (User) session.getAttribute("user");
        Integer uid = user.getUid();
        // 调用业务对象执行查询数据
        List<CartVO> data = cartService.getVOByCids(uid, cids);
        // 返回成功与数据
        return new JsonResult<>(OK, data);
    }

    @RequestMapping("/deleteXZ")
    public JsonResult<Void> deleteXZ(HttpServletRequest request, HttpSession session) {
        String[] cids = request.getParameterValues("id");
        User user = (User) session.getAttribute("user");
        Integer uid = user.getUid();
        cartService.delete(cids,uid);
        return new JsonResult<>(OK);
    }

    @RequestMapping("/showPrice")
    public JsonResult<CartVO> showPrice(HttpServletRequest request, HttpSession session) {
        String[] cids = request.getParameterValues("id");
        // 从Session中获取uid
        //System.out.println(Arrays.toString(cids));
        User user = (User) session.getAttribute("user");
        Integer uid = user.getUid();
        // 调用业务对象执行查询数据
        CartVO data = cartService.showPrice(cids,uid);
        // 返回成功与数据
        return new JsonResult<>(OK, data);
    }

    @RequestMapping("/test")
    public JsonResult test() {
        List<Vo> test = cartService.test();
        return new JsonResult(OK,MESSAGE+test.size(), test);
    }

}
