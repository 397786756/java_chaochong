package com.teeqee.spring.mode.strategy.annotation;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class DataSourceContextAware implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public DataSourceStrategy getStrategyInstance(String dsType) {
        Class clazz = CacheCollection.getDS(dsType);
        return (DataSourceStrategy) applicationContext.getBean(clazz);
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
