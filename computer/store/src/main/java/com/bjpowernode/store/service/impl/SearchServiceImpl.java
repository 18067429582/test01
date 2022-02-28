package com.bjpowernode.store.service.impl;

import com.bjpowernode.store.domain.Product;
import com.bjpowernode.store.mapper.ProductMapper;
import com.bjpowernode.store.service.IProductService;
import com.bjpowernode.store.service.SearchService;
import com.bjpowernode.store.service.execption.ProductNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/** 处理商品数据的业务层实现类 */
@Service
public class SearchServiceImpl implements SearchService {
    @Resource
    private ProductMapper productMapper;

   /* @Override
    public List<Product> findHotList() {
        List<Product> list = productMapper.findHotList();
        for (Product product : list) {
            product.setPriority(null);
            product.setCreatedUser(null);
            product.setCreatedTime(null);
            product.setModifiedUser(null);
            product.setModifiedTime(null);
        }
        return list;
    }*/

    @Override
    public List<Product> findProductList(String pname) {
        List<Product> list = productMapper.findProductListByPname(pname);
        if (list == null ){
            throw new ProductNotFoundException("不好意思没找到该商品");
        }
        for (Product product : list) {
//            System.out.println(product.toString());
            product.setPriority(null);
            product.setCreatedUser(null);
            product.setCreatedTime(null);
            product.setModifiedUser(null);
            product.setModifiedTime(null);
        }
        return list;
    }
}
