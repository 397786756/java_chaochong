package com.teeqee.netty.echo.server;

import com.teeqee.netty.echo.handler.EchoServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 *我是一个服务器
 */
public class EchoServer {
    private final int port;
    private final String name;
    public EchoServer(int port ,String name) {
        this.port=port;
        this.name=name;
    }

    public void serverStar() throws Exception{
        EchoServerHandler echoServerHandler = new EchoServerHandler();
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup,workGroup).
                channel(NioServerSocketChannel.class).
                localAddress(new InetSocketAddress(port)).
                childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) {
                ch.pipeline().addLast(echoServerHandler);
            }
        });
        ChannelFuture future;
        try {
            future = serverBootstrap.bind().sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully().sync();
            workGroup.shutdownGracefully().sync();
        }
    }
}
