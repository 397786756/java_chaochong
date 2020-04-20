package com.teeqee.spring.mode.strategy;

import com.alibaba.fastjson.JSONObject;
import com.teeqee.spring.result.Result;
import io.netty.channel.Channel;


import java.util.Map;

public interface DataSourceStrategy {

   Result connect(String cmd, JSONObject data, Channel channel);
}
