package com.teeqee.spring.dispatcher.servlet.login.pingtai.entity;

import com.teeqee.spring.dispatcher.servlet.login.pingtai.config.TouTiaoProperties;
import com.teeqee.spring.dispatcher.servlet.login.pingtai.config.WeiXinGameProperties;
import com.teeqee.spring.dispatcher.servlet.login.pingtai.qudao.QuDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @Description: 用户登录信息
 * @Author: zhengsongjie
 * @Software: IntelliJ IDEA
 */
@Service("quDaoLogin")
public class QuDaoLoginService {
    /**微信*/
    @Resource
    private WeiXinGameProperties weiXinGameProperties;
    @Resource
    private TouTiaoProperties touTiaoProperties;
    /**logger*/
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public String login(Integer channelId,String code) throws IOException {

        String openId = null;
        if (channelId== QuDao.WECHAT){
            //微信端
             openId = weiXinGameProperties.getPlayerInfo(code);
        }else if (channelId==QuDao.TOU_TIAO){
            //头条端
            openId = touTiaoProperties.getPlayerInfo(code);
        }
        logger.info("channelId:{},code:{},openId:{}",channelId,code,openId);
        //防止部分平台传空串的openId
        if ("".equals(openId)){
            return null;
        }
        return openId;
    }
}
