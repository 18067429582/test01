package com.bjpowernode.store.mapper;

import com.bjpowernode.store.domain.Address;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AddressMapper {
    int deleteByPrimaryKey(Integer aid);

    /**
     * 插入地址
     * @param record
     * @return
     */
    int insert(Address record);

    int insertSelective(Address record);

    Address selectByPrimaryKey(Integer aid);

    int updateByPrimaryKeySelective(Address record);

    int updateByPrimaryKey(Address record);

    /**
     * 查询地址总数量
     * @param uid
     * @return
     */
    int countNum(Integer uid);

    List<Address> selectOutAddress(Integer id);

    Address findLastModified (Integer uid);

    Address getDefault(Integer id);

    Integer updateDefaultByAid(Integer lastModifiedAid, String username, Date date);

    Address findByAid(Integer aid);

    Integer updateNonDefaultByUid(Integer id);
}