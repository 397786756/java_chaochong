package com.teeqee.spring.dispatcher.servlet.rank;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.teeqee.mybatis.dao.PlayerRankMapper;
import com.teeqee.mybatis.pojo.PlayerInfo;
import com.teeqee.mybatis.pojo.PlayerRank;
import com.teeqee.net.handler.Session;
import com.teeqee.spring.dispatcher.cmd.PlayerCmd;
import com.teeqee.spring.dispatcher.model.MethodModel;
import com.teeqee.spring.dispatcher.servlet.entity.Opponent;
import com.teeqee.spring.dispatcher.servlet.entity.TopRankInfo;
import com.teeqee.spring.dispatcher.servlet.entity.WorldRankEnd;
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
        //因为现在的打榜是存到数据库中的,所以玩家去拉取只能拉取到这个
        PlayerRank playerRank = playerRankMapper.selectByPrimaryKey(session.getUid());
        if (playerRank==null){
            playerRank=new PlayerRank();
            playerRank.setUid(session.getUid());
            playerRankMapper.insertSelective(playerRank);
        }
        return initPlayerWorldrank(playerRank,session.getChannelid());
    }


    /**
     * @param playerRank 玩家的排行榜 初始化6个敌人
     */
    private JSONObject initPlayerWorldrank(PlayerRank playerRank,Integer channelid){
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
        playerRank.setIsopponent(true);
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
                    SerializerFeature.WriteMapNullValue));
            jsonArray.add(jsonObject);
            if (opponent!=null){
                Long uid = opponent.getUid();
                updatePlayerRankOppoent(i,uid,playerRank);
            }
        }
        return jsonArray;
    }


    /**
     * @param i 位置
     * @param uid 玩家的uid
     * @param playerRank 修改的数据
     */
    private void updatePlayerRankOppoent(int i,Long uid,PlayerRank playerRank){
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



   /**获取6个挑战者*/
   private JSONArray  getSixOpponenter(Integer channelid,PlayerRank playerRank){
       playerRank.setIsopponent(true);
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
           if (rank==index){
               index+=1;
           }
           Opponent opponent = playerRankMapper.selectChannelidPlayerRank(channelid,index);
           JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(opponent, SerializerFeature.WriteNullListAsEmpty,
                   SerializerFeature.WriteNullStringAsEmpty,
                   SerializerFeature.WriteNullNumberAsZero,
                   SerializerFeature.WriteNullBooleanAsFalse,
                   SerializerFeature.WriteMapNullValue));
           jsonArray.add(jsonObject);
           if (opponent!=null){
               Long uid = opponent.getUid();
               updatePlayerRankOppoent(i,uid,playerRank);
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
       logger.info("get playerRank rank:{}",rankNum+1);
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

    /**
     * 赢了为1
     */
    private static final int WIN=1;

    /**打榜结算gameover*/
    @Dispather(value = "worldrankend")
    public JSONObject worldrankend(MethodModel methodModel) {
        JSONObject data = methodModel.getData();
        WorldRankEnd worldRankEnd = data.toJavaObject(WorldRankEnd.class);
        if (worldRankEnd!=null){
            Integer iswin = worldRankEnd.getIswin();
            Long uid2 = worldRankEnd.getOpponentuid();
            Long uid1 = methodModel.getSession().getUid();
            String animal = worldRankEnd.getAnimal();
            String animal2 = worldRankEnd.getOpponentanimal();
            Long rank1 = worldRankEnd.getRank();
            Long rank2 = worldRankEnd.getOpponentrank();
            if (iswin!=null&&uid2!=null&&uid1!=null&&animal!=null){
                logger.info(iswin==WIN?"victory":"defeated");
                if (iswin==WIN){
                  //打赢了
                    updateWorldRank(uid1,rank1, uid2, rank2, animal, animal2);
                }else {
                    updateWorldRank(uid1,null, uid2, null, animal, animal2);
                }
                return getworldrank(methodModel);
            }
        }
        return null;
    }

    /**
     * @param uid1 玩家1
     * @param rank1 需要替换玩家1的排名
     * @param uid2 玩家2
     * @param rank2 需要替换玩家2的排名
     * @param animal1 玩家1需要修改的数据
     * @param animal2 玩家2需要修改的数据
     */
    private synchronized void updateWorldRank(Long uid1,Long rank1,Long uid2,Long rank2,String animal1,String animal2 ){
        PlayerRank playerRank1 = new PlayerRank();
        playerRank1.setUid(uid1);
        playerRank1.setRank(rank1);
        playerRank1.setAnimal(animal1);
        PlayerRank playerRank2 = new PlayerRank();
        playerRank2.setUid(uid2);
        playerRank2.setRank(rank2);
        playerRank2.setAnimal(animal2);
        logger.info("update playerRank1:{},animal:{},rank:{},update playerRank2:{},animal:{},rank:{}",uid1,animal1,rank1,uid2,animal2,rank2);
        playerRankMapper.updateByPrimaryKeySelective(playerRank1);
        playerRankMapper.updateByPrimaryKeySelective(playerRank2);
    }
}
