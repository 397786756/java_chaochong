package com.teeqee.spring.dispatcher.servlet.rank.service;

import com.teeqee.mybatis.dao.PlayerDataMapper;
import com.teeqee.mybatis.dao.PlayerInfoMapper;
import com.teeqee.mybatis.dao.PlayerRankMapper;
import com.teeqee.mybatis.dao.ServerInfoMapper;
import com.teeqee.mybatis.pojo.PlayerInfo;
import com.teeqee.mybatis.pojo.PlayerRank;
import com.teeqee.mybatis.pojo.ServerInfo;
import com.teeqee.spring.dispatcher.servlet.entity.TopRankInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @Description: 超宠排行榜
 * @Author: zhengsongjie
 * @File: RedisServiceImpl
 * @Version: 1.0.0
 * @Time: 2020-05-05 下午 04:02
 * @Project: java_chaochong
 * @Package: com.teeqee.spring.dispatcher.servlet.rank.service
 * @Software: IntelliJ IDEA
 */
@Service("redisService")
public class RedisServiceImpl implements RedisService, CommandLineRunner, DisposableBean {
    /**
     * LOGGER
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 排行榜定时器
     */
    @Value("${rankTask}")
    private Integer rankTask;
    @Resource
    private RedisTemplate redisTemplate;
    /**serverInfo dao*/
    @Resource
    private ServerInfoMapper serverInfoMapper;
    @Resource
    private PlayerInfoMapper playerInfoMapper;
    @Resource
    private PlayerDataMapper playerDataMapper;
    @Resource
    private PlayerRankMapper playerRankMapper;
    /**未名中的排行榜Map*/
    private ConcurrentHashMap<Integer, List<TopRankInfo>> missRankMap = new ConcurrentHashMap<>(16);
    /**打榜的排行榜Map*/
    private ConcurrentHashMap<Integer, List<TopRankInfo>> roundsRankMap = new ConcurrentHashMap<>(16);
    /**serverMap*/
    private ConcurrentHashMap<Integer, ServerInfo> serverInfoMap = new ConcurrentHashMap<>(16);
    /**打榜*/
    private static final String ROUNDS = "rounds";
    /**miss*/
    private static final String MISSNUM = "missnum";
    /**守卫者*/
    private static final String  SHOU_WEI_ZHE = "守卫者";
    /**rank size*/
    private static final long MAX_NUM=20;
    /**rank type*/
    public static final int ROUNDS_TYPE = 1;
    /**miss 排行榜*/
    public static final int MISSNUM_TYPE = 2;

    /**
     * @param channelId 平台id
     * @param type      排行榜的key
     * @return 返回排行榜
     */
    @Override
    public List<TopRankInfo> getRankList(Integer channelId, Integer type) {
        //合并的key
        if (type == ROUNDS_TYPE) {
            return roundsRankMap.get(channelId);
        } else if (type == MISSNUM_TYPE) {
            return missRankMap.get(channelId);
        }
        return new ArrayList<>();
    }

    /**
     * @param channelId 平台id
     * @param type      排行榜的类型
     * @param uid    玩家的uid
     * @return 返回个人排名
     */
    @Override
    public TopRankInfo getMyTopRankInfo(Integer channelId, Integer type,Long uid) {
        String redisAddKey = getRedisZSetKey(channelId, type);
        Double score;
        long myRank = 0L;
        score = redisTemplate.opsForZSet().score(redisAddKey, uid);
        if (score != null) {
            Long aLong = redisTemplate.opsForZSet().reverseRank(redisAddKey, uid);
            if (aLong != null) {
                //排行榜第一名是0
                myRank = aLong + 1;
            }
        } else {
            score = 0D;
        }
        TopRankInfo topRankInfo = new TopRankInfo();
        topRankInfo.setRounds(score.intValue());
        topRankInfo.setRankunm(myRank);
        return topRankInfo;
    }


    /**
     * @param channelId 平台id
     * @param type      排行榜的类型
     * @param uid    玩家的uid
     * @param score     分数
     * @param isCover   是否覆盖
     * @return 返回添加结果
     */
    @Override
    public void addRank(Integer channelId, Integer type, Long uid, Double score, boolean isCover) {
        if (channelId != null && type != null && uid != null && score != null && score > 0) {
            String redisZSetKey = getRedisZSetKey(channelId, type);
            if (isCover) {
                redisTemplate.opsForZSet().add(redisZSetKey, uid, score);
            } else {
                redisTemplate.opsForZSet().incrementScore(redisZSetKey, uid, score);
            }
        }
    }


    /**
     * 玩家排行榜的热更新
     */
    @Scheduled(cron = "0 * */1 * * ?")
    public void task() {
        initServerInfo();
        updateRank();
    }


    /**
     * @return 返回一个redisZset的key
     */
    private String getRedisZSetKey(Integer channelId, Integer type) {
        Integer serverId = serverInfoMap.get(channelId).getChannelId();
        if (type == ROUNDS_TYPE) {
            return serverId + "_" + ROUNDS;
        } else if (type == MISSNUM_TYPE) {
            return serverId + "_" + MISSNUM;
        }
        throw new RuntimeException("this rank type not exit " + type);
    }

    /**
     * 关闭时关闭排行榜
     */
    @Override
    public void destroy() throws Exception {
        logger.info("delete redis zSet info");
        int type = 2;
        serverInfoMap.forEach((k, v) -> {
            for (int i = 1; i <= type; i++) {
                String redisZSetKey = getRedisZSetKey(v.getChannelId(), i);
                logger.info("delete redis rank key:{}", redisZSetKey);
                redisTemplate.delete(redisZSetKey);
            }
        });
    }

    /**
     * 开启时加载排行榜
     */
    @Override
    public void run(String... args) throws Exception {
        initServerInfo();
        initMysqlRankInfo();
        initServerRobot();
    }

    /**生成机器人*/
    private void initServerRobot(){
        logger.info("检测机器人生成情况");
        int robotMaxSize=100;
        serverInfoMap.forEach((k,v)->{
            Integer channelIdRobotRankNum = playerRankMapper.findChannelIdRobotRankNum(k);
            if (channelIdRobotRankNum==null||channelIdRobotRankNum<robotMaxSize){
                //阶乘
                int channelRideHundred = k * 100;
                //删除排行榜范围区间
                playerRankMapper.deleteByScopeUid(channelRideHundred,(channelRideHundred*2)-1);
                //删除用户的uuid
                playerInfoMapper.deleteByScopeUid(channelRideHundred,(channelRideHundred*2)-1);
                for (int i = 0; i < robotMaxSize; i++) {
                    PlayerRank playerRank = new PlayerRank();
                    playerRank.setUid((long)channelRideHundred);
                    playerRank.setIsopponent(false);
                    playerRank.setRanktotal(0);
                    playerRank.setRank((long)i+1);
                    playerRankMapper.insertSelective(playerRank);
                    PlayerInfo playerInfo = new PlayerInfo();
                    playerInfo.setUid((long)channelRideHundred);
                    playerInfo.setChannelid(k);
                    playerInfo.setOpenid(channelRideHundred+"");
                    playerInfo.setCreatetime(new Date());
                    playerInfo.setNickname(SHOU_WEI_ZHE);
                    playerInfoMapper.insertSelective(playerInfo);
                    channelRideHundred++;
                }
            }
        });
    }


    /**
     * 初始化服务器
     */
    private void initServerInfo() {
        logger.info("init gameserver info");
        List<ServerInfo> serverList = serverInfoMapper.serverInfoList();
        serverList.forEach(server -> serverInfoMap.put(server.getChannelId(), server));
    }

    /**
     * 初始化排行榜
     */
    private void initMysqlRankInfo() {
        logger.info("init redis zSet info");
        serverInfoMap.forEach((k, v) -> {
            Integer channelId = v.getChannelId();
            List<TopRankInfo> roundsList = playerDataMapper.initTopRank(channelId, ROUNDS);
            for (TopRankInfo topRankInfo : roundsList) {
                addRank(channelId, ROUNDS_TYPE, topRankInfo.getUid(), topRankInfo.getRounds().doubleValue(),true);
            }
            List<TopRankInfo> missList = playerDataMapper.initTopRank(channelId, MISSNUM);
            for (TopRankInfo topRankInfo : missList) {
                addRank(channelId, MISSNUM_TYPE,  topRankInfo.getUid(), topRankInfo.getRounds().doubleValue(),true);
            }
            logger.info("gameserver id:{},roundsListSize:{}", channelId, roundsList.size());
            logger.info("gameserver id:{},missListSize:{}", channelId, missList.size());
            missRankMap.put(channelId, missList);
            roundsRankMap.put(channelId, roundsList);
        });
    }


    /**
     * 更新排行榜
     */
    private void updateRank() {
        logger.info("update redis zSet info");
        serverInfoMap.forEach((k, v) -> {
            Integer channelId = v.getChannelId();
            //第一个排行榜
            String rounsdKey = getRedisZSetKey(channelId, ROUNDS_TYPE);
            Long roundsSize = redisTemplate.opsForZSet().size(rounsdKey);
            if (roundsSize == null) {
                roundsSize = 0L;
            }else {
                roundsSize=MAX_NUM;
            }
            roundsRankMap.put(channelId, updateTopRankInfo(rounsdKey, roundsSize));
            //第二个排行榜
            String missKey = getRedisZSetKey(channelId, MISSNUM_TYPE);
            Long missSize = redisTemplate.opsForZSet().size(missKey);
            if (missSize == null) {
                missSize = 0L;
            }else {
                missSize=MAX_NUM;
            }
            missRankMap.put(channelId, updateTopRankInfo(missKey, missSize));
        });
    }


    /**
     * 更新打榜
     */
    private List<TopRankInfo> updateTopRankInfo(String redisZSetKey, Long size) {
        List<TopRankInfo> list = new ArrayList<>(50);
        if (size == 0) {
            return list;
        }
        Set<ZSetOperations.TypedTuple<Object>> set = redisTemplate.opsForZSet().reverseRangeWithScores(redisZSetKey, 0, size);
        if (set != null && set.size() > 0) {
            int top = 1;
            for (ZSetOperations.TypedTuple<Object> tuple : set) {
                Double score = tuple.getScore();
                Long uid = (Long) tuple.getValue();
                if (score != null) {
                    TopRankInfo topRankInfo = new TopRankInfo(top, score.longValue());
                    //获取头像昵称
                    PlayerInfo playerInfo = playerInfoMapper.selectByPrimaryKey(uid);
                    if (playerInfo != null) {
                        String avatar = playerInfo.getMyAvatar();
                        String nickName = playerInfo.getMyNickName();
                        topRankInfo.setNickname(nickName);
                        topRankInfo.setAvatar(avatar);
                    } else {
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
