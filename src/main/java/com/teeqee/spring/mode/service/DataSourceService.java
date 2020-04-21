package com.teeqee.spring.mode.service;

import com.alibaba.fastjson.JSONObject;
import com.teeqee.net.handler.AbstractSession;
import com.teeqee.spring.mode.context.DataSourceContextAware;
import com.teeqee.spring.dispatcher.DataSourceStrategy;
import com.teeqee.spring.result.Result;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class DataSourceService {

    private final DataSourceContextAware dataSourceContextAware;

    @Autowired
    public DataSourceService(DataSourceContextAware dataSourceContextAware) {
        this.dataSourceContextAware = dataSourceContextAware;
    }

    public Result connect(String dsType, JSONObject data, AbstractSession channel) {
        DataSourceStrategy dataSourceChild = dataSourceContextAware.getStrategyInstance(dsType);
        if (dataSourceChild != null) {
            //获取接口
            return dataSourceChild.connect(dsType,data,channel);
        }else {
            //获取自定义的方法
        }
        //这边可以返回自定义的Result结果集
        return new Result("error","undefined cmd");
    }

}
