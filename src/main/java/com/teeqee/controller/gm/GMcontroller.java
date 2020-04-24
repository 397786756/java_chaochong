package com.teeqee.controller.gm;

import com.teeqee.spring.result.Result;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 测试springMvc
 * @Author: zhengsongjie
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/test",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
public class GMcontroller {
    @RequestMapping("/hello")
    public Result helloWord(){
        return new Result("hello chaochong",200);
    }
}
