package com.kai.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "hello spring security";
    }

    @GetMapping("/index")
    public String index(){
        return "hello index";
    }
}
