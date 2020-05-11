package com.teeqee;



import com.teeqee.mybatis.dao.PlayerDataMapper;
import com.teeqee.mybatis.dao.ServerInfoMapper;
import com.teeqee.spring.dispatcher.servlet.entity.TopRankInfo;
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
   private ServerInfoMapper serverInfoMapper;
    @Resource
    private PlayerDataMapper playerDataMapper;


    @Test
    public void  test() throws Exception {
        List<TopRankInfo> list = playerDataMapper.initTopRank(10001, "rounds");
        for (TopRankInfo simpleTop : list) {
            System.out.println(simpleTop);
        }
    }
}
