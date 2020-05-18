package com.teeqee;



import com.teeqee.mybatis.dao.PlayerRankMapper;
import com.teeqee.mybatis.pojo.PlayerRank;
import com.teeqee.spring.dispatcher.servlet.rank.PlayerRankEntrance;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import javax.annotation.Resource;
import java.util.Set;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChaoChongServerTest {

   @Resource
   private PlayerRankEntrance playerRankEntrance;
   @Resource
   private RedisTemplate redisTemplate;
    @Test
    public void  test() throws Exception {

    }
}
