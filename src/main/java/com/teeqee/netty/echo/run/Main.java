package com.teeqee.netty.echo.run;

import com.teeqee.netty.echo.server.EchoServer;

public class Main {
    public static void main(String[] args) {
        try {
            new EchoServer(9527,"服务器").serverStar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
