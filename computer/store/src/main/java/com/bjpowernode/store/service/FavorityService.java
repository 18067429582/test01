package com.bjpowernode.store.service;

import com.bjpowernode.store.domain.Cart;
import com.bjpowernode.store.domain.Favority;
import com.bjpowernode.store.vo.CartVO;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface FavorityService {
    void favorites(Favority favority);

    List<CartVO> getProductLists(HttpSession session);

    void cancelFavorites(String fid, Integer uid, Integer pid);

    void addCart(Integer pid, String fid, Integer uid,String uName);
}
