package com.bjpowernode.store.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bjpowernode.store.domain.Cart;
import com.bjpowernode.store.domain.Order;
import com.bjpowernode.store.vo.CartVO;
import com.bjpowernode.store.vo.Vo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartMapper extends BaseMapper<Cart> {
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

    List<Vo> test();
}
