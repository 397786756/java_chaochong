package com.teeqee.spring.mode.service;

import com.alibaba.fastjson.JSONObject;
import com.teeqee.spring.mode.context.DataSourceContextAware;
import com.teeqee.spring.mode.strategy.CacheCollection;
import com.teeqee.spring.mode.strategy.DataSourceStrategy;
import com.teeqee.spring.result.Result;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


@Component
public class DataSourceService {

    @Autowired
    private DataSourceContextAware dataSourceContextAware;

    public Result connect(String dsType, JSONObject data, Channel channel) {
        DataSourceStrategy dataSourceChild = dataSourceContextAware.getStrategyInstance(dsType);
        if (dataSourceChild != null) {
            return dataSourceChild.connect(dsType,data,channel);
        }
        //这边可以返回自定义的Result结果集
        return new Result("error","undefined cmd");
    }

    public Object connectMethod(String dsType, JSONObject data, Channel channel) throws Exception {
        Class ds = CacheCollection.getDS(dsType);
        if (ds != null) {
            //TODO 能否根据方法获得当前类的实例化
           // Object invoke = CacheCollection.getMethod(dsType).invoke(ds.newInstance(), null, null, null);
           // return invoke;
            Method method = CacheCollection.getMethod(dsType);
            Class<?> declaringClass = method.getDeclaringClass();
            Object o = declaringClass.newInstance();
          return    CacheCollection.getMethod(dsType).invoke(o,null,null,null);
        }
        //这边可以返回自定义的Result结果集
        return new Result("error","undefined cmd");
    }
}
