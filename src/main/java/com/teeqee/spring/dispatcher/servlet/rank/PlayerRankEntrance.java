package com.teeqee.spring.dispatcher.servlet.rank;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.teeqee.mybatis.dao.PlayerRankMapper;
import com.teeqee.mybatis.pojo.PlayerInfo;
import com.teeqee.mybatis.pojo.PlayerRank;
import com.teeqee.net.handler.Session;
import com.teeqee.spring.dispatcher.model.MethodModel;
import com.teeqee.spring.dispatcher.servlet.entity.TopRankInfo;
import com.teeqee.spring.dispatcher.servlet.rank.service.RedisService;
import com.teeqee.spring.mode.annotation.Dispather;
import com.teeqee.spring.mode.annotation.DataSourceType;
import com.teeqee.utils.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Service
@DataSourceType("redisRank")
public class PlayerRankEntrance  {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

     @Resource
     private RedisService redisService;
     @Resource
     private PlayerRankMapper playerRankMapper;

    /**世界排行榜*/
    @Dispather(value = "getworldrank")
    public JSONObject getworldrank(MethodModel method) {
       return getworldrankJson(method.getSession());
    }

    /**获取我的session*/
    private JSONObject getworldrankJson( Session session){
       PlayerRank playerRank = session.getPlayerRank();
       JSONObject jsonObject = new JSONObject();
       if (playerRank==null){
           playerRank=new PlayerRank(session.getUid());
           playerRank.initData(0,false);
       }
       return jsonObject;
    }


    /**
     * @param playerRank 玩家的排行榜 初始化6个敌人
     */
    public void initPlayerWorldrank(PlayerRank playerRank,Integer channelid){
       Boolean isopponent = playerRank.getIsopponent();
       if (isopponent==null||!isopponent){
           Long opponent1 = playerRank.getOpponent1();
           Long opponent6 = playerRank.getOpponent6();
           if (opponent1==null||opponent6==null){
               Long rank = playerRank.getRank();
               if (rank==null){
                   rank=getMyRank(channelid);
               }
               int rankPlayerSize=6;
              if (rank<=rankPlayerSize){
                  //拉取前五个
                  logger.info("玩家的排名为:{},拉取最强的前五个",rank);
                  return;
              }else {
                  //获取X
                  long B = getRankRandom(rank);
                  logger.info("玩家的排名为:{}", rank);
                  logger.info("得到的B:{}",B);
                  long index=0;
                  for (int i = 0; i < rankPlayerSize; i++) {
                     if (i==0){
                       //挑战1的排名 Y1 = X-1
                         index=rank-1;
                     }else {
                         int randomInt = RandomUtils.getRandomInt(1, (int) B);
                         index-=randomInt;
                     }
                     //TODO 排名会不正常 待修复
                     logger.info("挑战者"+(i+1)+"的排名:{}",index);
                  }
              }
           }
       }
   }


   /**获取随机的排名*/
   public long getRankRandom(Long rank){
       long randomRank;
       if (rank>250 ){
           randomRank=50;
       }else if (50<rank){
           randomRank=10;
       }else if (12<rank&&rank<50){
           randomRank=2;
       }else if (6<rank&&rank<=12){
           randomRank=1;
       }else {
           randomRank=6;
       }
       return randomRank;
   }

    /**
     * @param channelid 渠道id
     * @return 返回一个排名
     */
    public  synchronized Long getMyRank(Integer channelid){
       Integer rankNum = playerRankMapper.findChannelIdRobotRankNum(channelid);
       //服务器需要维持一个人数id
       //TODO 已确保rankId的人数
       return rankNum.longValue()+1;
   }

    /**世界排行榜*/
    @Dispather(value = "toplist")
    public JSONObject toplist(MethodModel model) {
        PlayerInfo playerInfo = model.getSession().getPlayerInfo();
        //获取渠道id
        Integer channelid = playerInfo.getChannelid();
        JSONObject jsonObject = new JSONObject(2);
        String  toplistdata ="toplistdata";
        String  yourrank ="yourrank";
        Long uid = model.getSession().getUid();
        JSONArray rankList = new JSONArray(48);
        TopRankInfo topRankInfo;
        if (channelid!=null){
            //世界排名为1
            int topListType=1;
            List<TopRankInfo> topList = redisService.getRankList(channelid, topListType);
            if (topList!=null&&topList.size()>0){
                jsonObject.put(toplistdata,topList) ;
            }
             topRankInfo = redisService.getMyTopRankInfo(channelid, topListType, uid);
             topRankInfo.setNickname(playerInfo.getMyNickName());
             topRankInfo.setAvatar(playerInfo.getMyAvatar());
        }else {
            topRankInfo = new TopRankInfo();
            topRankInfo.init();
        }
        jsonObject.put(yourrank,topRankInfo);
        jsonObject.put(toplistdata,rankList);
        return jsonObject;
    }

    /**飞镖手残排行榜 */
    @Dispather(value = "toplistmissnum")
    public JSONObject toplistmissnum(MethodModel model) {
        PlayerInfo playerInfo = model.getSession().getPlayerInfo();
        //获取渠道id
        Integer channelid = playerInfo.getChannelid();
        JSONObject jsonObject = new JSONObject();
        String toplistmissnum="toplistmissnum";
        if (channelid!=null){
            int missTopType=2;
            List<TopRankInfo> missList = redisService.getRankList(channelid, missTopType);
            JSONArray jsonArray = updateTopMissToJsonObject(missList);
            jsonObject.put(toplistmissnum,jsonArray);
        }else {
            jsonObject.put(toplistmissnum,new ArrayList<>());
        }
        return jsonObject;
    }


    /**修改下玩家的默认排行榜变为miss排行榜*/
    private JSONArray updateTopMissToJsonObject( List<TopRankInfo> list){
        JSONArray jsonArray;
        if (list!=null&&list.size()>0){
            jsonArray= new JSONArray(list.size());
            for (TopRankInfo topRankInfo : list) {
                Integer rounds = topRankInfo.getRounds();
                JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(topRankInfo));
                jsonObject.remove("rounds");
                jsonObject.remove("openid");
                jsonObject.put("missnum",rounds);
                jsonArray.add(jsonObject);
            }
        }else {
           jsonArray=new JSONArray(0);
        }
        return jsonArray;
    }

}
