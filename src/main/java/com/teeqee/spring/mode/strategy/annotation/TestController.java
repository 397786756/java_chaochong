package com.teeqee.spring.mode.strategy.annotation;


import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping(value = "/test")
public class TestController {
    @Resource
    private DataSourceService dataSourceService;

    @RequestMapping("/strategy")
    public Object test(@RequestBody JSONObject jsonObject) {
        String cmd = jsonObject.getString("cmd");
        return dataSourceService.connect(cmd, null);
    }
}
