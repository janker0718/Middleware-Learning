package com.example.rpc.example.spring.demo;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

//@Component
public class Test4Service implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("属性设置后执行方法");
    }
}
