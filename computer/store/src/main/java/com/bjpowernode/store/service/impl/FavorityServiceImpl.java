package com.bjpowernode.store.service.impl;

import com.bjpowernode.store.domain.Cart;
import com.bjpowernode.store.domain.Favority;
import com.bjpowernode.store.domain.Product;
import com.bjpowernode.store.domain.User;
import com.bjpowernode.store.mapper.CartMapper;
import com.bjpowernode.store.mapper.FavorityMapper;
import com.bjpowernode.store.mapper.ProductMapper;
import com.bjpowernode.store.service.FavorityService;
import com.bjpowernode.store.service.execption.CartNotFoundException;
import com.bjpowernode.store.service.execption.CommodityRepeatException;
import com.bjpowernode.store.service.execption.DeleteFavorityException;
import com.bjpowernode.store.service.execption.InsertException;
import com.bjpowernode.store.util.UUIDUtil;
import com.bjpowernode.store.vo.CartVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FavorityServiceImpl implements FavorityService {
    @Resource
    private FavorityMapper favorityMapper;

    @Resource
    private ProductMapper productMapper;

    @Resource
    private CartMapper cartMapper;

    @Override
    public void favorites(Favority favority) {
        Favority result = favorityMapper.selectProductById(favority.getPid(), favority.getUid());
        if (result != null) {
            throw new CartNotFoundException("该商品之前已经收藏过，无须重复");
        }
        //到这里说明没有收藏过
        System.out.println(favority.getCreatedTime());
        System.out.println(favority);
        favority.setFid(UUIDUtil.getUUIDId());
        System.out.println(favority);
        int num = favorityMapper.insertFavority(favority);
        if (num != 1) {
            throw new InsertException("收藏商品异常");
        }
    }
    @Override
    public List<CartVO> getProductLists(HttpSession session) {
        User user = (User) session.getAttribute("user");
        Integer uid = user.getUid();
        List<CartVO> clist = new ArrayList<>();
        List<Favority> list = favorityMapper.findByUid(uid);
        for (Favority l:list) {
            Product product = productMapper.findById(l.getPid());
            CartVO cartVO = new CartVO();
            cartVO.setFid(l.getFid());
            cartVO.setPid(product.getId());
            cartVO.setUid(uid);
            cartVO.setPrice(product.getPrice());
            cartVO.setNum(l.getNum());
            cartVO.setTitle(product.getTitle());
            cartVO.setImage(product.getImage());
            clist.add(cartVO);
        }
        return clist;
    }

    @Override
    public void cancelFavorites(String fid, Integer uid, Integer pid) {
        Favority favority = new Favority();
        favority.setUid(uid);
        favority.setPid(pid);
        favority.setFid(fid);
        int count = favorityMapper.deleteByPidUidFid(favority);
        if (count!=1){
            throw new DeleteFavorityException("取消已经收藏的商品失败");
        }
    }
    @Transactional
    @Override
    public void addCart(Integer pid, String fid, Integer uid,String uName) {
        Cart cart1 = cartMapper.selectCartByPidUid(pid,uid);
        if (cart1!=null){
            throw new CommodityRepeatException("您的购物车已有该商品，无须重复添加");
        }
        Product product = productMapper.findById(pid);
        Cart cart = new Cart();
        cart.setPid(pid);
        cart.setUid(uid);
        cart.setNum(1);
        cart.setCreatedTime(new Date());
        cart.setCreatedUser(uName);
        cart.setModifiedTime(new Date());
        cart.setModifiedUser(uName);
        cart.setPrice(product.getPrice());
        int num = cartMapper.insertProduct(cart);
        //购物车加入数据
        Favority favority = new Favority();
        favority.setUid(uid);
        favority.setPid(pid);
        favority.setFid(fid);
        //收藏较少数据
        if (num==1){
            favorityMapper.deleteByPidUidFid(favority);
        }else {
            throw new CartNotFoundException("加入购物车异常");
        }
    }
}
