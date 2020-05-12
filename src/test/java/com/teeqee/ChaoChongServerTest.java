package com.teeqee;



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
        playerRankEntrance.initPlayerWorldrank(playerRank,1);
    }
}
