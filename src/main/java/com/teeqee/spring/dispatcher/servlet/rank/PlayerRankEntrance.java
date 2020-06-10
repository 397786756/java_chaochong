package com.teeqee.spring.dispatcher.servlet.rank;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.teeqee.mybatis.dao.PlayerRankLogMapper;
import com.teeqee.mybatis.dao.PlayerRankMapper;
import com.teeqee.mybatis.pojo.PlayerInfo;
import com.teeqee.mybatis.pojo.PlayerRank;
import com.teeqee.mybatis.pojo.PlayerRankLog;
import com.teeqee.net.handler.Session;
import com.teeqee.spring.dispatcher.model.MethodModel;
import com.teeqee.spring.dispatcher.servlet.entity.Animal;
import com.teeqee.spring.dispatcher.servlet.entity.Opponent;
import com.teeqee.spring.dispatcher.servlet.entity.TopRankInfo;
import com.teeqee.spring.dispatcher.servlet.entity.WorldRankEnd;
import com.teeqee.spring.dispatcher.servlet.rank.entity.TopListRankResult;
import com.teeqee.spring.dispatcher.servlet.rank.service.RedisService;
import com.teeqee.spring.dispatcher.servlet.rank.service.RedisServiceImpl;
import com.teeqee.spring.mode.annotation.Dispather;
import com.teeqee.spring.mode.annotation.DataSourceType;
import com.teeqee.utils.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@DataSourceType("redisRank")
public class PlayerRankEntrance  {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

     @Resource
     private RedisService redisService;
     @Resource
     private PlayerRankMapper playerRankMapper;
     @Resource
     private PlayerRankLogMapper playerRankLogMapper;

     private ConcurrentHashMap<Long,PlayerRank> playerRankMap=new ConcurrentHashMap<>(1024);

    /**获取打榜战报*/
    @Dispather(value = "getrankreport")
    public JSONObject getrankreport(MethodModel method) {
        List<PlayerRankLog> playerRankLog = playerRankLogMapper.selectByPrimaryKey(method.getSession().getUid());
        if (playerRankLog!=null){
            playerRankLog.forEach(log->{
                String opponentnickname = log.getOpponentnickname();
                String opponentavatar = log.getOpponentavatar();
                if (opponentnickname==null){
                    log.setOpponentnickname("");
                }
                if (opponentavatar==null){
                    log.setOpponentavatar("");
                }
            });
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("reportlist", playerRankLog);
        return jsonObject;
    }

    /**获取打榜对战列表*/
    @Dispather(value = "getworldrank")
    public JSONObject getworldrank(MethodModel method) {
       return getworldrankJson(method.getSession());
    }

    /**获取我的session*/
    private JSONObject getworldrankJson( Session session){
        //因为现在的打榜是存到数据库中的,所以玩家去拉取只能拉取到这个
        return initPlayerWorldRank(session.getPlayerRank(),session.getChannelid());
    }


   private JSONObject initPlayerWorldRank(PlayerRank playerRank, Integer channelid){
       JSONObject jsonObject = new JSONObject(2);
       if (playerRank!=null&&channelid!=null){
           //挑战的类型
           int toplistType = RedisServiceImpl.TOPLIST_TYPE;
        //   Boolean isopponent = playerRank.getIsopponent();
        //   if (isopponent==null){
        //       updatePlayerRankOpponenter(playerRank,channelid,toplistType);
        //       playerRank.setIsopponent(false);
        //   }else if(isopponent){
        //       updatePlayerRankOpponenter(playerRank,channelid,toplistType);
        //   }
           TopListRankResult topListRankResult = updatePlayerRankOpponenter(playerRank, channelid, toplistType);
           //返回的挑战人数
           List<Opponent> opponentList = getSixOpponenter(channelid, playerRank);
           List<Long> xList = topListRankResult.getXList();
           for (int i = 0; i < opponentList.size(); i++) {
               Long aLong = xList.get(i);
               Opponent opponent = opponentList.get(i);
               opponent.setRank(aLong);
           }
           jsonObject.put("yourrank", playerRank.getRank());
           jsonObject.put("opponentlist",opponentList);
       }
       return jsonObject;
   }

    /**
     * @param playerRank 玩家的信息
     * @param channelid 渠道的id
     * @param toplistType 排行榜的类型
     */
   private TopListRankResult updatePlayerRankOpponenter(PlayerRank playerRank,Integer channelid,Integer toplistType){
       Long uid = playerRank.getUid();
       TopListRankResult topListRankResult = redisService.getTopRankList(channelid, toplistType, uid);
       logger.info("topListRankResult:{}",JSONObject.parseObject(JSON.toJSONString(topListRankResult)));
       if (topListRankResult!=null&&topListRankResult.getMyRank()!=null&&topListRankResult.getXList()!=null){
           Long myRank = topListRankResult.getMyRank();
           List<Long> xList = topListRankResult.getXList();
           List<Long> playerList = topListRankResult.getPlayerList();
           playerRank.setRank(myRank);
           //设置玩家的集合
           for (int i = 0; i < playerList.size(); i++) {
               updatePlayerRankOppoent(i,playerList.get(i), playerRank);
           }
       }
       return topListRankResult;
   }

    /**获取6个挑战者*/
    private   List<Opponent>  getSixOpponenter(Integer channelid, PlayerRank playerRank){
       List<Opponent> list = new ArrayList<>(6);
        if (channelid!=null&&playerRank!=null){
            Long opponent1id = playerRank.getOpponent1();
            Long opponent2id = playerRank.getOpponent2();
            Long opponent3id = playerRank.getOpponent3();
            Long opponent4id = playerRank.getOpponent4();
            Long opponent5id = playerRank.getOpponent5();
            Long opponent6id = playerRank.getOpponent6();
            Opponent opponent1 = selectChannelidPlayerRank(channelid, opponent1id);
            Opponent opponent2 = selectChannelidPlayerRank(channelid, opponent2id);
            Opponent opponent3 = selectChannelidPlayerRank(channelid, opponent3id);
            Opponent opponent4 = selectChannelidPlayerRank(channelid, opponent4id);
            Opponent opponent5 = selectChannelidPlayerRank(channelid, opponent5id);
            Opponent opponent6 = selectChannelidPlayerRank(channelid, opponent6id);
            list.add(opponent1);
            list.add(opponent2);
            list.add(opponent3);
            list.add(opponent4);
            list.add(opponent5);
            list.add(opponent6);
        }
        return list;
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

    /**将动物转成客户端是json串*/
    private Opponent selectChannelidPlayerRank(Integer channelid, long uid){
        Opponent opponent = playerRankMapper.selectChannelidPlayerRank(channelid,uid);
        if (opponent!=null){
            String animalString = opponent.getAnimal();
            if (animalString!=null){
                JSONArray list = JSONArray.parseArray(animalString);
                JSONArray jsonArray = new JSONArray(6);
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    JSONArray animalJsonArrayBean = list.getJSONArray(i);
                    Integer id = animalJsonArrayBean.getInteger(0);
                    Integer lv = animalJsonArrayBean.getInteger(1);
                    Integer blood = animalJsonArrayBean.getInteger(2);
                    Integer attack = animalJsonArrayBean.getInteger(3);
                    Integer defense = animalJsonArrayBean.getInteger(4);
                    Animal animal = new Animal(id, lv, blood, attack, defense);
                    jsonArray.add(animal);
                }
                opponent.setAnimal(jsonArray.toJSONString());
            }
        }
        return opponent;
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
        JSONArray rankList;
        TopRankInfo topRankInfo;
        if (channelid!=null){
            //世界排名为1
            int topListType=1;
             List<TopRankInfo> topList = redisService.getRankList(channelid, topListType);
             rankList= JSONArray.parseArray(JSON.toJSONString(topList));
             topRankInfo = redisService.getMyTopRankInfo(channelid, topListType, uid);
             topRankInfo.setNickname(playerInfo.getMyNickName());
             topRankInfo.setAvatar(playerInfo.getMyAvatar());
        }else {
            topRankInfo = new TopRankInfo();
            topRankInfo.init();
            rankList=new JSONArray();
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
                JSONObject jsonObject = new JSONObject();
                String nickname = topRankInfo.getNickname();
                jsonObject.put("nickname", nickname==null?"未授权的玩家":nickname);
                String avatar = topRankInfo.getAvatar();
                jsonObject.put("avatar", avatar==null?"":avatar);
                jsonObject.put("missnum", topRankInfo.getRounds());
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
            //我的排名
            Long  rank1= worldRankEnd.getRank();
            //玩家的排名
            Long rank2 = worldRankEnd.getOpponentrank();
            if (iswin!=null&&uid2!=null&&uid1!=null){
                //渠道的id
                Integer channelid = methodModel.getSession().getChannelid();
                int toplistType = RedisServiceImpl.TOPLIST_TYPE;
                logger.info(iswin==WIN?"victory":"defeated");
                if (iswin==WIN&&rank1>rank2){
                    //排名越小越大
                    methodModel.getSession().getPlayerRank().setRank(rank2);
                    updateWorldRank(iswin,uid1,rank2, uid2, rank1, animal, animal2);
                    redisService.addRank(channelid, toplistType, uid1, rank2.doubleValue(), true);
                    redisService.addRank(channelid, toplistType, uid2, rank1.doubleValue(), true);
                }else {
                    updateWorldRank(iswin,uid1,rank1, uid2, rank2, animal, animal2);
                    logger.info("change error redis do not touch");
                }
                  //打赢了
                return getworldrank(methodModel);
            }
        }
        return null;
    }

    /**
     * @param animalStringJson 动物的json
     * @return 返回成优化好的json 字符串
     */
    private String  checkAnimalJosn(String animalStringJson){
        String animalJson=null;
        if (animalStringJson!=null&&!"".equals(animalStringJson)){
            List<Animal> list = JSONArray.parseArray(animalStringJson, Animal.class);
            if (list!=null&&list.size()>0){
                JSONArray jsonArray = new JSONArray();
                for (Animal animal : list) {
                    //将json对象转成
                    String json = JSON.toJSONString(animal, SerializerFeature.BeanToArray);
                    JSONArray parseArray = JSONArray.parseArray(json);
                    jsonArray.add(parseArray);
                }
                animalJson=jsonArray.toJSONString();
            }
        }
        return animalJson;
    }

    /**
     * @param iswin 是否胜利
     * @param uid1 玩家1
     * @param rank1 需要替换玩家1的排名
     * @param uid2 玩家2
     * @param rank2 需要替换玩家2的排名
     * @param animal1 玩家1需要修改的数据
     * @param animal2 玩家2需要修改的数据
     */
    private synchronized void updateWorldRank(Integer iswin,Long uid1,Long rank1,Long uid2,Long rank2,String animal1,String animal2 ){
        animal1=checkAnimalJosn(animal1);
        animal2=checkAnimalJosn(animal2);
        PlayerRank playerRank1 = new PlayerRank();
        playerRank1.setUid(uid1);
        playerRank1.setRank(rank1);
        playerRank1.setAnimal(animal1);
        PlayerRank playerRank2 = new PlayerRank();
        playerRank2.setUid(uid2);
        playerRank2.setRank(rank2);
        playerRank2.setAnimal(animal2);
        logger.info("update playerRank1:{},rank:{},update playerRank2:{},,rank:{}",uid1,rank1,uid2,rank2);
        playerRankMapper.updateByPrimaryKeySelective(playerRank1);
        playerRankMapper.updateByPrimaryKeySelective(playerRank2);
        PlayerRankLog playerRankLog = new PlayerRankLog();
        playerRankLog.setIswin(iswin);
        playerRankLog.setBeforerank(rank1);
        playerRankLog.setAfterrank(rank2);
        playerRankLog.setDatatime(new Date());
        playerRankLog.setUid(uid1);
        playerRankLog.setUid2(uid2);
        playerRankLogMapper.insertSelective(playerRankLog);
    }
}
