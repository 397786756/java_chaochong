package com.teeqee.spring.dispatcher.servlet.login;

import com.alibaba.fastjson.JSONObject;
import com.teeqee.mybatis.dao.PlayerDataMapper;
import com.teeqee.mybatis.dao.PlayerInfoMapper;
import com.teeqee.mybatis.dao.PlayerLogMapper;
import com.teeqee.mybatis.pojo.PlayerData;
import com.teeqee.mybatis.pojo.PlayerInfo;
import com.teeqee.mybatis.pojo.PlayerLog;
import com.teeqee.net.gm.ChannelSupervise;
import com.teeqee.net.handler.Session;
import com.teeqee.spring.dispatcher.cmd.PlayerCmd;
import com.teeqee.spring.dispatcher.model.MethodModel;
import com.teeqee.spring.mode.annotation.Dispather;
import com.teeqee.spring.mode.annotation.DataSourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;


@DataSourceType("login")
@Service
public class PlayerLogin {
    /**测试服*/
    private static final int TEST_SERVER=1000;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**playerData dao*/
    @Resource
    private PlayerDataMapper playerDataMapper;
    /**playerLog dao*/
    @Resource
    private PlayerLogMapper playerLogMapper;
    @Resource
    private PlayerInfoMapper playerInfoMapper;

    @Dispather(value = "login")
    public JSONObject login(MethodModel model) {
        Session session = model.getSession();
        //判断是否有重复登录
        PlayerData playerData = session.getPlayerData();
        if (playerData ==null){
            JSONObject data = model.getData();
            if (data != null && data.size() > 0) {
                String openid = data.getString("openid");
                if (openid != null) {
                    //修改为已经登录
                    session.isLogin(openid);
                    session.setChannelid(TEST_SERVER);
                    //TODO 平台
                    //获取数据
                    playerData = localLogin(openid);
                    session.add(PlayerCmd.PLAYER_DATA,playerData);
                    session.add(PlayerCmd.PLAYER_LOG, createPlayerLog(openid));
                    session.add(PlayerCmd.PLAYER_INFO,createPlayerInfo(openid,TEST_SERVER));
                    return playerData.loginPush();
                }else {
                    //调用http接口
                }
            }
        }else {
            //直接返回
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
    public boolean userinfor(MethodModel model){
        Session session = model.getSession();
        //TODO code
        String openId = session.getOpenId();
        if (openId!=null){
            PlayerInfo playerInfo = session.getPlayerInfo();
            if (playerInfo!=null){
                return playerInfo.updateUserInfo(model.getData());
            }else {
                //这边可以直接不查询sql
                //TODO
                PlayerInfo playerInfoMysql = playerInfoMapper.selectByPrimaryKey(openId);
                if (playerInfoMysql==null){
                    playerInfoMysql=new PlayerInfo(openId);
                    playerInfoMapper.insertSelective(playerInfoMysql);
                }
                session.add(PlayerCmd.PLAYER_INFO, playerInfoMysql);
                return playerInfoMysql.updateUserInfo(model.getData()) ;
            }
        }
        return false;
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
    /**
     * @param openId 用户的id
     * @param channelId 平台组
     * @return 返回用户的数据源
     */
    private PlayerInfo createPlayerInfo(String openId,Integer channelId) {
        PlayerInfo playerInfo = playerInfoMapper.selectByPrimaryKey(openId);
        if (playerInfo==null){
            playerInfo=new PlayerInfo();
            playerInfo.setOpenid(openId);
            //设置个渠道
            playerInfo.setChannelid(channelId);
            playerInfo.setCreatetime(new Date());
            playerInfoMapper.insertSelective(playerInfo);
        }else {
            if (playerInfo.getChannelid()==null){
                playerInfo.setChannelid(channelId);
            }
        }
        return playerInfo;
    }
}
