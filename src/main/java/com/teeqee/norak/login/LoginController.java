package com.teeqee.norak.login;

import xyz.noark.core.annotation.Controller;
import xyz.noark.core.annotation.controller.ExecThreadGroup;
import xyz.noark.core.annotation.controller.PacketMapping;
import xyz.noark.core.network.Session;

import static xyz.noark.log.LogHelper.logger;

@Controller(threadGroup = ExecThreadGroup.ModuleThreadGroup)
public class LoginController {
	@PacketMapping(opcode = 1001, state = Session.State.CONNECTED)
	public void login(LoginRequest request) {
		logger.info("登录请求 username={}, password={}", request.getUsername(), request.getPassword());
	}
}