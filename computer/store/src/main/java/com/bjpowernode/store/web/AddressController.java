package com.bjpowernode.store.web;

import com.bjpowernode.store.domain.Address;
import com.bjpowernode.store.domain.User;
import com.bjpowernode.store.service.AddressService;
import com.bjpowernode.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController extends BaseController{
    @Autowired
    private AddressService addressService;

    @RequestMapping("add_new_address")
    public JsonResult<Address> address(Address address, HttpSession session){
        User user = (User) session.getAttribute("user");
        Integer id = user.getUid();
        String username = user.getUsername();
        address.setUid(id);
        address.setCreatedUser(username);
        address.setModifiedUser(username);
        addressService.InsertAddress(address);
        return new JsonResult<>(OK);
    }

    @RequestMapping("show_address")
    public JsonResult<List<Address>> showAddress(HttpSession session){
        User user = (User) session.getAttribute("user");
        Integer id = user.getUid();
        List<Address> data = addressService.selectOutAddress(id);
        return new JsonResult<>(OK,data);
    }

    @RequestMapping("{aid}/delete_address")
    public JsonResult<List<Address>> deleteAddress(@PathVariable("aid") Integer aid, HttpSession session){
        addressService.deleteAddressByAid(aid,session);
        return new JsonResult<>(OK);
    }

    @RequestMapping("{aid}/default_address")
    public JsonResult<List<Address>> isDefaultAddress(@PathVariable("aid")Integer aid,HttpSession session){

        User user = (User) session.getAttribute("user");
        Integer id = user.getUid();
        String username = user.getUsername();
        addressService.setDefault(aid,id,username);
        return new JsonResult<>(OK);
    }
}
