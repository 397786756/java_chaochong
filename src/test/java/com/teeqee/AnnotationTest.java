package com.teeqee;



import com.teeqee.spring.mode.strategy.annotation.DataSourceService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Map;

@SpringBootTest
public class AnnotationTest {
    @Resource
    private DataSourceService dataSourceService;

    @Test
    public void  test(){
        try {
            Map<String, Object> map = dataSourceService.connect("fourElements", null);
            System.out.println(map);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("服务器指令异常");
        }
    }
}
