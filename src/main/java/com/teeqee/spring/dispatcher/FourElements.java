package com.teeqee.spring.dispatcher;

import com.alibaba.fastjson.JSONObject;
import com.teeqee.spring.mode.strategy.DataSourceStrategy;
import com.teeqee.spring.mode.annotation.DataSourceType;
import com.teeqee.spring.result.Result;
import io.netty.channel.Channel;
import org.springframework.stereotype.Service;


@DataSourceType("login")
@Service
public class FourElements implements DataSourceStrategy {


    @Override
    public Result connect(String cmd, JSONObject data, Channel channel) {
        return new Result("login","22");
    }
}
