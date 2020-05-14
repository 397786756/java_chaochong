package com.teeqee;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.teeqee.mybatis.dao.PlayerDataMapper;
import com.teeqee.mybatis.dao.PlayerRankLogMapper;
import com.teeqee.mybatis.dao.PlayerRankMapper;
import com.teeqee.mybatis.dao.ServerInfoMapper;
import com.teeqee.mybatis.pojo.PlayerRank;
import com.teeqee.mybatis.pojo.PlayerRankLog;
import com.teeqee.spring.dispatcher.servlet.entity.Opponent;
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
   private PlayerRankEntrance playerRankEntrance;

    @Test
    public void  test() throws Exception {
        String s = playerRankEntrance.checkAnimalJosn("[{\"animalid\":8,\"lv\":0,\"blood\":432,\"attack\":232,\"defense\":78},{\"animalid\":4,\"lv\":0,\"blood\":368,\"attack\":345,\"defense\":65},{\"animalid\":3,\"lv\":2,\"blood\":880,\"attack\":283,\"defense\":93},{\"animalid\":3,\"lv\":2,\"blood\":880,\"attack\":283,\"defense\":93},{\"animalid\":1,\"lv\":4,\"blood\":1440,\"attack\":160,\"defense\":126}]");
        System.out.println(JSONObject.toJSONString(s));
        System.out.println(playerRankEntrance.selectChannelidPlayerRank(1, 2));
    }
}
