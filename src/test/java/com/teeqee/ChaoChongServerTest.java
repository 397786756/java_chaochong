package com.teeqee;



import com.alibaba.fastjson.JSONObject;
import com.teeqee.mybatis.dao.PlayerRankMapper;
import com.teeqee.mybatis.pojo.PlayerRank;
import com.teeqee.spring.dispatcher.servlet.entity.Opponent;
import com.teeqee.spring.dispatcher.servlet.rank.PlayerRankEntrance;
import com.teeqee.spring.dispatcher.servlet.rank.service.RedisService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChaoChongServerTest {

   @Resource
   private PlayerRankEntrance playerRankEntrance;
   @Resource
   private RedisTemplate redisTemplate;
    @Resource
    private RedisService redisService;
    @Test
    public void  test() throws Exception {
       String key="1_toplist";
       long rank =400L;
        for (int i = 0; i < 1000; i++) {
            redisTemplate.opsForZSet().add(key, i+"",i);
        }
       // for (long i = 0; i < 100L; i++) {
       //     List<Long> topRankList = redisService.getTopRankList(1, 3, 64646454L);
       //     System.out.println(topRankList);
       // }
    }
}
