package com.teeqee.norak;


import xyz.noark.game.Noark;


public class NettyServer {

    public static void main(String[] args)  {
        Noark.run(GameServerBootstrap.class, args);
    }
}
