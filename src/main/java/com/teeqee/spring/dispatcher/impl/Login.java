package com.teeqee.spring.dispatcher.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.teeqee.mybatis.dao.PlayerDataMapper;
import com.teeqee.mybatis.pojo.PlayerData;
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
public class Login {


    @Dispather(value = "login")
    public Result login(JSONObject jsonObject) {
        JSONObject data = jsonObject.getJSONObject("data");
        if (data != null && data.size() > 0) {
            AbstractSession abstractSession = (AbstractSession) data.get("session");
            String openid = data.getString("openid");
            String cmd = jsonObject.getString("cmd");
            if (openid != null) {
                JSONObject loginJson = localLogin(openid);
                return new Result(cmd, loginJson);
            }
        }
        return new Result("login", "openId");
    }


    @Resource
    private PlayerDataMapper playerDataMapper;

    /**
     * @param openId 用户的openId
     * @return 本地登录
     */
    private JSONObject localLogin(String openId) {
        if (openId != null && openId.length() > 0) {
            PlayerData playerData = playerDataMapper.selectByPrimaryKey(openId);
            if (playerData == null) {
                playerData = createPlayData(openId);
            }
            return JSONObject.parseObject(JSON.toJSONString(playerData));
        }
        return null;
    }

    private PlayerData createPlayData(String openId) {
        PlayerData playerData = new PlayerData();
        playerData.setOpenid(openId);
        playerDataMapper.insertSelective(playerData);
        playerData=playerDataMapper.selectByPrimaryKey(openId);
        return playerData;
    }
}
