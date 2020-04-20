package com.teeqee.spring.mode.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSourceType {
    //策略名
    String value() default "cmd";

    //自定义的模块
    String model() default "模块";
}