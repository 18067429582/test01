package com.bjpowernode.store.mapper;


import com.bjpowernode.store.domain.Cart;
import com.bjpowernode.store.domain.Favority;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户模块接口
 */
@Repository
public interface FavorityMapper {
    Favority selectProductById(Integer pid, Integer uid);


    int insertFavority(Favority favority);

    List<Favority> findByUid(Integer uid);


    int deleteByPidUidFid(Favority favority);
}
