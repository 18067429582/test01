package com.test.mybatisplus;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.test.mybatisplus.domain.User;
import com.test.mybatisplus.mapper.UserMapper;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
class MybatisPlusApplicationTests {

    @Resource
    private UserMapper userMapper;
    @Test
    void contextLoads() {

        //参数是一个wrapper，条件构造器，没有条件就是null
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    /**
     * 插入操作
     */
    @Test
    public void insert(){
        User user = new User();
        user.setAge(90);
        user.setName("安安");
        user.setEmail("123@qq.com");
        int insert = userMapper.insert(user);
    }

    /**
     * 测试乐观锁
     */
    @Test
    public void local(){
        User user = userMapper.selectById(1L);
        user.setName("安安");
        user.setEmail("123@qq.com");
        userMapper.updateById(user);
    }

    /**
     * 测试条件查询,单个查询
     */

    @Test
    public void testSelectById(){
        User user = userMapper.selectById(1L);
        System.out.println(user);
    }

    /**
     * 测试条件查询,多个查询
     */

    @Test
    public void testSelectByEachId(){
       /* List list = new ArrayList();
        list.add(1L);
        list.add(2L);
        List<User> user = userMapper.selectBatchIds(list);*/
        List<User> user = userMapper.selectBatchIds(Arrays.asList(1,2,3));

        user.forEach(System.out::println);
    }

    @Test
    public void testSelectByMap(){
        HashMap<String, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("name","安安");
        List<User> user = userMapper.selectByMap(objectObjectHashMap);
        user.forEach(System.out::println);
    }


    /**
     * 测试分页查询
     */
    @Test
    public void testSelecPage(){
        Page<User> page = new Page<>(1,2);
        IPage<User> userIPage = userMapper.selectPage(page, null);
        page.getRecords().forEach(System.out::println);
    }


}
