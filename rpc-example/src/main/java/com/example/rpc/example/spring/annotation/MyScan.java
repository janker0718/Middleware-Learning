package com.example.rpc.example.spring.annotation;

import com.example.rpc.example.spring.bean.MyScannerRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Import(MyScannerRegistrar.class)
@Documented
public @interface MyScan {
    String[] basePackage();
}
