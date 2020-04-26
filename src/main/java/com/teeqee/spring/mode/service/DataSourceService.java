package com.teeqee.spring.mode.service;

import com.alibaba.fastjson.JSONObject;
import com.teeqee.net.handler.AbstractSession;
import com.teeqee.spring.mode.context.DataSourceContextAware;
import com.teeqee.spring.dispatcher.DataSourceStrategy;
import com.teeqee.spring.result.Result;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


@Component
public class DataSourceService {

    private final DataSourceContextAware dataSourceContextAware;

    @Autowired
    public DataSourceService(DataSourceContextAware dataSourceContextAware) {
        this.dataSourceContextAware = dataSourceContextAware;
    }

    public Object connect(String dsType, JSONObject data, AbstractSession channel)  {
        //加入泛型
        Class<?> aClass = dataSourceContextAware.getStrategyInstance(dsType);
        //判断一下会话的状态
        if (aClass != null&&data!=null&&channel.getChannel().isOpen()) {
            try {
                //获取接口
                Method method = aClass.getMethod("connect", String.class, JSONObject.class, AbstractSession.class);
                //返回一个实例化对象
                Object obj = aClass.newInstance();
                return method.invoke(obj, dsType, data, channel);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
            }
        }
        //这边可以返回自定义的Result结果集
        return new Result("error","undefined cmd");
    }

}
