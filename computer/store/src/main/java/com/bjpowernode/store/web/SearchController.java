package com.bjpowernode.store.web;

import com.bjpowernode.store.domain.Product;
import com.bjpowernode.store.service.IProductService;
import com.bjpowernode.store.service.SearchService;
import com.bjpowernode.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("search")
public class SearchController extends BaseController {
    @Autowired
    private SearchService searchService;

    @RequestMapping("showProductList")
    public JsonResult<List<Product>> getHotList(String search) {
        List<Product> data = searchService.findProductList(search);
        return new JsonResult<List<Product>>(OK, data);
    }
}
