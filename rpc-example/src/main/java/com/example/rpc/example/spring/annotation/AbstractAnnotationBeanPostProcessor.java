package com.example.rpc.example.spring.annotation;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class AbstractAnnotationBeanPostProcessor  extends
        InstantiationAwareBeanPostProcessorAdapter implements MergedBeanDefinitionPostProcessor, Ordered,
        BeanFactoryAware, BeanClassLoaderAware, EnvironmentAware, DisposableBean {
    private ClassLoader classLoader;
    private Environment environment;
    private final Class<? extends Annotation>[] annotationTypes;
    private int order = Ordered.LOWEST_PRECEDENCE;

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
    /**
     * @param annotationTypes the multiple types of {@link Annotation annotations}
     */
    public AbstractAnnotationBeanPostProcessor(Class<? extends Annotation>... annotationTypes) {
        Assert.notEmpty(annotationTypes, "The argument of annotations' types must not empty");
        this.annotationTypes = annotationTypes;
    }


    protected ClassLoader getClassLoader() {
        return classLoader;
    }


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {

    }

    @Override
    public void destroy() throws Exception {

    }

    @Override
    public void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class<?> beanType, String beanName) {

    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }
    protected Object getInjectedObject(AnnotationAttributes attributes, Object bean, String beanName, Class<?> injectedType,
                                       AnnotatedInjectElement injectedElement) throws Exception {
        return doGetInjectedBean(attributes, bean, beanName, injectedType, injectedElement);
    }
    protected abstract Object doGetInjectedBean(AnnotationAttributes attributes, Object bean, String beanName, Class<?> injectedType,
                                                AnnotatedInjectElement injectedElement) throws Exception;

    private static <T> Collection<T> combine(Collection<? extends T>... elements) {
        List<T> allElements = new ArrayList<T>();
        for (Collection<? extends T> e : elements) {
            allElements.addAll(e);
        }
        return allElements;
    }

    protected static class AnnotatedInjectionMetadata extends InjectionMetadata {

        private Class<?> targetClass;
        private final Collection<AnnotatedFieldElement> fieldElements;

        private final Collection<AbstractAnnotationBeanPostProcessor.AnnotatedMethodElement> methodElements;

        public AnnotatedInjectionMetadata(Class<?> targetClass, Collection<AbstractAnnotationBeanPostProcessor.AnnotatedFieldElement> fieldElements,
                                          Collection<AbstractAnnotationBeanPostProcessor.AnnotatedMethodElement> methodElements) {
            super(targetClass, combine(fieldElements, methodElements));
            this.targetClass = targetClass;
            this.fieldElements = fieldElements;
            this.methodElements = methodElements;
        }

        public Collection<AbstractAnnotationBeanPostProcessor.AnnotatedFieldElement> getFieldElements() {
            return fieldElements;
        }

        public Collection<AbstractAnnotationBeanPostProcessor.AnnotatedMethodElement> getMethodElements() {
            return methodElements;
        }

        //@Override // since Spring 5.2.4
        protected boolean needsRefresh(Class<?> clazz) {
            if (this.targetClass == clazz) {
                return false;
            }
            //IGNORE Spring CGLIB enhanced class
            if (targetClass.isAssignableFrom(clazz) &&  clazz.getName().contains("$$EnhancerBySpringCGLIB$$")) {
                return false;
            }
            return true;
        }
    }

    /**
     * {@link Annotation Annotated} {@link Method} {@link InjectionMetadata.InjectedElement}
     */
    protected class AnnotatedInjectElement extends InjectionMetadata.InjectedElement {

        protected final AnnotationAttributes attributes;

        protected volatile Object injectedObject;

        private Class<?> injectedType;

        protected AnnotatedInjectElement(Member member, PropertyDescriptor pd, AnnotationAttributes attributes) {
            super(member, pd);
            this.attributes = attributes;
        }

        @Override
        protected void inject(Object bean, String beanName, PropertyValues pvs) throws Throwable {

            Object injectedObject = getInjectedObject(attributes, bean, beanName, getInjectedType(), this);

            if (member instanceof Field) {
                Field field = (Field) member;
                ReflectionUtils.makeAccessible(field);
                field.set(bean, injectedObject);
            } else if (member instanceof Method) {
                Method method = (Method) member;
                ReflectionUtils.makeAccessible(method);
                method.invoke(bean, injectedObject);
            }
        }

        public Class<?> getInjectedType() throws ClassNotFoundException {
            if (injectedType == null) {
                if (this.isField) {
                    injectedType = ((Field) this.member).getType();
                }
                else if (this.pd != null) {
                    return this.pd.getPropertyType();
                }
                else {
                    Method method = (Method) this.member;
                    if (method.getParameterTypes().length > 0) {
                        injectedType = method.getParameterTypes()[0];
                    } else {
                        throw new IllegalStateException("get injected type failed");
                    }
                }
            }
            return injectedType;
        }

        public String getPropertyName() {
            if (member instanceof Field) {
                Field field = (Field) member;
                return field.getName();
            } else if (this.pd != null) {
                // If it is method element, using propertyName of PropertyDescriptor
                return pd.getName();
            } else {
                Method method = (Method) this.member;
                return method.getName();
            }
        }
    }

    protected class AnnotatedMethodElement extends AnnotatedInjectElement {

        protected final Method method;

        protected AnnotatedMethodElement(Method method, PropertyDescriptor pd, AnnotationAttributes attributes) {
            super(method, pd, attributes);
            this.method = method;
        }
    }

    public class AnnotatedFieldElement extends AnnotatedInjectElement {

        protected final Field field;

        protected AnnotatedFieldElement(Field field, AnnotationAttributes attributes) {
            super(field, null, attributes);
            this.field = field;
        }
    }
}
