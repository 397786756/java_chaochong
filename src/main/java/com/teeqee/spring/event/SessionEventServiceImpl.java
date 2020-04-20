package com.teeqee.spring.event;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.teeqee.net.gm.ChannelSupervise;
import com.teeqee.spring.mode.service.DataSourceService;
import com.teeqee.spring.result.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import io.netty.channel.Channel;

import java.util.Map;

/**
 * @Description: 会话处理器
 * @Author: zhengsongjie
 * @Software: IntelliJ IDEA
 */
@Service("sessionEventService")
public class SessionEventServiceImpl implements SessionEventService {

    @Resource
    private DataSourceService dataSourceService;

    @Override
    public void getId(Channel channel) {

    }

    @Override
    public void open(Channel channel) {

    }

    @Override
    public void close(Channel channel) {

    }

    @Override
    public void send(Channel channel, String msg) {
        try {
            //连续发送错误10次则关闭
            JSONObject jsonObject = JSONObject.parseObject(msg);
            String cmd = jsonObject.getString("cmd");
            JSONObject data = jsonObject.getJSONObject("data");
            Result result = dataSourceService.connect(cmd, data, channel);
            if (result != null) {
                ChannelSupervise.sendToUser(channel.id(), result);
            }
        } catch (Exception e) {
            channel.close();
            e.printStackTrace();
        }
    }

    @Override
    public void exceptionCaught(Channel channel) {

    }
}
