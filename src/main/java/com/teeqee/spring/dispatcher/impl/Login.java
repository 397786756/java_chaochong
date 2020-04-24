package com.teeqee.spring.dispatcher.impl;

import com.alibaba.fastjson.JSONObject;
import com.teeqee.mybatis.dao.PlayerMapper;
import com.teeqee.mybatis.pojo.Player;
import com.teeqee.net.handler.AbstractSession;
import com.teeqee.spring.dispatcher.DataSourceStrategy;
import com.teeqee.spring.mode.annotation.Dispather;
import com.teeqee.spring.mode.annotation.DataSourceType;
import com.teeqee.spring.result.Result;
import io.netty.channel.Channel;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@DataSourceType("login")
@Service
public class Login implements DataSourceStrategy {

    @Resource
    private PlayerMapper playerMapper;

    @Dispather(value = "login")
    @Override
    public Result connect(String cmd, JSONObject data,AbstractSession session) {
        return new Result("login",playerLogin(null,null));
    }


    /**
     * @param pingTai 渠道
     * @param code 用户code
     * @return
     */
    private Player playerLogin(Integer pingTai,String code){
        if (pingTai!=null&&code!=null){
            //TODO
        }
        return null;
    }
}
