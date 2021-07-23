package com.bjpowernode.springboot.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class controller {
    @RequestMapping(value = "/say")
    public @ResponseBody String say(){
        return "你好";
    }
}
