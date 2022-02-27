package com.bjpowernode.store.web;

import com.bjpowernode.store.domain.Cart;
import com.bjpowernode.store.domain.Favority;
import com.bjpowernode.store.domain.User;
import com.bjpowernode.store.service.CartService;
import com.bjpowernode.store.service.FavorityService;
import com.bjpowernode.store.util.JsonResult;
import com.bjpowernode.store.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("favorites")
public class FavorityController extends BaseController {
    @Resource
    private FavorityService favorityService;

    @Resource
    private CartService cartService;

    @RequestMapping("/add_favorites")
    public JsonResult<Void> favorites(Favority favority, HttpSession session) {
        //System.out.println(favority.toString());
        User user = (User) session.getAttribute("user");
        favority.setUid(user.getUid());
        favority.setCreatedUser(user.getUsername());
        favority.setCreatedTime(new Date());
        favority.setModifiedTime(new Date());
        favority.setModifiedUser(user.getUsername());
        favorityService.favorites(favority);
        return new JsonResult<>(OK);
    }


    @RequestMapping("/showFavoritesList")
    public JsonResult<List<CartVO>> getProductList(HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<CartVO> data = favorityService.getProductLists(session);
        return new JsonResult<List<CartVO>>(OK, data);
    }

    @RequestMapping("/cancelFavorites")
    public JsonResult<Void> delCartItem(String fid,HttpSession session,Integer pid) {
        User user = (User) session.getAttribute("user");
        Integer uid = user.getUid();
        favorityService.cancelFavorites(fid,uid,pid);
        return new JsonResult<Void>(OK);
    }

    @RequestMapping("/addCart")
    public JsonResult<Void> addProductNum(String fid,HttpSession session,Integer pid) {
        User user = (User) session.getAttribute("user");
        System.out.println(pid);
        System.out.println(fid);
        favorityService.addCart(pid,fid,user.getUid(),user.getUsername());
        return new JsonResult<Void>(OK);
    }
    @RequestMapping("/reduceProductNum")
    public JsonResult<Void> reduceProductNum(Integer pid,HttpSession session) {
        User user = (User) session.getAttribute("user");
        cartService.reduceProductNum(pid,user.getUid());
        return new JsonResult<Void>(OK);
    }



    @GetMapping("/orderList")
    public JsonResult<List<CartVO>> getVOByCids(Integer[] cids, HttpSession session) {
        System.out.println(cids);
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
}
