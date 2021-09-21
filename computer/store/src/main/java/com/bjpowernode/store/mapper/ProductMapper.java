package com.bjpowernode.store.mapper;

import com.bjpowernode.store.domain.Product;

import java.util.List;

public interface ProductMapper {
    /**
     * 查找热销商品
     * @return
     */
    List<Product> findHotList();

    Product findById(Integer id);

}
