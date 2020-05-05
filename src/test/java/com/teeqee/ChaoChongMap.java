package com.teeqee;

import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @Description:
 * @Author: zhengsongjie
 * @File: ChaoChongMap
 * @Version: 1.0.0
 * @Time: 2020-04-26 下午 02:36
 * @Project: java_chaochong
 * @Package: com.teeqee
 * @Software: IntelliJ IDEA
 */
public class ChaoChongMap {
    public HashMap<String, Function> functionHashMap=new HashMap<>();
    public static void main(String[] args) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("double", 2.555D);
        System.out.println(jsonObject.getInteger("double"));
    }
}

