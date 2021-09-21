package com.bjpowernode.store.service;

import com.bjpowernode.store.domain.Address;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface AddressService {
    /**
     * 插入用户地址
     * @param address
     */
    void InsertAddress(Address address);

    List<Address> selectOutAddress(Integer id);

    void deleteAddressByAid(Integer aid, HttpSession session);

    void setDefault(Integer aid, Integer id,String username);
}
