package com.teeqee.spring.dispatcher.servlet.rank.service;

import com.teeqee.mybatis.dao.PlayerDataMapper;
import com.teeqee.mybatis.dao.PlayerInfoMapper;
import com.teeqee.mybatis.dao.ServerInfoMapper;
import com.teeqee.mybatis.pojo.PlayerInfo;
import com.teeqee.mybatis.pojo.ServerInfo;
import com.teeqee.spring.dispatcher.servlet.rank.entity.TopRankInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description: 排行榜的redisKey
 * @Author: zhengsongjie
 * @File: RedisServiceImpl
 * @Version: 1.0.0
 * @Time: 2020-05-05 下午 04:02
 * @Project: java_chaochong
 * @Package: com.teeqee.spring.dispatcher.servlet.rank.service
 * @Software: IntelliJ IDEA
 */
@Service("redisService")
public class RedisServiceImpl implements RedisService , CommandLineRunner , DisposableBean {
    /**LOGGER*/
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**排行榜定时器*/
    @Value("${rankTask}")
    private Integer rankTask;
    @Resource
    private RedisTemplate redisTemplate;
    /**server dao*/
    @Resource
    private ServerInfoMapper serverInfoMapper;
    @Resource
    private PlayerInfoMapper playerInfoMapper;
    @Resource
    private PlayerDataMapper playerDataMapper;
    /**未名中的排行榜Map*/
    private ConcurrentHashMap<Integer,List<TopRankInfo>> missRankMap=new ConcurrentHashMap<>(16);
    /**打榜的排行榜Map*/
    private ConcurrentHashMap<Integer,List<TopRankInfo>> topRankMap=new ConcurrentHashMap<>(16);
    /**serverMap*/
    private ConcurrentHashMap<Integer, ServerInfo> serverInfoMap=new ConcurrentHashMap<>(16);
    /**player info dao*/

    /**
     * @param channelId  平台id
     * @param redisKey 排行榜的key
     * @return 返回未命中排行榜的List
     */
    @Override
    public List<TopRankInfo> getRankList(Integer channelId, String redisKey) {
        //合并的key
        String addRedisKey=channelId+"_"+redisKey;
        List<TopRankInfo> missRankInfos = missRankMap.get(addRedisKey);
        if (missRankInfos==null){

        }
        return missRankInfos;
    }

    /**
     * @param channelId  平台id
     * @param redisKey 排行榜的key
     * @return 返回排行榜的List
     */
    @Override
    public List<TopRankInfo> getTopRankList(Integer channelId, String redisKey) {
        return null;
    }
    /**
     * @param channelId  平台id
     * @param redisKey 排行榜的key
     * @param openId 玩家的openId
     * @return 返回个人排名
     */
    @Override
    public TopRankInfo getMyTopRankInfo(Integer channelId, String redisKey, String openId) {
        String redisAddKey = getRedisZSetKey(channelId, redisKey);
        Double score = redisTemplate.opsForZSet().score(redisAddKey, openId);
        Long myRank=0L;
        if (score!=null){
            Long aLong = redisTemplate.opsForZSet().reverseRank(redisAddKey, openId);
            if (aLong!=null){
                myRank=aLong;
            }
        }
        return new TopRankInfo(score != null ? score.intValue() : 0, myRank);
    }


    /**
     * @param channelId 平台id
     * @param redisKey  redis的Key
     * @param openId    玩家的openId
     * @param score     分数
     * @return
     */
    @Override
    public Boolean addRank(Integer channelId, String redisKey, String openId,Double score) {
        return false;
    }


    /**玩家排行榜的热更新*/
    @Scheduled(cron = "0/60 * * * * ?")
    public void task(){
        updateRank();
    }


    /**
     * @return 返回一个redisZset的key
     */
    private String getRedisZSetKey(Integer channelId, String redisKey){
        return channelId+"_"+redisKey;
    }

    /**
     * 关闭时关闭排行榜
     */
    @Override
    public void destroy() throws Exception {
        logger.info("delete redis zSet info");
    }

    /**
     * 开启时加载排行榜
     */
    @Override
    public void run(String... args) throws Exception {
        initServerInfo();
        initRankInfo();
    }

    /**初始化服务器*/
    private void initServerInfo(){
        logger.info("init server info");
        List<ServerInfo> serverList = serverInfoMapper.serverInfoList();
        serverList.forEach(server->{
            Integer channelId = server.getChannelId();
            serverInfoMap.put(channelId, server);
        });
    }

    /**初始化排行榜*/
    private void initRankInfo(){
        logger.info("init redis zSet info");
        List<TopRankInfo> list = new ArrayList<>(16);
        TopRankInfo missRankInfo = new TopRankInfo( 20, 1L);
        missRankInfo.setNickname("测试玩家");
        list.add(missRankInfo);
        //排行榜
        missRankMap.put(10001,list);
        topRankMap.put(10001,list);
        //0 rounds 1 missnum
        List<String> rankOrderByNameList = new ArrayList<>(2);
        String rounds="rounds";
        String missnum="missnum";
        rankOrderByNameList.add(rounds);
        rankOrderByNameList.add(missnum);
        serverInfoMap.forEach((k,v)->{
            for (String orderByName : rankOrderByNameList) {
                List<TopRankInfo> topList = playerDataMapper.initTopRank(k, orderByName);
                if (topList!=null&&topList.size()>0){
                    //先排好序
                    int top=1;
                    for (TopRankInfo topRankInfo : topList) {
                        topRankInfo.setRounds(top);
                        top++;
                    }
                    if (orderByName.equals(rounds)){
                        //打榜

                    }else {
                        //miss
                    }
                }
            }
        });
    }

    /**更新排行榜*/
    private void  updateRank(){
        logger.info("update redis zSet info");
        serverInfoMap.forEach((k,v)->{
            Integer channelId = v.getChannelId();
            String missRankKey = v.getMissRankKey();
            //获取排行榜的redisKey
            String redisZSetKey = getRedisZSetKey(channelId, missRankKey);
            Long size = redisTemplate.opsForZSet().size(redisZSetKey);
        });
    }



    /**更新打榜*/
    private List<TopRankInfo> updateTopRankInfo(String redisZSetKey, Long size){
         List<TopRankInfo> list = new ArrayList<>(50);
         if (size==0){
             return list;
         }
         int maxNum=50;
         if (size>=maxNum){
             size=50L;
         }
        Set<ZSetOperations.TypedTuple<Object>> set = redisTemplate.opsForZSet().reverseRangeWithScores(redisZSetKey, 0, size);
        if (set!=null&&set.size()>0){
            int top=1;
            for (ZSetOperations.TypedTuple<Object> tuple : set) {
                Double score = tuple.getScore();
                String openId = (String) tuple.getValue();
                if (score!=null){
                    TopRankInfo topRankInfo = new TopRankInfo(top, score.longValue());
                    PlayerInfo playerInfo = playerInfoMapper.selectByPrimaryKey(openId);
                    if (playerInfo!=null){
                        String avatar = playerInfo.getMyAvatar();
                        String nickName = playerInfo.getMyNickName();
                        topRankInfo.setNickname(nickName);
                        topRankInfo.setAvatar(avatar);
                    }else {
                        topRankInfo.setNickname("未授权玩家");
                        topRankInfo.setAvatar("");
                    }
                    list.add(topRankInfo);
                }
                top++;
            }
        }
        return list;
    }
}
