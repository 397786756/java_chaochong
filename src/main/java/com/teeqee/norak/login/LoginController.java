package com.teeqee.norak.login;

import xyz.noark.core.annotation.Controller;
import xyz.noark.core.annotation.controller.ExecThreadGroup;
import xyz.noark.core.annotation.controller.PacketMapping;
import xyz.noark.core.network.Session;

import static xyz.noark.log.LogHelper.logger;

@Controller(threadGroup = ExecThreadGroup.PlayerThreadGroup)
public class LoginController {
    @PacketMapping(opcode = 1001, state = Session.State.ALL)
    public void login(Session session, LoginRequest request) {
        logger.info("登录请求 username={}, password={}", request.getUsername(), request.getPassword());
        session.send(1002, request);
    }
}