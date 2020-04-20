package com.teeqee.spring.mode.context;

import com.alibaba.fastjson.JSONObject;
import com.teeqee.spring.mode.strategy.CacheCollection;
import com.teeqee.spring.mode.strategy.DataSourceStrategy;
import io.netty.channel.Channel;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 使用已被spring实例化的bean
 */
@Component
public class DataSourceContextAware implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @SuppressWarnings(value = {"unchecked"})
    public DataSourceStrategy getStrategyInstance(String dsType) {
        Class clazz = CacheCollection.getDS(dsType);
        if (clazz != null) {
            return (DataSourceStrategy) applicationContext.getBean(clazz);
        }
        return null;
    }


    @SuppressWarnings(value = {"unchecked"})
    public Object getDispatherMethod( String cmd)throws Exception{
        return CacheCollection.getMethod(cmd);

    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
