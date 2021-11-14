package com.example.rpc.example;

import com.example.rpc.example.spring.MainConfig;
import com.example.rpc.example.spring.demo.HelloService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MyRefBeanPostProcessorTest {

    @Test
    public void testRpcAnnotationBeanPostProcessor(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig.class);
        HelloService helloService = (HelloService) context.getBean("helloService");
        System.out.printf(helloService.test("11"));
    }
}
