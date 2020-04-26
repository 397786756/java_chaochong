package com.teeqee;



import com.teeqee.mybatis.dao.PlayerDataMapper;
import com.teeqee.mybatis.dao.PlayerInfoMapper;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChaoChongServerTest {

   @Resource
   private PlayerInfoMapper playerInfoMapper;
   @Resource
   private PlayerDataMapper playerDataMapper;

    @Test
    public void  test() throws Exception {
        playerInfoMapper.selectByPrimaryKey(2222+"");
        playerDataMapper.selectByPrimaryKey(2222+"");
    }
}
