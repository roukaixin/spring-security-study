package com.kai.controller;

import com.kai.HttpIgnoreAuthentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * hello 控制层
 *
 * @author 不北咪
 * @date 2023/4/23 23:35
 */
@RestController
public class HelloController {

    @RequestMapping(value = {"/hello","/helloabc"}, method = {RequestMethod.DELETE, RequestMethod.GET})
    @HttpIgnoreAuthentication
    public String hello(){
        return "hello Spring Security";
    }

    @RequestMapping(value = {"/hello1","/hello1abc"})
    public String hello1(){
        return "hello Spring Security";
    }
}
