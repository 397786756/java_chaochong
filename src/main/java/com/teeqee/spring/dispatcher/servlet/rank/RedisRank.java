package com.teeqee.spring.dispatcher.servlet.rank;

import com.alibaba.fastjson.JSONObject;

import com.teeqee.mybatis.pojo.PlayerInfo;
import com.teeqee.spring.dispatcher.model.MethodModel;
import com.teeqee.spring.mode.annotation.Dispather;
import com.teeqee.spring.mode.annotation.DataSourceType;
import com.teeqee.spring.result.Result;
import com.teeqee.utils.SpringUtil2;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;


@Service
@DataSourceType("redisRank")
public class RedisRank  {
     @Resource
     private RedisTemplate redisTemplate;

    /**手残排行榜*/
    @Dispather(value = "toplistmissnum")
    public JSONObject toplistmissnum(MethodModel model) {
        PlayerInfo playerInfo = model.getSession().getPlayerInfo();
        //获取渠道id
        Integer channelid = playerInfo.getChannelid();
        System.out.println(redisTemplate.toString());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("redis", redisTemplate.toString());
        return jsonObject;
    }
}
