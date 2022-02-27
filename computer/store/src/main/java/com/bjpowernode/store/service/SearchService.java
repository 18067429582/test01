package com.bjpowernode.store.service;

import com.bjpowernode.store.domain.Order;
import com.bjpowernode.store.domain.Product;

import java.util.List;

public interface SearchService {
    List<Product> findProductList(String pname);
}
