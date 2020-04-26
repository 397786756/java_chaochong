package com.teeqee.spring.event;

import com.teeqee.mybatis.dao.PlayerMapper;
import com.teeqee.net.gm.ChannelSupervise;
import com.teeqee.net.handler.AbstractSession;
import com.teeqee.spring.mode.service.DataSourceService;
import com.teeqee.spring.result.Result;
import com.teeqee.utils.UriUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.Map;

/**
 * @Description: 会话处理器
 * @author: zhengsongjie
 * @Software: IntelliJ IDEA
 */
@Component
public class SessionEventServiceImpl implements SessionEventService<AbstractSession> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private DataSourceService dataSourceService;



    @Override
    public void open(AbstractSession session) {
        logger.info("clint open={}", session.getChannel().id().asLongText());
    }

    @Override
    public void close(AbstractSession session) {
        logger.info("client close={}", session.getChannel().id().asLongText());
        logger.info("update playerInfo");
        ChannelSupervise.removeSession(session);
    }

    @Override
    public void send(String msg, AbstractSession session) {
        logger.info("client msg ={}", msg);
        Object connect = dataSourceService.connect(msg, null, session);
        ChannelSupervise.sendToUser(session.getChannel().id(), connect);
    }

    @Override
    public void exceptionCaught(AbstractSession session) {
        logger.info("client exceptionCaught ={}", session.getChannel().id().asLongText());
    }
}
