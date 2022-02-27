package com.test.mybatisplus.domain;

import com.baomidou.mybatisplus.annotation.*;

import javax.jws.Oneway;
import java.lang.reflect.Type;
import java.util.Date;


@lombok.Data
@TableName("user")
public class User {
    /*雪花算法，生成id*/
//    @TableId(type = IdType.AUTO)//表示主键自增,注意数据库的主键自增也需要勾选+
    @TableId(type = IdType.UUID)//表示主键自增,注意数据库的主键自增也需要勾选+
    private String id;
    private String name;
    private Integer age;
    private String email;

    @TableField(fill = FieldFill.INSERT)
    private Date creatTime;

    @Version//乐观锁注解，需要注册主键的
    private Integer Version;
}

