package com.bjpowernode.store.service;

import com.bjpowernode.store.domain.Cart;
import com.bjpowernode.store.vo.CartVO;
import com.bjpowernode.store.vo.Vo;

import javax.servlet.http.HttpSession;
import java.util.List;


public interface CartService {

    void addToCart(Cart cart);

    List<CartVO> getProductLists(HttpSession session);

    void addProductNum(Integer pid, Integer uid);

    void reduceProductNum(Integer pid, Integer uid);

    void delCartItem(Integer pid, Integer cid);

    List<CartVO> getVOByCids(Integer uid, Integer[] cids);

    void delete(String[] cids, Integer uid);

    CartVO showPrice(String[] cids, Integer uid);

    List<Vo> test();

}
