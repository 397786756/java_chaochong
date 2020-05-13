package com.teeqee.spring.dispatcher.servlet.rank;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.teeqee.mybatis.dao.PlayerRankMapper;
import com.teeqee.mybatis.pojo.PlayerInfo;
import com.teeqee.mybatis.pojo.PlayerRank;
import com.teeqee.net.handler.Session;
import com.teeqee.spring.dispatcher.model.MethodModel;
import com.teeqee.spring.dispatcher.servlet.entity.Opponent;
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
    public JSONObject initPlayerWorldrank(PlayerRank playerRank,Integer channelid){
        JSONObject jsonObject = new JSONObject(2);
        Boolean isopponent = playerRank.getIsopponent();
        JSONArray jsonArray = null;
       //如果没有挑战过
       if (isopponent==null||!isopponent){
           Long opponent1 = playerRank.getOpponent1();
           Long opponent6 = playerRank.getOpponent6();
           if (opponent1==null||opponent6==null){
               Long rank = playerRank.getRank();
               if (rank==null){
                   rank=getMyRank( playerRank,channelid);
               }
               int rankPlayerSize=6;
              if (rank<=rankPlayerSize){
                  //拉取前五个
                  jsonArray= getMaxSixOpponenter(channelid,playerRank);
              }else {
                  jsonArray=getSixOpponenter(channelid,playerRank);
              }
           }
       }else {
           jsonArray= getSixOpponenter(channelid,playerRank);
       }
       jsonObject.put("yourrank", playerRank.getRank());
       jsonObject.put("opponentlist",jsonArray);
       return jsonObject;
   }

    /**获取6个挑战者*/
    private JSONArray  getMaxSixOpponenter(Integer channelid,PlayerRank playerRank){
        JSONArray jsonArray = new JSONArray(6);
        int rankPlayerSize=6;
        //获取我的排名
        Long rank = playerRank.getRank();
        for (int i = 0; i < rankPlayerSize; i++) {
            long selectRank=(long)(i+1);
            //防止拉取到自己
            if (rank==selectRank){
                selectRank+=1;
            }
            Opponent opponent = playerRankMapper.selectChannelidPlayerRank(channelid,selectRank );
            JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(opponent, SerializerFeature.WriteNullListAsEmpty,
                    SerializerFeature.WriteNullStringAsEmpty,
                    SerializerFeature.WriteNullNumberAsZero,
                    SerializerFeature.WriteNullBooleanAsFalse,
                    SerializerFeature.WriteMapNullValue,
                    SerializerFeature.PrettyFormat));
            jsonArray.add(jsonObject);
            if (opponent!=null){
                Long uid = opponent.getUid();
                if (i==0){
                    playerRank.setOpponent1(uid);
                }else if (i==1){
                    playerRank.setOpponent2(uid);
                }else if (i==2){
                    playerRank.setOpponent3(uid);
                }else if (i==3){
                    playerRank.setOpponent4(uid);
                }else if (i==4){
                    playerRank.setOpponent5(uid);
                }else {
                    playerRank.setOpponent6(uid);
                }
            }
        }
        return jsonArray;
    }





   /**获取6个挑战者*/
   private JSONArray  getSixOpponenter(Integer channelid,PlayerRank playerRank){
       JSONArray jsonArray = new JSONArray(6);
       Long rank = playerRank.getRank();
       long B = getRankRandom(rank);
       int rankPlayerSize=6;
       long index=0;
       for (int i = 0; i < rankPlayerSize; i++) {
           if (i==0){
               //挑战1的排名 Y1 = X-1
               index=rank-1;
           }else {
               int randomInt = RandomUtils.getRandomInt(1, (int) B);
               index-=randomInt;
           }
           Opponent opponent = playerRankMapper.selectChannelidPlayerRank(channelid,index);
           JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(opponent, SerializerFeature.WriteNullListAsEmpty,
                   SerializerFeature.WriteNullStringAsEmpty,
                   SerializerFeature.WriteNullNumberAsZero,
                   SerializerFeature.WriteNullBooleanAsFalse,
                   SerializerFeature.WriteMapNullValue,
                   SerializerFeature.PrettyFormat));
           jsonArray.add(jsonObject);
           if (opponent!=null){
               Long uid = opponent.getUid();
               if (i==0){
                   playerRank.setOpponent1(uid);
               }else if (i==1){
                   playerRank.setOpponent2(uid);
               }else if (i==2){
                   playerRank.setOpponent3(uid);
               }else if (i==3){
                   playerRank.setOpponent4(uid);
               }else if (i==4){
                   playerRank.setOpponent5(uid);
               }else {
                   playerRank.setOpponent6(uid);
               }
           }
       }
       return jsonArray;
   }



    private static final int RANK250=250;
    private static final int RANK50=50;
    private static final int RANK12=12;
    private static final int RANK6=6;
   /**获取随机的排名*/
   private long getRankRandom(Long rank){
       long randomRank;
       if (rank>RANK250 ){
           randomRank=50;
       }else if (RANK50<rank){
           randomRank=10;
       }else if (RANK12<rank&&rank<RANK50){
           randomRank=2;
       }else if (RANK6<rank&&rank<=RANK12){
           randomRank=1;
       }else {
           randomRank=6;
       }
       return randomRank;
   }

    /**
     * @param playerRank 玩家挑战
     * @param channelid 渠道id
     * @return 返回一个排名
     */
    private synchronized Long getMyRank(PlayerRank playerRank,Integer channelid){
       Integer rankNum = playerRankMapper.findChannelIdRobotRankNum(channelid);
       //服务器需要维持一个人数id
       logger.info("玩家获取排名,同步到数据库中");
       playerRank.setRank(rankNum.longValue()+1);
       playerRankMapper.updateByPrimaryKeySelective(playerRank);
       return (rankNum.longValue()+1);
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
