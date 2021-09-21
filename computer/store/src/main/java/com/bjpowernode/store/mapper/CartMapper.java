package com.bjpowernode.store.mapper;

import com.bjpowernode.store.domain.Cart;
import com.bjpowernode.store.vo.CartVO;

import java.util.List;

public interface CartMapper {
    Cart selectProductById(Integer pid,Integer uid);

    Cart selectProductByPidCid(Integer pid,Integer cid);

    int insertProduct(Cart cart);

    int updataCartNum(Cart cart);

    List<Cart> findByUid(Integer uid);

    int selectProductCount(Integer pid, Integer uid);

    int reduceProductNum(Integer pid, Integer uid, int i);

    int delCartItem(Integer pid, Integer cid);

    List<CartVO> findVOByCids(Integer[] cids);

    int selectCartCount(String cid, Integer uid);

    int delete(String cid, Integer uid);

    Cart selectCartByCidUid(String cid, Integer uid);

    Cart selectCartByPidUid(Integer pid, Integer uid);
}
