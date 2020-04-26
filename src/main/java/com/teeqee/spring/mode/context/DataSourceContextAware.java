package com.teeqee.spring.mode.context;

import com.teeqee.spring.mode.strategy.CacheCollection;
import com.teeqee.spring.dispatcher.DataSourceStrategy;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 使用已被spring实例化的bean
 */
@Component
public class DataSourceContextAware implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @SuppressWarnings(value = {"unchecked"})
    public Class getStrategyInstance(String dsType) {
        return CacheCollection.getDS(dsType);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
