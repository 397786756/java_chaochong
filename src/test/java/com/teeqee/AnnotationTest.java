package com.teeqee;



import com.teeqee.spring.mode.service.DataSourceService;
import com.teeqee.spring.result.Result;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Map;

@SpringBootTest
public class AnnotationTest {
    @Resource
    private DataSourceService dataSourceService;

    @Test
    public void  test() throws Exception {
        Result fourElements = dataSourceService.connect("fourElements", null, null);
        System.out.println(fourElements);
        Object o = dataSourceService.connectMethod("log", null, null);
        System.out.println(o);
    }
}
