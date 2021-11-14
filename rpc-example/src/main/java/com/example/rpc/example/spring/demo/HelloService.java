package com.example.rpc.example.spring.demo;

import com.example.rpc.example.spring.annotation.MyRef;

import com.example.rpc.example.spring.api.DemoService;
import org.springframework.stereotype.Component;

@Component
public class HelloService {
    @MyRef(version = "version1", group = "test1")
    public DemoService demoService;

    public String test(String arg) {
        return demoService.sayName(arg);
    }
}
