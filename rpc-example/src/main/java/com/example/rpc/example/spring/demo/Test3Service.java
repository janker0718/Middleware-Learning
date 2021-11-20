package com.example.rpc.example.spring.demo;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Test3Service {
//    @PostConstruct
    public void init(){
        System.out.println("初始化ing");
    }

}
