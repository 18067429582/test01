package com.bjpowernode.store.mapper;

import com.bjpowernode.store.domain.User;

import java.util.Date;

/**
 * 用户模块接口
 */
public interface UserDao {
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

    int selectUser(User user);

    int updatePassword(Integer id, String username, String newPassword, Date now);

    Integer updateUserInformation(User user);

    User getUserById(Integer id);

    Integer updateUserAvatar(User user);
}
