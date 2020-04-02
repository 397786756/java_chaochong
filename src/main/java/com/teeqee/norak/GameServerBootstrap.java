package com.teeqee.norak;


import org.springframework.stereotype.Component;
import xyz.noark.game.bootstrap.BaseServerBootstrap;

/**
 * @Description:
 * @Author: zhengsongjie
 * @File: GameServer
 * @Version: 1.0.0
 * @Time: 2019-12-19 下午 12:50
 * @Project: demo
 * @Package: com.com.teeqee.com.teeqee.component
 * @Software: IntelliJ IDEA
 */
@Component
public class GameServerBootstrap  extends BaseServerBootstrap {
    @Override protected String getServerName() {
        return "game-server";
    }

}
