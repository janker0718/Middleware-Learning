package com.example.rpc.example;

import com.example.rpc.example.spring.MainConfig;
import com.example.rpc.example.spring.demo.MyFactoryBeanService;
import com.example.rpc.example.spring.demo.TestService2;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainConfigTest {
    @Test
    public void testMyFactoryBeanService(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig.class);
        MyFactoryBeanService myFactoryBeanService = (MyFactoryBeanService) context.getBean("myFactoryBeanService");
        myFactoryBeanService.test();
    }
}
