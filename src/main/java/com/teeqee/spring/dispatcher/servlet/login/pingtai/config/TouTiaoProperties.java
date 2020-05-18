package com.teeqee.spring.dispatcher.servlet.login.pingtai.config;

import com.alibaba.fastjson.JSONObject;
import com.teeqee.utils.SpringbootHttpClientTest;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Data
@Component
@ConfigurationProperties(prefix = "toutiao.miniapp")
public class TouTiaoProperties implements CommandLineRunner {

    /**appId*/
    @Value("${toutiao.miniapp.appid}")
    private String appId;

    /**secret*/
    @Value("${toutiao.miniapp.secret}")
    private String secret;

    /**头条的URL*/
    private static String URL = "https://developer.toutiao.com/api/apps/jscode2session?appid=APPID&secret=SECRET&code=CODE";

    private static final String CODE= "CODE";
    private static final String ENCODING ="UTF-8";
    @Override
    public void run(String... args) throws Exception {
        URL=URL.replace("APPID", appId).replace("SECRET", secret);
    }


    public String getPlayerInfo(String code) throws IOException {
        if (code!=null){
            String url = URL.replace(CODE, code);
            String json = SpringbootHttpClientTest.sendPostData(url, ENCODING);
            if (json!=null){
                JSONObject jsonObject = JSONObject.parseObject(json);
                String openId = jsonObject.getString("open_id");
                if (openId!=null){
                    return openId;
                }
            }
        }
        return null;
    }
}
