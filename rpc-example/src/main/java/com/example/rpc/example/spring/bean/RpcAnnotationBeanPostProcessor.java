package com.example.rpc.example.spring.bean;

import com.example.rpc.example.spring.annotation.MyRef;
import com.example.rpc.example.spring.proxy.MyRefProxy;
import com.example.rpc.example.spring.util.AnnotationUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Map;

import static org.springframework.util.StringUtils.hasText;

@Component
public class RpcAnnotationBeanPostProcessor implements BeanPostProcessor {

    //bean初始化后置处理器
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> targetClass = bean.getClass();
        Field[] declaredFields = targetClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            MyRef myRef = declaredField.getAnnotation(MyRef.class);
            if (myRef != null) {
                MyRefProxy myRefProxy = new MyRefProxy(myRef.group(),myRef.version());
                Object clientProxy = myRefProxy.getProxy(declaredField.getType());
                declaredField.setAccessible(true);
                try {
                    declaredField.set(bean, clientProxy);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

        }
        return bean;

    }
}
