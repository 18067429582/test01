package com.test.mybatisplus.config;


import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@MapperScan(basePackages = "com.test.mybatisplus.mapper")//扫描mapper类，乐观锁
@Component
public class Page {
    // 旧版
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        //分页插件
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        return paginationInterceptor;
    }
}
