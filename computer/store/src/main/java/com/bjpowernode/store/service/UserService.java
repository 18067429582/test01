package com.bjpowernode.store.service;

import com.bjpowernode.store.domain.User;

public interface UserService {
    /**
     * 插入用户数据
     * @param user 用户数据
     * @return 受影响的行数
     */
    Integer insert(User user);
    /**
     * 根据用户名查询用户数据
     * @param username 用户名
     * @return 匹配的用户数据，如果没有匹配的数据，则返回null
     */
    User findByUsername(String username);

    /**
     * 注册方法
     * @param user
     */
    void reg(User user);

    /**
     * 用户登录功能
     * @return
     */
    User verify(String username,String password);

    /**
     * 用户密码的修改
     * @param id
     * @param username
     * @param oldPassword
     * @param newPassword
     */
    void updatePassword(Integer id, String username, String oldPassword, String newPassword);

    /**
     * 用户个人资料的更新
     * @param user
     */
    void updateUserInformation(User user);

    /**
     * 根据用户id查询以后信息
     * @param id
     * @return
     */
    User getUserById(Integer id);

    /**
     * 用户头像的更新
     * @param user
     */
    void updateUserAvatar(User user);
}
