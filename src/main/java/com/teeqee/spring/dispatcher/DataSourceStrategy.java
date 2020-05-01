package com.teeqee.spring.dispatcher;

import com.alibaba.fastjson.JSONObject;
import com.teeqee.net.handler.Session;
import com.teeqee.spring.result.Result;


public interface DataSourceStrategy {
   Result connect(String cmd, JSONObject data, Session session);
}
