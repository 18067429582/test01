package com.test.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.test.mybatisplus.domain.User;
import org.springframework.stereotype.Repository;

@Repository//表示是mapper层
public interface UserMapper extends BaseMapper<User> {

}
