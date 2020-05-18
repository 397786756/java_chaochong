package com.teeqee.spring.dispatcher.servlet.rank;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import com.teeqee.spring.dispatcher.servlet.rank.service.RedisService;
import com.teeqee.spring.dispatcher.servlet.rank.service.RedisServiceImpl;
import com.teeqee.spring.mode.annotation.Dispather;
import com.teeqee.spring.mode.annotation.DataSourceType;
import com.teeqee.utils.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
        PlayerRankLog playerRankLog = playerRankLogMapper.selectByPrimaryKey(method.getSession().getUid());
        if (playerRankLog!=null){
            String opponentnickname = playerRankLog.getOpponentnickname();
            String opponentavatar = playerRankLog.getOpponentavatar();
            if (opponentnickname==null){
                playerRankLog.setOpponentnickname("");
            }
            if (opponentavatar==null){
                playerRankLog.setOpponentavatar("");
            }
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
        //挑战的类型
       int toplistType = RedisServiceImpl.TOPLIST_TYPE;
       Boolean isopponent = playerRank.getIsopponent();
       //我的排名
       Long yourrank=playerRank.getRank();
       //返回的数据源
       JSONObject jsonObject = new JSONObject(2);
       Long aLong = redisService.rankPlayerSize(channelid, toplistType);
       if (yourrank!=null&&yourrank>aLong){
           logger.info("topList rank num :{},thisPlayer rank:{}",aLong,yourrank);
           playerRank.setRank(aLong+1);
       }
       if (yourrank==null){
           logger.info("this playerRank is null");
           redisService.addRank(channelid, toplistType, playerRank.getUid(), aLong.doubleValue()+1, true);
           playerRank.setRank(aLong+1);
       }
       if (isopponent==null||!isopponent){
           logger.info("isopponent is  null");
           updatePlayerRankOpponenter(playerRank,channelid,toplistType);
           playerRank.setIsopponent(false);
       }else {
           logger.info("isopponent is not null and true");
           updatePlayerRankOpponenter(playerRank,channelid,toplistType);
       }
       //返回的挑战人数
       JSONArray jsonArray=getSixOpponenter(channelid,playerRank);
       jsonObject.put("yourrank", playerRank.getRank());
       jsonObject.put("opponentlist",jsonArray);
       return jsonObject;
   }

   private void updatePlayerRankOpponenter(PlayerRank playerRank,Integer channelid,Integer toplistType){
        logger.info("start:{}",JSON.toJSONString(playerRank));
       int playersNum=6;
       Long rank = playerRank.getRank();
       logger.info("player rank:{}",rank);
       if (rank <=playersNum){
           logger.info("get max sixPlayer");
           for (int i = 0; i < playersNum; i++) {
               Long playerUid = redisService.getTopRankUid(channelid, toplistType, (long) i);
               updatePlayerRankOppoent(i,playerUid,playerRank);
           }
       }else {
           logger.info("for ger six");
           List<Long> list = getBandX(rank, playersNum+1);
           for (int i = 0; i < list.size(); i++) {
               Long aLong = list.get(i);
               Long playerUid = redisService.getTopRankUid(channelid, toplistType, aLong);
               updatePlayerRankOppoent(i,playerUid,playerRank);
           }
       }
       logger.info("end:{}",JSON.toJSONString(playerRank));
   }


    /**
     * @param rank 根据下标排名获取区间值
     * @param playerNum 获取的玩家人数
     * @return 返回被拉取到的玩家的集合
     */
   private List<Long> getBandX(Long rank,int playerNum){
       long B = getRankRandom(rank);
       logger.info("rank B:{}",B);
       Long Y = null;
       List<Long> list = new ArrayList<>(playerNum);
       for (int i = 0; i < playerNum; i++) {
           if (i==0){
               //挑战1的排名 Y1 = X-1
               Y=rank-1;
           }else {
               int randomInt = RandomUtils.getRandomInt(1, (int) B);
               Y-=randomInt;
           }
           list.add(Y);
       }
       logger.info("this player rank:{},rankList:{}",rank,list.toArray());
       return list;
   }

    /**获取6个挑战者*/
    private JSONArray  getSixOpponenter(Integer channelid,PlayerRank playerRank){
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
        JSONArray jsonArray = new JSONArray(6);
        jsonArray.add(opponent1);
        jsonArray.add(opponent2);
        jsonArray.add(opponent3);
        jsonArray.add(opponent4);
        jsonArray.add(opponent5);
        jsonArray.add(opponent6);
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

    /**将动物转成客户端是json串*/
    private Opponent selectChannelidPlayerRank(Integer channelid, long uid){
        Opponent opponent = playerRankMapper.selectChannelidPlayerRank(channelid,uid);
        String animalString = opponent.getAnimal();
        if (animalString!=null&&!"".equals(animalString)){
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
        return opponent;
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
            Long rank1 = worldRankEnd.getRank();
            Long rank2 = worldRankEnd.getOpponentrank();
            if (iswin!=null&&uid2!=null&&uid1!=null&&animal!=null){
                logger.info(iswin==WIN?"victory":"defeated");
                if (iswin==WIN){
                    //如果我赢了并且敌人的名次大于我的名次
                     if (rank2<rank1){
                         rank1=rank2;
                         rank2=rank1;
                         logger.info("original rank:{},now rank:{},default rank:{}", rank1,rank2,rank1);
                     }
                }
                  //打赢了
                 updateWorldRank(iswin,uid1,rank1, uid2, rank2, animal, animal2);
                return getworldrank(methodModel);
            }
        }
        return null;
    }

    /**
     * @param animalStringJson 动物的json
     * @return 返回成优化好的json 字符串
     */
    public String  checkAnimalJosn(String animalStringJson){
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
        logger.info("update playerRank1:{},animal:{},rank:{},update playerRank2:{},animal:{},rank:{}",uid1,animal1,rank1,uid2,animal2,rank2);
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
