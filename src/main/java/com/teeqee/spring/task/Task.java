package com.teeqee.spring.task;

import com.alibaba.fastjson.JSONObject;
import com.teeqee.net.gm.ChannelSupervise;
import com.teeqee.net.gm.NettyPlayerInfoAttributeKey;
import com.teeqee.net.handler.Session;
import com.teeqee.spring.result.Result;
import io.netty.channel.Channel;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Description: 定时器
 * @Author: zhengsongjie
 * @Time: 2020-06-05 上午 11:37
 * @Software: IntelliJ IDEA
 */
@Component
public class Task {
    private static final String INIT_CMD="init";
    //@Scheduled(cron = "0 0 12 * * ?")
    @Scheduled(cron = "0/5 * * * * ?")
    private void init() {
        for (Channel channel : ChannelSupervise.getGlobalGroup()) {
            if (channel!=null&&channel.isActive()){
                Session session = NettyPlayerInfoAttributeKey.getSession(channel);
                 if (session!=null){
                    JSONObject init = session.getPlayerData().init();
                    if (init!=null){
                        Result result = new Result();
                        result.setCmd(INIT_CMD);
                        result.setData(init);
                        session.send(result);
                    }
                }
            }
        }
    }
}
