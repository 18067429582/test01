package com.bjpowernode.store.service.impl;

import com.bjpowernode.store.domain.User;
import com.bjpowernode.store.service.execption.*;
import com.bjpowernode.store.mapper.UserDao;
import com.bjpowernode.store.service.UserService;
import com.bjpowernode.store.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;
    @Override
    public Integer insert(User user) {
        return userDao.insert(user);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public void reg(User user) {
        // 根据参数user对象获取注册的用户名
        String username = user.getUsername();
        // 调用持久层的User findByUsername(String username)方法，根据用户名查询用户数据
        User result = userDao.findByUsername(username);
        // 判断查询结果是否不为null
        if (result != null) {
            // 是：表示用户名已被占用，则抛出UsernameDuplicateException异常
            throw new UsernameDuplicateException("尝试注册的用户名[" + username + "]已经被占用");
        }

        // 创建当前时间对象
        Date now = new Date();
        // 补全数据：加密后的密码
        String salt = UUID.randomUUID().toString().toUpperCase();
        String md5Password = UUIDUtil.getUUID(user.getPassword(), salt);
        user.setPassword(md5Password);
        // 补全数据：盐值
        user.setSalt(salt);
        // 补全数据：isDelete(0)
        user.setIsDelete(0);
        // 补全数据：4项日志属性
        user.setCreatedUser(username);
        user.setCreatedTime(now);
        user.setModifiedUser(username);
        user.setModifiedTime(now);

        // 表示用户名没有被占用，则允许注册
        // 调用持久层Integer insert(User user)方法，执行注册并获取返回值(受影响的行数)
        Integer rows = userDao.insert(user);
        // 判断受影响的行数是否不为1
        if (rows != 1) {
            // 是：插入数据时出现某种错误，则抛出InsertException异常
            throw new InsertException("添加用户数据出现未知错误，请联系系统管理员");
        }
    }

    @Override
    public User verify(String username,String password) {
        User user = userDao.findByUsername(username);
        if (user==null){
            throw new UserNotFoundException("用户名不存在");
        }
        //密码验证
        String password1 = user.getPassword();
        String sale = user.getSalt();
        password = UUIDUtil.getUUID(password,sale);
        if (!password.equals(password1)){
            throw new PasswordNotMatchException("密码错误");
        }
        if (user.getIsDelete()==1){
            throw new UserNotFoundException("该用户已注销");
        }
        //返回的user对象是为了其他页面的数据展示使用的，只有三个字段有用，id，username，avatar（头像）
        //为了安全，并且数据量变小(效率高)，所以使用新的对象传输数据
        User vo = new User();
        vo.setUid(user.getUid());
        vo.setUsername(user.getUsername());
        vo.setAvatar(user.getAvatar());
        return vo;
    }

    @Override
    public void updatePassword(Integer id,
                               String username,
                               String oldPassword,
                               String newPassword) {

        User user = userDao.findByUsername(username);
        if (user==null && user.getIsDelete()==1){
            throw new UserNotFoundException("用户没有找到");
        }
        String oldSale = user.getSalt();
        oldPassword = UUIDUtil.getUUID(oldPassword,oldSale);
        //获取盐值,进行三次加密
        newPassword = UUIDUtil.getUUID(newPassword,oldSale);
        // 创建当前时间对象
        Date now = new Date();
        int count = 0;
        if (oldPassword.equals(user.getPassword())){
            count = userDao.updatePassword(id, username,newPassword,now);
        }else if (count!=1){
            throw new UpdateException("更新密码时常说的异常，请联系管理员");
        }else {
            throw new PasswordNotMatchException("旧密码错误");
        }
    }

    @Override
    public void updateUserInformation(User user) {
        User u = userDao.getUserById(user.getUid());
        if (u.getIsDelete() == 1 || u == null) {
            throw new UserNotFoundException("用户信息已经删除");
        }else if (user.getPhone().equals(u.getPhone())|| user.getEmail().equals(u.getEmail())){

            throw new DuplicateInformationException("电话号码或邮箱地址和修改之前重复");
        }
            if (user.getEmail() == "" || user.getGender() == null || user.getPhone() == "") {
                throw new EmptyDataException("数据不能为空");
            }
            Date now = new Date();
            user.setModifiedTime(now);
            int count = userDao.updateUserInformation(user);
            if (count != 1) {
                throw new UpdateException("更新数据异常");
            }
    }

    @Override
    public User getUserById(Integer id) {
        return userDao.getUserById(id);
    }

    /**
     * 传头像
     * @param user
     */
    @Override
    public void updateUserAvatar(User user) {
        User u = userDao.getUserById(user.getUid());
        if (u.getIsDelete() == 1 || u == null) {
            throw new UserNotFoundException("用户信息已经删除");
        }
            Date now = new Date();
            user.setModifiedTime(now);
            int count = userDao.updateUserAvatar(user);
            if (count != 1) {
                throw new UpdateException("更新头像异常");
        }
    }
}
