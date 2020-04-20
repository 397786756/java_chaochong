package com.teeqee.spring.mode.service;

import com.alibaba.fastjson.JSONObject;
import com.teeqee.spring.mode.context.DataSourceContextAware;
import com.teeqee.spring.mode.strategy.DataSourceStrategy;
import com.teeqee.spring.result.Result;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


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
}
