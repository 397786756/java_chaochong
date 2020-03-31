package com.teeqee.spring.mode.strategy.annotation;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Component
@DataSourceType("faceRecognition")
@Service
public class FaceRecognition implements DataSourceStrategy {
    @Override
    public Map<String, Object> connect(Map<String, String> params) {
        //do something....
        //返回结果
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("type", "人脸识别");
        map.put("status", "success");
        return map;
    }
}
