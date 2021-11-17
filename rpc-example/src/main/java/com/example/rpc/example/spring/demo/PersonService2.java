package com.example.rpc.example.spring.demo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
public class PersonService2 implements ApplicationContextAware {
    private ApplicationContext applicationContext;



    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
    public void sout(){
        HelloService helloService = (HelloService) applicationContext.getBean("helloService");
        System.out.println(helloService.test("haha"));
    }

}
