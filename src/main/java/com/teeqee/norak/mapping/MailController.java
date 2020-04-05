package com.teeqee.norak.mapping;

import com.alibaba.fastjson.JSONObject;
import xyz.noark.core.annotation.Controller;
import xyz.noark.core.annotation.controller.ExecThreadGroup;
import xyz.noark.core.annotation.controller.PacketMapping;
import xyz.noark.core.network.NetworkProtocol;
import xyz.noark.core.network.Session;

@Controller(threadGroup = ExecThreadGroup.PlayerThreadGroup)
public class MailController {

    @PacketMapping(opcode = 1002, state = Session.State.ALL)
    public void loginGame(Session session, JSONObject jsonObject) {
        try {
            System.out.println(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(jsonObject);
        }
    }
}