package com.example.rpc.example.spring.demo;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component
public class MyFactoryBean implements FactoryBean {
    @Override
    public Object getObject() throws Exception {
        Integer a = 1;
        Integer b = 2;
        return a+b;
    }

    @Override
    public Class<?> getObjectType() {
        return Integer.class;
    }
}
