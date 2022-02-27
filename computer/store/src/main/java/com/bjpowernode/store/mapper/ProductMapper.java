package com.bjpowernode.store.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bjpowernode.store.domain.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductMapper extends BaseMapper<Product> {
    /**
     * 查找热销商品
     * @return
     */
    List<Product> findHotList();

    Product findById(Integer id);

    List<Product> findNewList();

    List<Product> findProductListByPname(String pname);

    void editNum(Integer pNum, Integer num, Integer pid);
}
