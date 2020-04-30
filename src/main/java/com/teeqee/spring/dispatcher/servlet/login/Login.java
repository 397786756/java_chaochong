package com.teeqee.spring.dispatcher.servlet.login;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.teeqee.mybatis.dao.PlayerDataMapper;
import com.teeqee.mybatis.dao.PlayerInfoMapper;
import com.teeqee.mybatis.pojo.PlayerData;
import com.teeqee.mybatis.pojo.PlayerInfo;
import com.teeqee.net.handler.AbstractSession;
import com.teeqee.spring.dispatcher.cmd.PlayerCmd;
import com.teeqee.spring.dispatcher.model.MethodModel;
import com.teeqee.spring.mode.annotation.Dispather;
import com.teeqee.spring.mode.annotation.DataSourceType;
import com.teeqee.spring.result.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@DataSourceType("login")
@Service
public class Login {


    /**playerData dao*/
    @Resource
    private PlayerDataMapper playerDataMapper;

    @Dispather(value = "login")
    public JSONObject login(MethodModel model) {
        AbstractSession session = model.getSession();
        JSONObject data = model.getData();
        if (data != null && data.size() > 0) {
            String openid = data.getString("openid");
            if (openid != null) {
                PlayerData playerData = session.getPlayerData();
                if (playerData==null){
                    session.isLogin(openid);
                    playerData = localLogin(openid);
                    session.add(PlayerCmd.PLAYER_DATA,playerData);
                }
                return playerData.loginPush();
            }
        }
        //返回openid异常
        return null;
    }

    /**
     * @param model 数据源
     * @return 返回获取宠物的位置信息
     */
    @Dispather(value = "getsite")
    public JSONObject getsite(MethodModel model){
        JSONObject getsite = model.getSession().getPlayerData().getsite();
        return getsite;
    }

    /**
     * @param model 数据源
     * @return 拉取玩家动物信息
     */
    @Dispather(value = "gettask")
    public JSONObject gettask(MethodModel model){
        return model.getSession().getPlayerData().gettask();
    }

    /**
     * @param model 数据源
     * @return 拉取玩家动物信息
     */
    @Dispather(value = "getanimal")
    public JSONObject getanimal(MethodModel model){
        return model.getSession().getPlayerData().getanimal();
    }



    /**
     * @param openId 用户的openId
     * @return 本地登录
     */

    private PlayerData localLogin(String openId) {
        if (openId != null && openId.length() > 0) {
            PlayerData playerData = playerDataMapper.selectByPrimaryKey(openId);
            //没有则直接创建
            if (playerData == null) {
                playerData = createPlayData(openId);
            }
           return playerData;
        }
        return null;
    }


    /**
     * @param openId 用户的id
     * @return 返回用户的数据源
     */
    private PlayerData createPlayData(String openId) {
        PlayerData playerData = new PlayerData(openId);
        playerDataMapper.insertSelective(playerData);
        return playerData;
    }
}
