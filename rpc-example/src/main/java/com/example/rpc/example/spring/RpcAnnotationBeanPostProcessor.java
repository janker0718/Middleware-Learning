package com.example.rpc.example.spring;

import com.example.rpc.example.spring.annotation.AbstractAnnotationBeanPostProcessor;
import com.example.rpc.example.spring.annotation.RpcRef;
import com.example.rpc.example.spring.bean.RpcRefBean;
import com.example.rpc.example.spring.util.AnnotationUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationAttributes;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.springframework.util.StringUtils.hasText;

public class RpcAnnotationBeanPostProcessor extends AbstractAnnotationBeanPostProcessor implements ApplicationContextAware, BeanFactoryPostProcessor {

    /**
     * Cache size
     */
    private static final int CACHE_SIZE = Integer.getInteger("referenceAnnotationBeanPostProcessor" + ".cache.size", 32);

    private final ConcurrentMap<String, RpcRefBean<?>> referenceBeanCache =
            new ConcurrentHashMap<>(CACHE_SIZE);

    public RpcAnnotationBeanPostProcessor() {
        super(RpcRef.class);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }

    @Override
    protected Object doGetInjectedBean(AnnotationAttributes attributes, Object bean, String beanName, Class<?> injectedType, AnnotatedInjectElement injectedElement) throws Exception {
        String referencedBeanName = buildReferencedBeanName(attributes, injectedType);
        return null;
    }

    private String buildReferencedBeanName(AnnotationAttributes attributes, Class<?> interfaceClass) {
        StringBuilder beanNameBuilder = new StringBuilder("ServiceBean");
        beanNameBuilder.append(":").append(interfaceClass.getName());
        String rawBeanName = beanNameBuilder.toString();
        // Resolve placeholders
        return rawBeanName;
    }

    private String generateReferenceBeanName(AnnotationAttributes attributes, Class<?> interfaceClass) {
        StringBuilder beanNameBuilder = new StringBuilder("@Reference");

        if (!attributes.isEmpty()) {
            beanNameBuilder.append('(');
            for (Map.Entry<String, Object> entry : attributes.entrySet()) {
                beanNameBuilder.append(entry.getKey())
                        .append('=')
                        .append(entry.getValue())
                        .append(',');
            }
            // replace the latest "," to be ")"
            beanNameBuilder.setCharAt(beanNameBuilder.lastIndexOf(","), ')');
        }

        beanNameBuilder.append(" ").append(interfaceClass.getName());

        return beanNameBuilder.toString();
    }

    private String getReferenceBeanName(AnnotationAttributes attributes, Class<?> interfaceClass) {
        // id attribute appears since 2.7.3
        String beanName = AnnotationUtils.getAttribute(attributes, "id");
        if (!hasText(beanName)) {
            beanName = generateReferenceBeanName(attributes, interfaceClass);
        }
        return beanName;
    }
}
