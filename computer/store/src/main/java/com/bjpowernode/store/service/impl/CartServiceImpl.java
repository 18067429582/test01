package com.bjpowernode.store.service.impl;

import com.bjpowernode.store.domain.Cart;
import com.bjpowernode.store.domain.Product;
import com.bjpowernode.store.domain.User;
import com.bjpowernode.store.mapper.CartMapper;
import com.bjpowernode.store.mapper.ProductMapper;
import com.bjpowernode.store.service.CartService;
import com.bjpowernode.store.service.IProductService;
import com.bjpowernode.store.service.execption.*;
import com.bjpowernode.store.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/** 处理商品数据的业务层实现类 */
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private IProductService productService;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public void addToCart(Cart cart) {
        Cart result = cartMapper.selectProductById(cart.getPid(),cart.getUid());
        if (result != null){
            if ((result.getNum()+cart.getNum())>10){
               return;
            }
            result.setNum(cart.getNum()+result.getNum());
            result.setModifiedUser(cart.getModifiedUser());
            result.setModifiedTime(cart.getModifiedTime());
            cartMapper.updataCartNum(result);
        }else{
            // 调用productService.findById(pid)查询商品数据，得到商品价格
            Product product = productService.findById(cart.getPid());
            // 封装数据：price
            cart.setPrice(product.getPrice());
            int num = cartMapper.insertProduct(cart);
            if (num != 1) {
                throw new InsertException("购物车添加商品异常");
            }
        }
    }

    @Override
    public List<CartVO> getProductLists(HttpSession session) {
        User user = (User) session.getAttribute("user");
        Integer uid = user.getUid();
        List<CartVO> clist = new ArrayList<>();
        List<Cart> list = cartMapper.findByUid(uid);
        for (Cart l:list) {
            Product product = productMapper.findById(l.getPid());
            CartVO cartVO = new CartVO();
            cartVO.setCid(l.getCid());
            cartVO.setPid(product.getId());
            cartVO.setUid(uid);
            cartVO.setPrice(l.getPrice());
            cartVO.setNum(l.getNum());
            cartVO.setTitle(product.getTitle());
            cartVO.setRealPrice(l.getNum()*l.getPrice());
            cartVO.setImage(product.getImage());
            clist.add(cartVO);
        }
        return clist;
    }

    @Override
    public void addProductNum(Integer pid, Integer uid) {
        Cart cart = cartMapper.selectProductById(pid,uid);
        if (cart.getNum()+1>10){
            throw new ProductNotFoundException("宝贝不能再添加了，最多十个");
        }
        cart.setNum(cart.getNum()+1);
        int num = cartMapper.updataCartNum(cart);
        if (num!=1){
            throw new UpdateException("购物车点击按钮添加宝贝异常");
        }
    }

    @Override
    public void reduceProductNum(Integer pid, Integer uid) {
        Cart c = cartMapper.selectProductById(pid,uid);
        if (c.getNum()==1){
            throw new ProductNotFoundException("宝贝不能再减少了");
        }
        Cart cart = cartMapper.selectProductById(pid,uid);
        cart.setNum(cart.getNum()-1);
        int num = cartMapper.updataCartNum(cart);
        if (num!=1){
            throw new UpdateException("购物车点击按钮减少宝贝异常");
        }
    }

    @Override
    public void delCartItem(Integer pid, Integer cid) {
        Cart cart = cartMapper.selectProductByPidCid(pid,cid);
        if (cart==null){
            throw new ProductNotFoundException("该商品不存在，请刷新重试");
        }
        int count = cartMapper.delCartItem(pid,cid);
        if (count != 1){
            throw new DeleteException("删除失败，请联系管理员");
        }
    }

    @Override
    public List<CartVO> getVOByCids(Integer uid, Integer[] cids) {
        List<CartVO> list = null;
        if (cids!=null) {
            list = cartMapper.findVOByCids(cids);
            /**
             for (CartVO cart : list) {
             if (!cart.getUid().equals(uid)) {
             list.remove(cart);
             }
             }
             */
            for (CartVO c : list) {
                c.setRealPrice(c.getPrice() * c.getNum());
            }
        }else {
            throw new EmptyDataException("请选择要购买的商品");
        }
        return list;
    }

    @Override
    public void delete(String[] cids, Integer uid) {
        int num = 0;
        for (int i = 0; i < cids.length ; i++) {
            int count = cartMapper.selectCartCount(cids[i],uid);
            if (count==1){
                num = num+1;
            }
        }
        if (num != cids.length){
            throw  new DeleteException("某些商品未找到，请刷新重试");
        }
        int rows = 0;
        for (int i = 0; i < cids.length ; i++) {
            int count = cartMapper.delete(cids[i],uid);
            if (count==1){
                rows = rows+1;
            }
        }
        if (rows != cids.length){
            throw  new DeleteException("删除异常，请联系管理员");
        }
    }

    @Override
    public CartVO showPrice(String[] cids, Integer uid) {
        long price = 0;
        int num = 0;
        CartVO cartVO = new CartVO();
        if (cids!=null) {
            if (cids.length != 0) {
                for (int i = 0; i < cids.length; i++) {
                    Cart cart = cartMapper.selectCartByCidUid(cids[i].trim(), uid);
                    price = price + cart.getNum() * cart.getPrice();
                    num = num + cart.getNum();
                }
                cartVO.setNum(num);
                cartVO.setRealPrice(price);
            }
        }
        return cartVO;
    }
}
