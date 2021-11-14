package com.example.rpc.example.spring.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
public @interface MyRef {

    //分组
    String group() default "";

    //版本
    String version() default "";
}
