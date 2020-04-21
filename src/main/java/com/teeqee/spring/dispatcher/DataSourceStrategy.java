package com.teeqee.spring.dispatcher;

import com.alibaba.fastjson.JSONObject;
import com.teeqee.net.handler.AbstractSession;
import com.teeqee.spring.result.Result;
import io.netty.channel.Channel;


public interface DataSourceStrategy {
   Result connect(String cmd, JSONObject data, AbstractSession session);
}
