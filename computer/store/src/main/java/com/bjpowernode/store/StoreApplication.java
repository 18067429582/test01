package com.bjpowernode.store;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.bjpowernode.store.mapper")
public class StoreApplication {

    private static Logger log = LoggerFactory.getLogger(StoreApplication.class.getName());
    public static void main(String[] args) {
        SpringApplication.run(StoreApplication.class, args);
        log.info("系统成功启动");
    }
}
