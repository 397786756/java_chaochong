package com.teeqee.norak.mapping;

import com.alibaba.fastjson.JSONObject;
import com.teeqee.norak.login.LoginRequest;
import xyz.noark.core.annotation.Controller;
import xyz.noark.core.annotation.controller.ExecThreadGroup;
import xyz.noark.core.annotation.controller.PacketMapping;
import xyz.noark.core.network.NetworkProtocol;
import xyz.noark.core.network.Session;

import static xyz.noark.log.LogHelper.logger;

@Controller(threadGroup = ExecThreadGroup.ModuleThreadGroup)
public class MailController {

    @PacketMapping(opcode = 1002, state = Session.State.ALL)
    public void loginGame(Session session, LoginRequest request) {
        //转发回去
        logger.info("登录请求 username={}, password={}", request.getUsername(), request.getPassword());
        session.send(1002, request);
    }
}