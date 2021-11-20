package com.example.rpc.example.spring.demo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

@Component
public class MyFactoryBeanService implements BeanFactoryAware {
    private BeanFactory beanFactory;
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public void test(){
        Object myFactoryBean = beanFactory.getBean("myFactoryBean");
        Class<?> aClass = myFactoryBean.getClass();
        System.out.println(myFactoryBean);
        System.out.println(aClass);
        Object bean = beanFactory.getBean("&myFactoryBean");
        Class<?> aClass1 = bean.getClass();
        System.out.println(bean);
        System.out.println(aClass1);
    }
}
