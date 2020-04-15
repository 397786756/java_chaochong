package com.teeqee.spring.mode.strategy.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DataSourceService {

    @Autowired
    private DataSourceContextAware dataSourceContextAware;

    public Map<String, Object> connect(String dsType, Map<String, String> map) {
        DataSourceStrategy dataSourceChild = dataSourceContextAware.getStrategyInstance(dsType);
        if (dataSourceChild != null) {
            return dataSourceChild.connect(map);
        }
        //这边可以返回自定义的Result结果集
        return new HashMap<>(1);
    }
}
