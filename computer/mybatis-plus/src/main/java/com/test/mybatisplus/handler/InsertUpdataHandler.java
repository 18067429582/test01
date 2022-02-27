package com.test.mybatisplus.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class InsertUpdataHandler implements MetaObjectHandler {

    //自动填充时间的handler
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("insert start");
        this.setFieldValByName("creatTime",new Date(),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }
}
