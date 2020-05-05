package com.teeqee.spring.dispatcher.servlet.rank.service;

import com.teeqee.spring.dispatcher.servlet.rank.entity.MissRankInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

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
    /**未名中的排行榜*/
    private ConcurrentHashMap<String,List<MissRankInfo>> rankMap=new ConcurrentHashMap<>(16);

    @Override
    public List<MissRankInfo> getRankList(Integer pingTai, String redisKey) {
        //合并的key
        String addRedisKey=pingTai+"_"+redisKey;
        List<MissRankInfo> missRankInfos = rankMap.get(addRedisKey);
        if (missRankInfos==null){

        }
        return missRankInfos;
    }

    @Scheduled(cron = "0/60 * * * * ?")
    public void task(){

    }

    /**
     * 关闭时关闭排行榜
     */
    @Override
    public void destroy() throws Exception {

    }

    /**
     * 开启时加载排行榜
     */
    @Override
    public void run(String... args) throws Exception {
        List<MissRankInfo> list = new ArrayList<>(16);
        MissRankInfo missRankInfo = new MissRankInfo(2,"玩家","", 20, 1L);
        list.add(missRankInfo);
        rankMap.put("1000_toplistmissnum",list);
    }
}
