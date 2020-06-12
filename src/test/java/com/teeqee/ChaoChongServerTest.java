package com.teeqee;



import com.alibaba.fastjson.JSONObject;
import com.teeqee.mybatis.dao.PlayerRankMapper;
import com.teeqee.mybatis.pojo.PlayerRank;
import com.teeqee.spring.dispatcher.servlet.entity.Opponent;
import com.teeqee.spring.dispatcher.servlet.login.pingtai.config.TouTiaoProperties;
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
   private TouTiaoProperties touTiaoProperties;

   @Test
    public void  test() throws Exception {
        String code="24d3750f8321af65";
        String playerInfo = touTiaoProperties.getPlayerInfo(code);
        System.out.println(playerInfo);
    }

}
