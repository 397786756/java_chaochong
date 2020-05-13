package com.teeqee;



import com.alibaba.fastjson.JSONObject;
import com.teeqee.mybatis.dao.PlayerDataMapper;
import com.teeqee.mybatis.dao.PlayerRankMapper;
import com.teeqee.mybatis.dao.ServerInfoMapper;
import com.teeqee.mybatis.pojo.PlayerRank;
import com.teeqee.spring.dispatcher.servlet.entity.TopRankInfo;
import com.teeqee.spring.dispatcher.servlet.rank.PlayerRankEntrance;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.annotation.Resource;
import java.util.List;

import static com.alibaba.fastjson.serializer.SerializerFeature.WriteMapNullValue;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChaoChongServerTest {

   @Resource
   private PlayerRankMapper playerRankMapper;
   @Resource
   private PlayerRankEntrance playerRankEntrance;

    @Test
    public void  test() throws Exception {
        PlayerRank playerRank = new PlayerRank();
        playerRank.setUid(222L);
        playerRank.setRank(6L);
        JSONObject jsonObject = playerRankEntrance.initPlayerWorldrank(playerRank, 1);
        System.out.println(jsonObject.toJSONString());
    }
}
