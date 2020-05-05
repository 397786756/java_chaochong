package com.teeqee.spring.dispatcher.servlet.rank;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.teeqee.mybatis.pojo.PlayerInfo;
import com.teeqee.spring.dispatcher.model.MethodModel;
import com.teeqee.spring.dispatcher.servlet.entity.RankInfo;
import com.teeqee.spring.dispatcher.servlet.rank.service.RedisService;
import com.teeqee.spring.mode.annotation.Dispather;
import com.teeqee.spring.mode.annotation.DataSourceType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;


@Service
@DataSourceType("redisRank")
public class PlayerRank  {

     @Resource
     private RedisService redisService;

    /**飞镖手残排行榜*/
    @Dispather(value = "toplistmissnum")
    public JSONObject toplistmissnum(MethodModel model) {
        PlayerInfo playerInfo = model.getSession().getPlayerInfo();
        //获取渠道id
        Integer channelid = playerInfo.getChannelid();
        JSONObject jsonObject = new JSONObject();
        if (channelid!=null&&channelid>0){
            String toplistmissnum1 = "toplistmissnum";
            List<RankInfo> toplistmissnum = redisService.getRankList(channelid, toplistmissnum1);
            JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(toplistmissnum));
            jsonObject.put(toplistmissnum1,jsonArray);
        }
        return jsonObject;
    }
}
