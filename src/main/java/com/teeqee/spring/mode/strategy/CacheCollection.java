package com.teeqee.spring.mode.strategy;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class CacheCollection {

    private static Map<String, Class> ALL_DATA_SOURCE;

    static {
        ALL_DATA_SOURCE = new HashMap<>(100);
    }

    private static Map<String, Method> ALL_DATA_METHOD;

    static {
        ALL_DATA_METHOD = new HashMap<>(100);
    }
    /**
     * 根据策略名获取 Class
     * @param dsType
     * @return
     */
    public static Method getMethod(String dsType) {
        return ALL_DATA_METHOD.get(dsType);
    }
    /**
     * 策略名为 key,Class 为 value
     */
    public static void putMethod(String dsType,Method method){
        ALL_DATA_METHOD.put(dsType,method);
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
