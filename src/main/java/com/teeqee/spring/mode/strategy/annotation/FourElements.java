package com.teeqee.spring.mode.strategy.annotation;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@DataSourceType("fourElements")
@Service
public class FourElements implements DataSourceStrategy {
    @Override
    public Map<String, Object> connect(Map<String, String> params) {
        //do something....
        //返回结果
        //do something....
        //返回结果
        HashMap<String, Object> map = new HashMap<>();
        map.put("type", "四要素");
        map.put("status", "success");
        return map;
    }
}
