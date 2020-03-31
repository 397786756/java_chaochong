package com.teeqee.spring.mode.strategy.annotation;

import java.util.Map;

public interface DataSourceStrategy {

    //每个策略的逻辑实现
    Map<String, Object> connect(Map<String, String> params);
}
