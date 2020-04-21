package com.teeqee.spring.mode.strategy;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


public class CacheCollection {

    @Resource
    private static Map<String, Class> ALL_DATA_SOURCE;

    static {
        ALL_DATA_SOURCE = new HashMap<>(128);
    }
    /**
     * 根据策略名获取 Class
     * @param dsType
     * @return
     */
    public static Class getDS(String dsType) {
        return ALL_DATA_SOURCE.get(dsType);
    }
    /**
     * 策略名为 key,Class 为 value
     */
    public static void putClass(String dsType,Class clazz){
        ALL_DATA_SOURCE.put(dsType,clazz);
    }
}
