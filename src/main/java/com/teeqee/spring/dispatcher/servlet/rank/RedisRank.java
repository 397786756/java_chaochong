package com.teeqee.spring.dispatcher.servlet.rank;

import com.alibaba.fastjson.JSONObject;

import com.teeqee.spring.mode.annotation.Dispather;
import com.teeqee.spring.mode.annotation.DataSourceType;
import com.teeqee.spring.result.Result;
import com.teeqee.utils.SpringUtil2;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;


@Service
@DataSourceType("rank")
public class RedisRank  {


    @Dispather(value = "rank")
    public Result rank(JSONObject data) {
        HashMap<String, String> map = new HashMap<>();
        //获取到redis的Key
        RedisTemplate redisTemplate = (RedisTemplate)SpringUtil2.getBean("redisTemplate");
        System.out.println(redisTemplate);
        return null;
    }
}
