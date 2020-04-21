package com.teeqee;



import com.teeqee.mybatis.dao.PlayerMapper;
import com.teeqee.spring.mode.service.DataSourceService;
import com.teeqee.spring.result.Result;
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
    private DataSourceService dataSourceService;
    @Resource
    private PlayerMapper playerMapper;

    @Test
    public void  test() throws Exception {
        playerMapper.selectByPrimaryKey("222");
    }
}
