package com.teeqee.spring.dispatcher.servlet.login;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.teeqee.mybatis.dao.PlayerDataMapper;
import com.teeqee.mybatis.dao.PlayerInfoMapper;
import com.teeqee.mybatis.dao.PlayerLogMapper;
import com.teeqee.mybatis.pojo.PlayerData;
import com.teeqee.mybatis.pojo.PlayerInfo;
import com.teeqee.mybatis.pojo.PlayerLog;
import com.teeqee.net.handler.AbstractSession;
import com.teeqee.spring.dispatcher.cmd.PlayerCmd;
import com.teeqee.spring.dispatcher.model.MethodModel;
import com.teeqee.spring.mode.annotation.Dispather;
import com.teeqee.spring.mode.annotation.DataSourceType;
import com.teeqee.spring.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;


@DataSourceType("login")
@Service
public class Login {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**playerData dao*/
    @Resource
    private PlayerDataMapper playerDataMapper;
    /**playerLog dao*/
    @Resource
    private PlayerLogMapper playerLogMapper;

    @Dispather(value = "login")
    public JSONObject login(MethodModel model) {
        AbstractSession session = model.getSession();
        //判断是否有重复登录
        PlayerData playerData = session.getPlayerData();
        if (playerData ==null){
            JSONObject data = model.getData();
            if (data != null && data.size() > 0) {
                String openid = data.getString("openid");
                if (openid != null) {
                    //修改为已经登录
                    session.isLogin(openid);
                    //获取数据
                    playerData = localLogin(openid);
                    //添加进去
                    session.add(PlayerCmd.PLAYER_DATA,playerData);
                    session.add(PlayerCmd.PLAYER_LOG,createPlayerLog(openid));
                    return playerData.loginPush();
                }
            }
        }else {
            return playerData.loginPush();
        }
        return null;
    }

    /**
     * @param model 数据源
     * @return 拉取建筑
     */
    @Dispather(value = "getbuilding")
    public JSONObject getbuilding(MethodModel model){
        return model.getSession().getPlayerData().getbuilding();
    }
    /**
     * @param model 数据源
     * @return 返回获取宠物的位置信息
     */
    @Dispather(value = "getsite")
    public JSONObject getsite(MethodModel model){
        return model.getSession().getPlayerData().getsite();
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
     * @param model 数据源
     * @return 修改玩家隐私信息
     */
    @Dispather(value = "userinfor")
    public JSONObject userinfor(MethodModel model){
        JSONObject data = model.getData();
        model.getSession().getPlayerInfo().updateUserInfo(data);
        return null;
    }
    /**
     * @param model 数据源
     * @return 从后端拉取缓存
     */
    @Dispather(value = "getcache")
    public JSONObject getcache(MethodModel model) {
        return model.getSession().getPlayerData().getCache();
    }

    /**
     * @param openId 用户的openId
     * @return 登录的信息
     */

    private PlayerData localLogin(String openId) {
            PlayerData playerData = playerDataMapper.selectByPrimaryKey(openId);
            //没有则直接创建
            if (playerData == null) {
                playerData = createPlayData(openId);
            }
           return playerData;
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
    /**
     * @param openId 用户的id
     * @return 返回用户的数据源
     */
    private PlayerLog createPlayerLog(String openId) {
        PlayerLog playerLog = playerLogMapper.selectByTimeLog(openId, new Date());
        if (playerLog==null){
            playerLog=new PlayerLog(openId,new Date());
            playerLogMapper.insertSelective(playerLog);
            logger.info("create player openid:{}", openId);
        }else {
            //登录次数+1
            playerLog.loginTotalAdd();
        }
        return playerLog;
    }

}
