package com.example.rpc.example.spring.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyRefProxy implements InvocationHandler {

    private String group;
    private String version;
    public MyRefProxy(String group, String verion){
        this.group = group;
        this.version = verion;
    }

    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //代理方法
        String simpleName = method.getName();
        //全路径名字
        String name = method.getDeclaringClass().getName();
        for (Object arg : args) {
            name = name +":" + arg.toString();
        }
        name = name +":" + group;
        name = name +":" + version;
        return name;
    }
}
