package com.teeqee.spring.dispatcher.servlet.rank;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.teeqee.mybatis.pojo.PlayerInfo;
import com.teeqee.spring.dispatcher.model.MethodModel;
import com.teeqee.spring.dispatcher.servlet.rank.entity.TopRankInfo;
import com.teeqee.spring.dispatcher.servlet.rank.service.RedisService;
import com.teeqee.spring.mode.annotation.Dispather;
import com.teeqee.spring.mode.annotation.DataSourceType;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;


@Service
@DataSourceType("redisRank")
public class PlayerRank  {

     @Resource
     private RedisService redisService;

    /**飞镖手残排行榜 */
    @Dispather(value = "toplistmissnum")
    public JSONObject toplistmissnum(MethodModel model) {
        PlayerInfo playerInfo = model.getSession().getPlayerInfo();
        //获取渠道id
        Integer channelid = playerInfo.getChannelid();
        JSONObject jsonObject = new JSONObject();
        if (channelid!=null&&channelid>0){
            int missTopType=2;
            String toplistmissnum="toplistmissnum";
            List<TopRankInfo> missList = redisService.getRankList(channelid, missTopType);
            if (missList.size()>0){
                JSONArray jsonArray = updateTopMissToJsonObject(missList);
                jsonObject.put(toplistmissnum,jsonArray);
            }else {
                jsonObject.put(toplistmissnum,missList);
            }
        }
        return jsonObject;
    }


    /**修改下玩家的默认排行榜变为miss排行榜*/
    private JSONArray updateTopMissToJsonObject( List<TopRankInfo> list){
        JSONArray jsonArray = new JSONArray(list.size());
        for (TopRankInfo topRankInfo : list) {
            Integer rounds = topRankInfo.getRounds();
            JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(topRankInfo));
            jsonObject.remove("rounds");
            jsonObject.remove("openid");
            jsonObject.put("missnum",rounds);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }
}
