package com.teeqee.spring.event;

import com.alibaba.fastjson.JSONObject;
import com.teeqee.mybatis.dao.PlayerDataMapper;
import com.teeqee.mybatis.dao.PlayerInfoMapper;
import com.teeqee.mybatis.dao.PlayerLogMapper;
import com.teeqee.mybatis.pojo.PlayerData;
import com.teeqee.mybatis.pojo.PlayerInfo;
import com.teeqee.mybatis.pojo.PlayerLog;
import com.teeqee.net.gm.ChannelSupervise;
import com.teeqee.net.handler.AbstractSession;
import com.teeqee.spring.dispatcher.method.MethodMapper;
import com.teeqee.spring.dispatcher.model.MethodModel;
import com.teeqee.spring.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description: 会话处理器
 * @author: zhengsongjie
 * @Software: IntelliJ IDEA
 */
@Component
public class SessionEventServiceImpl implements SessionEventService<AbstractSession> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private MethodMapper methodMapper;
    @Resource
    private PlayerDataMapper playerDataMapper;
    @Resource
    private PlayerLogMapper playerLogMapper;
    @Resource
    private PlayerInfoMapper playerInfoMapper;


    @Override
    public void open(AbstractSession session) {
        logger.info("clint open={}", session.getChannel().id().asLongText());
    }

    @Override
    public void close(AbstractSession session) {
        offLine(session);
        ChannelSupervise.removeSession(session);
    }

    @Override
    public void send(String msg, AbstractSession session) throws Exception {
        logger.info("client msg ={}", msg);
        try {
            JSONObject jsonObject = JSONObject.parseObject(msg);
            String cmd = jsonObject.getString("cmd");
            if (cmd!=null){
                PlayerLog playerLog = session.getPlayerLog();
                if (playerLog!=null){
                    //操作次数+1
                    playerLog.messageNumAdd();
                }
                JSONObject data = jsonObject.getJSONObject("data");
                MethodModel methodModel = new MethodModel(cmd, data, session);
                Result result = methodMapper.run(methodModel);
                if (result!=null){
                    ChannelSupervise.sendToUser(session.getChannel().id(), result);
                }
            }
        } catch (Exception e) {
           // session.getChannel().close();
            e.printStackTrace();
        }
    }

    @Override
    public void exceptionCaught(AbstractSession session) {
        logger.info("client exceptionCaught ={}", session.getChannel().id().asLongText());
    }



    private void offLine(AbstractSession session){
        logger.info("client close={}", session.getChannel().id().asLongText());
        logger.info("update playerInfo");
        //存数据
        PlayerData playerData = session.getPlayerData();
        if (playerData!=null){
            playerDataMapper.updateByPrimaryKeySelective(playerData);
        }
        //修改日志
        PlayerLog playerLog = session.getPlayerLog();
        if (playerLog!=null){
            playerLogMapper.updateByPrimaryKeySelective(playerLog);
        }
        //头像昵称
        PlayerInfo playerInfo = session.getPlayerInfo();
        if (playerInfo!=null){
            playerInfoMapper.updateByPrimaryKeySelective(playerInfo);
        }
        ChannelSupervise.removeSession(session);
        //TODO 用户下线
    }
}
