package com.example.rpc.example.spring.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;

public class RpcRefBean<T> implements FactoryBean,
        ApplicationContextAware, InitializingBean, DisposableBean {
    protected Boolean init;

    private transient volatile T ref;


    private transient volatile boolean destroyed;

    private transient volatile boolean initialized;

    @Override
    public void destroy() throws Exception {

    }

    @Override
    public Object getObject() throws Exception {
        return get();
    }

    public synchronized T get() {
        if (destroyed) {
//            throw new IllegalStateException("The invoker of ReferenceConfig(" + url + ") has already destroyed!");
        }
        if (ref == null) {
            init();
        }
        return ref;
    }

    private void init() {
        if (initialized) {
            return;
        }
        Map<String, String> map = new HashMap<String, String>();
        //创建动态代理对象
//        ref = createProxy(map);
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //准备rpc配置bean
        if (init == null) {
            init = false;
        }
        getObject();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }
}
