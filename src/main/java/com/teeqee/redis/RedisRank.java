package com.teeqee.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description: redis排行榜
 * @author : zhengosngjie
 * @Software: IntelliJ IDEA
 */
@Component
public class RedisRank {
    @Resource
    private RedisTemplate redisTemplate;


}
