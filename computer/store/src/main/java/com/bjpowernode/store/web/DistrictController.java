package com.bjpowernode.store.web;

import com.bjpowernode.store.domain.District;
import com.bjpowernode.store.service.IDistrictService;
import com.bjpowernode.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("districts")
public class DistrictController extends BaseController {
    @Autowired
    private IDistrictService districtService;

    @GetMapping({"", "/"})
    //@RequestMapping(method={RequestMethod.GET})
    public JsonResult<List<District>> getByParent(Integer parent) {
        System.out.println(parent);
        List<District> data = districtService.getByParent(parent);
        return new JsonResult<>(OK, data);
    }
}
