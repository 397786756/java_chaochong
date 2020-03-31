package com.teeqee.spring.mode.strategy.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DataSourceService {

    @Autowired
    private DataSourceContextAware dataSourceContextAware;

    public Map<String, Object> connect(String dsType, Map<String, String> map) {
        DataSourceStrategy dataSourceChild = dataSourceContextAware.getStrategyInstance(dsType);
        return dataSourceChild.connect(map);
    }
}
