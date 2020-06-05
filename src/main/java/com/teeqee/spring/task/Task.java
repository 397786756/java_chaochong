package com.teeqee.spring.task;

import com.alibaba.fastjson.JSONObject;
import com.teeqee.mybatis.pojo.PlayerData;
import com.teeqee.net.gm.ChannelSupervise;
import com.teeqee.net.gm.NettyPlayerInfoAttributeKey;
import com.teeqee.net.handler.Session;
import com.teeqee.spring.result.Result;
import io.netty.channel.Channel;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Description: 定时器(每日初始化)
 * @Author: zhengsongjie
 * @Time: 2020-06-05 上午 11:37
 * @Software: IntelliJ IDEA
 */
@Component
@Order(2)
public class Task {
    private static final String INIT_CMD="init";
    //@Scheduled(cron = "0 0 0 * * ?")
    @Scheduled(cron = "0/55 * * * * ?")
    private void init() {
        for (Channel channel : ChannelSupervise.getGlobalGroup()) {
            if (channel!=null&&channel.isActive()){
                Session session = NettyPlayerInfoAttributeKey.getSession(channel);
                 if (session!=null){
                     PlayerData playerData = session.getPlayerData();
                     if (playerData!=null){
                         JSONObject init = playerData.init();
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
}
