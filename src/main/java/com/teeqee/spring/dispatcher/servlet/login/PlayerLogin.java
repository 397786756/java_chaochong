package com.teeqee.spring.dispatcher.servlet.login;

import com.alibaba.fastjson.JSONObject;
import com.teeqee.mybatis.dao.PlayerDataMapper;
import com.teeqee.mybatis.dao.PlayerInfoMapper;
import com.teeqee.mybatis.dao.PlayerLogMapper;
import com.teeqee.mybatis.dao.PlayerRankMapper;
import com.teeqee.mybatis.pojo.PlayerData;
import com.teeqee.mybatis.pojo.PlayerInfo;
import com.teeqee.mybatis.pojo.PlayerLog;
import com.teeqee.mybatis.pojo.PlayerRank;
import com.teeqee.net.handler.Session;
import com.teeqee.spring.dispatcher.cmd.PlayerCmd;
import com.teeqee.spring.dispatcher.model.MethodModel;
import com.teeqee.spring.dispatcher.servlet.login.pingtai.entity.QuDaoLoginService;
import com.teeqee.spring.dispatcher.servlet.login.pingtai.qudao.QuDao;
import com.teeqee.spring.mode.annotation.Dispather;
import com.teeqee.spring.mode.annotation.DataSourceType;
import com.teeqee.utils.IdWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;


@DataSourceType("login")
@Service("playerLogin")
public class PlayerLogin {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**dao*/
    @Resource
    private PlayerDataMapper playerDataMapper;
    @Resource
    private PlayerLogMapper playerLogMapper;
    @Resource
    private PlayerInfoMapper playerInfoMapper;
    @Resource
    private PlayerRankMapper playerRankMapper;
    @Resource
    private QuDaoLoginService quDaoLoginService;

    @Dispather(value = "login")
    @Transactional(rollbackFor = Exception.class)
    public JSONObject login(MethodModel model)  {
        Session session = model.getSession();
        PlayerData playerData = session.getPlayerData();
        if (playerData ==null){
            JSONObject data = model.getData();
            if (data != null && data.size() > 0) {
                String openid = data.getString("openid");
                if (openid != null) {
                    //登录传过来的openid
                    tourist(session,openid, QuDao.TOURIST);
                }else {
                    //调用http接口
                    Integer channelid = data.getInteger("channelid");
                    String code = data.getString("code");
                    String openId = getOpenId(channelid, code);
                    if (openId!=null){
                        tourist(session,openId,channelid);
                    }else {
                        return errorLogin(channelid);
                    }
                }
            }
            return session.getPlayerData().loginPush();
        }else {
            return playerData.loginPush();
        }
    }


    /**
     * @param channelid 渠道
     * @return 返回错误码
     */
    private static JSONObject errorLogin(Integer channelid){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("error", 500);
        jsonObject.put("channelid", channelid);
        jsonObject.put("info", "please check your mobile phone");
        return jsonObject;
    }

    private String getOpenId( Integer channelid, String code) {
        if (channelid!=null&&code!=null){
            try {
                return   quDaoLoginService.login(channelid, code);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
          return null;
    }



    /**
     * @param openid 玩家的openid
     * @param channelid 渠道id
     */
    private void tourist(Session session, String openid, int channelid){
        //初始化用户隐私信息
        PlayerInfo playerInfo = createPlayerInfo(openid, channelid);
        Long uid = playerInfo.getUid();
        //初始化用户数据信息
        //初始化用户日志数据
        session.setUid(uid);
        session.setChannelid(channelid);
        PlayerLog playerLog = createPlayerLog(uid);
        //不初始化排行榜
        PlayerData playerData = localLogin(uid);
        //开启游客模式
        playerData.isTourist(openid);
        //TODO 排行榜手动取消
        PlayerRank playerRank = createPlayerRank(uid);
        session.add(PlayerCmd.PLAYER_RANK,playerRank);
        session.add(PlayerCmd.PLAYER_DATA,playerData);
        session.add(PlayerCmd.PLAYER_LOG, playerLog);
        session.add(PlayerCmd.PLAYER_INFO,playerInfo);
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
        Long uid = session.getUid();
        if (uid!=null){
            PlayerInfo playerInfo = session.getPlayerInfo();
            if (playerInfo!=null){
                return playerInfo.updateUserInfo(model.getData());
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
     * @param uid 玩家的uid
     * @return 登录的信息
     */
    private PlayerData localLogin(Long uid) {
            PlayerData playerData = playerDataMapper.selectByPrimaryKey(uid);
            //没有则直接创建
            if (playerData == null) {
                playerData = createPlayData(uid);
            }
           return playerData;
    }


    /**
     * @param uid 用户的id
     * @return 返回用户的数据源
     */
    private PlayerData createPlayData(Long uid) {
        PlayerData playerData = new PlayerData(uid);
        playerDataMapper.insertSelective(playerData);
        return playerData;
    }
    /**
     * @param uid 用户的id
     * @return 返回用户的数据源
     */
    private PlayerLog createPlayerLog(Long uid) {
        PlayerLog playerLog = playerLogMapper.selectByTimeLog(uid, new Date());
        if (playerLog==null){
            playerLog=new PlayerLog(uid,new Date());
            playerLogMapper.insertSelective(playerLog);
            logger.info("create playerLog uid:{}", uid);
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
        PlayerInfo playerInfo = playerInfoMapper.selectByOpenid(openId);
        if (playerInfo==null){
            playerInfo=new PlayerInfo();
            long uid = new IdWorker().nextId();
            playerInfo.setUid(uid);
            playerInfo.setOpenid(openId);
            //设置个渠道
            playerInfo.setChannelid(channelId);
            playerInfo.setCreatetime(new Date());
            playerInfoMapper.insertSelective(playerInfo);
            logger.info("create player",openId);
        }else {
            logger.info("push player",openId);
            if (playerInfo.getChannelid()==null){
                playerInfo.setChannelid(channelId);
            }
        }
        return playerInfo;
    }
    /**
     * @param uid 用户的id
     * @return 返回用户的数据源
     */
    private PlayerRank createPlayerRank(Long uid) {
        //排行榜初始化不添加用户的数据
        PlayerRank playerRank = playerRankMapper.selectByPrimaryKey(uid);
        if (playerRank==null){
            playerRank=new PlayerRank();
            playerRank.setUid(uid);
            playerRankMapper.insertSelective(playerRank);
        }
        return playerRank;
    }
}
