package com.teeqee.controller.gm;

import com.teeqee.spring.result.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 测试springMvc
 * @Author: zhengsongjie
 */
@RestController
@RequestMapping("/test")
public class GMcontroller {
    @RequestMapping("/hello")
    public Result helloWord(){
        return new Result("hello chaochong",200);
    }
}
