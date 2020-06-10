package com.teeqee.spring.dispatcher.servlet.login.pingtai.config;


import com.alibaba.fastjson.JSONObject;
import com.teeqee.utils.SpringbootHttpClientTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Description: 微信端配置
 * @author: zhengsongjie
 */
@Component
@ConfigurationProperties(prefix = "wx.miniapp")
public class WeiXinGameProperties implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${wx.miniapp.appid}")
    private String appid;

    @Value("${wx.miniapp.secret}")
    private String secret;

    @Value("${wx.miniapp.aesKey}")
    private String aesKey;

    private  static String URL = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";

    private static final String JSCODE="JSCODE";

    private static final String ENCODING ="UTF-8";

    @Override
    public void run(String... args) throws Exception {
        URL=URL.replace("APPID", appid).replace("SECRET", secret);
    }

    /**
     * @param code code
     * @return 返回openId
     * @throws IOException
     */
    public String getPlayerInfo(String code) throws IOException {
        if (code!=null){
            String url = URL.replace(JSCODE, code);
            String json = SpringbootHttpClientTest.sendPostData(url, ENCODING);
            if (json!=null){
                JSONObject jsonObject = JSONObject.parseObject(json);
                String openId = jsonObject.getString("openid");
                if (openId!=null){
                    return openId;
                }else {
                    logger.info("wechat:{}",json);
                }
            }
        }
        return null;
    }
}
