package com.teeqee.spring.mode.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 功能描述: 模块下的方法映射
 * @author :zsj
 * @Date  2020-04-21 上午 09:11
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Dispather {
    //策略名
    String value() default "cmd";
}
