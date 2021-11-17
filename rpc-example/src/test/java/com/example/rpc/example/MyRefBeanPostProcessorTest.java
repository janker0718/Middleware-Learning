package com.example.rpc.example;

import com.example.rpc.example.spring.MainConfig;
import com.example.rpc.example.spring.demo.HelloService;
import com.example.rpc.example.spring.demo.PersonService;
import com.example.rpc.example.spring.demo.PersonService2;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MyRefBeanPostProcessorTest {

    @Test
    public void testRpcAnnotationBeanPostProcessor(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig.class);
//        HelloService helloService = (HelloService) context.getBean("helloService");
//        System.out.printf(helloService.test("11"));
//        PersonService personService = (PersonService) context.getBean("personService");
        PersonService2 personService = (PersonService2) context.getBean("personService2");
        personService.sout();
    }
}
