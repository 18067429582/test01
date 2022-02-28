package com.bjpowernode.store.service.impl;

import com.bjpowernode.store.domain.Address;
import com.bjpowernode.store.domain.User;
import com.bjpowernode.store.mapper.AddressMapper;
import com.bjpowernode.store.service.AddressService;
import com.bjpowernode.store.service.IDistrictService;
import com.bjpowernode.store.service.execption.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private static Logger log = LoggerFactory.getLogger(AddressServiceImpl.class.getName());
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private IDistrictService districtService;

    //注入配置文件中的
    @Value("${user.address.max-count}")
    private Integer maxCount;

    @Override
    public void InsertAddress(Address address) {
        //查询用户地址数量，判断是否将该地址设置为默认值
        int count = addressMapper.countNum(address.getUid());
        if (count >= maxCount) {
            log.error("用户地址已经达到上限");
            throw new AddressCountLimitException("用户地址已经达到上限");
        }
        if (address.getName() != null && !address.getName().equals("")) {
            // 补全数据：省、市、区的名
            // 补全数据：省、市、区的名称
            String provinceName = districtService.getNameByCode(address.getProvinceCode());
            String cityName = districtService.getNameByCode(address.getCityCode());
            String areaName = districtService.getNameByCode(address.getAreaCode());
            address.setProvinceName(provinceName);
            address.setCityName(cityName);
            address.setAreaName(areaName);
            // 补全其他数据
            Integer IsDefault = count == 0 ? 1 : 0;
            address.setIsDefault(IsDefault);
            Date date = new Date();
            address.setCreatedTime(date);
            address.setModifiedTime(date);
            //调用插入地址的方法
            int num = addressMapper.insert(address);
            if (num != 1) {
                log.error("用户收货地址插入异常");
                throw new InsertException("用户收货地址插入异常");
            }
        }else {
            log.error("标星号的数据不能为空");
            throw new AddressNotFoundException("标星号的数据不能为空");
        }

    }

    @Override
    public List<Address> selectOutAddress(Integer id) {
        List<Address> list = addressMapper.selectOutAddress(id);
        if (list.size() == 0){
            log.error("该用户暂未添加地址");
            throw new InsertException("该用户暂未添加地址");
        }
        return list;
    }

    @Transactional
    @Override
    public void deleteAddressByAid(Integer aid, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Integer id = user.getUid();
        String username = user.getUsername();
        Address address = addressMapper.selectByPrimaryKey(Integer.valueOf(aid));
        if (address == null) {
            throw new AddressNotFoundException("用户收货地址没找到，请刷新重试或联系管理员");
        }
        if (!id.equals(address.getUid())) {
            throw new AccessDeniedException("非法用户访问");
        }
        int count = addressMapper.deleteByPrimaryKey(aid);
        if (count != 1) {
            throw new DeleteException("删除用户收货地址异常");
        }
        // 调用持久层的countByUid()统计目前还有多少收货地址
        Integer count1 = addressMapper.countNum(id);
        // 判断目前的收货地址的数量是否为0
        if (count1 == 0) {
            log.info("该用户：--->{}暂未添加收货地址",id);
            return;
        }
        //查看库中是否还有默认地址
        Address address1 = addressMapper.getDefault(id);
        if (address1 == null) {
            // 调用findLastModified()找出用户最近修改的收货地址数据
            Address lastModified = addressMapper.findLastModified(id);
            // 从以上查询结果中找出aid属性值
            Integer lastModifiedAid = lastModified.getAid();
            // 调用持久层的updateDefaultByAid()方法执行设置默认收货地址，并获取返回的受影响的行数
            Integer rows2 = addressMapper.updateDefaultByAid(lastModifiedAid, username, new Date());
            // 判断受影响的行数是否不为1
            if (rows2 != 1) {
                // 是：抛出UpdateException
                throw new UpdateException("更新收货地址数据时出现未知错误，请联系系统管理员");
            }
        }

    }

    @Transactional
    @Override
    public void setDefault(Integer aid, Integer id, String username) {
        // 根据参数aid，调用addressMapper中的findByAid()查询收货地址数据
        Address result = addressMapper.findByAid(aid);
        // 判断查询结果是否为null
        if (result == null) {
            // 是：抛出AddressNotFoundException
            throw new AddressNotFoundException("尝试访问的收货地址数据不存在");
        }

        // 判断查询结果中的uid与参数uid是否不一致(使用equals()判断)
        if (!result.getUid().equals(id)) {
            // 是：抛出AccessDeniedException
            throw new AccessDeniedException("非法访问的异常");
        }

        // 调用addressMapper的updateNonDefaultByUid()将该用户的所有收货地址全部设置为非默认，并获取返回受影响的行数
        Integer rows = addressMapper.updateNonDefaultByUid(id);
        // 判断受影响的行数是否小于1(不大于0)
        if (rows < 1) {
            // 是：抛出UpdateException
            throw new UpdateException("设置默认收货地址时出现未知错误[1]");
        }

        // 调用addressMapper的updateDefaultByAid()将指定aid的收货地址设置为默认，并获取返回的受影响的行数
        rows = addressMapper.updateDefaultByAid(aid, username, new Date());
        // 判断受影响的行数是否不为1
        if (rows != 1) {
            // 是：抛出UpdateException
            throw new UpdateException("设置默认收货地址时出现未知错误[2]");
        }
    }

    /*@Override
    public Address getByAid(Integer aid, Integer uid) {
        // 根据收货地址数据id，查询收货地址详情
        Address address = addressMapper.findByAid(aid);
        if (address == null) {
            throw new AddressNotFoundException("尝试访问的收货地址数据不存在");
        }
        if (!address.getUid().equals(uid)) {
            throw new AccessDeniedException("非法访问");
        }
        address.setProvinceCode(null);
        address.setCityCode(null);
        address.setAreaCode(null);
        address.setCreatedUser(null);
        address.setCreatedTime(null);
        address.setModifiedUser(null);
        address.setModifiedTime(null);
        return address;
    }*/
}
