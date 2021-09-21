package com.bjpowernode.store;

import com.bjpowernode.store.domain.Address;
import com.bjpowernode.store.domain.Cart;
import com.bjpowernode.store.domain.District;
import com.bjpowernode.store.domain.User;
import com.bjpowernode.store.mapper.AddressMapper;
import com.bjpowernode.store.mapper.CartMapper;
import com.bjpowernode.store.mapper.DistrictMapper;
import com.bjpowernode.store.service.AddressService;
import com.bjpowernode.store.service.CartService;
import com.bjpowernode.store.service.IDistrictService;
import com.bjpowernode.store.service.execption.ServiceException;
import com.bjpowernode.store.mapper.UserDao;
import com.bjpowernode.store.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@SpringBootTest
class StoreApplicationTests {
    @Autowired
    private CartService cartServicr;
    @Autowired
    private DataSource d;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;
    @Autowired
    private DistrictMapper districtMapper;
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private AddressService addressService;
    @Autowired
    private IDistrictService iDistrictService;
    @Autowired
    private CartMapper cartMapper;
    @Test
    void contextLoads() {
    }
    @Test
    public void inse4rt() {
        Cart cart = new Cart();
        cart.setUid(1);
        cart.setPid(10000022);
        cart.setNum(2);
        cartServicr.addToCart(cart);
    }
    @Test
    public void inse34rt() {
        cartServicr.addProductNum(10000042,1);
    }

    @Test
    void get() throws SQLException {
        System.out.println(d.getConnection());
        /**
         * HikariProxyConnection@1360312263 wrapping com.mysql.cj.jdbc.ConnectionImpl@dd71b20
         * 数据库连接池：
         * 1,DBCP
         * 2,C3P0
         * 3,Hikari
         */
    }
    @Test
    public void insert() {
        User user = new User();
        user.setUsername("zhangsan");
        user.setPassword("123456");
       int num = userDao.insert(user);
        System.out.println(num);
    }

    @Test
    public void findByUsername() {
        User user = userDao.findByUsername("zhaowu");
        System.out.println(user);
    }



    @Test
    public void reg() {
        User user = new User();
        user.setUsername("zhaowu");
        user.setPassword("123456");
        try {
            userService.reg(user);
        }catch (ServiceException e){
            System.out.println(e.getMessage());
        }

    }@Test
    public void v() {
        User user = userService.verify("包安安","123123");
        System.out.println(user);
    }

@Test
public void v1() {
    userService.updatePassword(15,"11","123","11");
    System.out.println('e');
}

    @Test
    public void v3() {
        User user = new User();
        user.setUsername("11");
        user.setPhone("112132231");
        user.setEmail("1231231232");
        user.setGender(1);
        java.util.Date now = new Date();
        user.setModifiedTime(now);
        userService.updateUserInformation(user);
        System.out.println(0);
    }
    @Test
    public void v11() {
        User user = userService.getUserById(11);
        System.out.println(user);
    }

    @Test
    public void v111() {
        User user = new User();
        user.setUsername("11");
        user.setUid(15);
        user.setAvatar("000000");
        userService.updateUserAvatar(user);
    }

    @Test
    public void v111q() {
        Address d = new Address();
        d.setUid(1);
        d.setName("安安");
        d.setCreatedTime(new Date());
        int v = addressMapper.insert(d);
        System.out.println(v);
    }

    @Test
    public void v111sq() {
        Integer i = addressMapper.countNum(1);
        System.out.println(i);
    }

    @Test
    public void v1113sq() {
        List<District> l = districtMapper.findByParent(86);
        for (District d:l
             ) {
            System.out.println(d);
        }
    }

    @Test
    public void v11113sq() {
        List<District> l = iDistrictService.getByParent(86);
        System.out.println(l);
    }

    @Test
    public void qv11113sq() {
        List<Address> l = addressMapper.selectOutAddress(1);
        System.out.println(l);
    }

    @Test
    public void qv111133sq() {
    }



}
