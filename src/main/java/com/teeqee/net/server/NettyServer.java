package com.teeqee.net.server;


import com.teeqee.net.handler.WebsocketServerHandler;
import com.teeqee.spring.event.SessionEventService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketFrameAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * netty服务器
 */
@Component("nettyServer")
@Order(2)
public class NettyServer implements CommandLineRunner, DisposableBean {

    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);

    @Value("${server.socket-port}")
    private int socketPort;
    @Value("${server.socket-uri}")
    private String socketUri;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    /**
     * 添加静态管道
     */
    private static Channel serverChannel;

    @Resource
    private WebsocketServerHandler websocketServerHandler;
    @Override
    public void run(String... args) throws Exception {
        logger.info("chaochong server open");
        Thread.sleep(5000);
        bind();
    }

    private void bind() throws InterruptedException {
        //制定boss线程数为4
        bossGroup = new NioEventLoopGroup(4);
        workerGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                //backlog对程序的连接数没影响，但是影响的是还没有被accept取出的连接
                .option(ChannelOption.SO_BACKLOG, 1024)
                // 日志监听,DEBUG模式
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        //添加心跳机制(心跳狗)
                        channel.pipeline().addLast("heart-dog", new IdleStateHandler(500, 500, 600, TimeUnit.SECONDS));
                        // WebSocket 是基于 Http 协议的，要使用 Http 解编码器
                        channel.pipeline().addLast("http-codec", new HttpServerCodec());
                        // 用于大数据流的分区传输
                        channel.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
                        // 将多个消息转换为单一的 request 或者 response 对象，最终得到的是 FullHttpRequest 对象
                        channel.pipeline().addLast("aggregator", new HttpObjectAggregator(65535));
                        channel.pipeline().addLast(new WebSocketFrameAggregator(65535));
                        // 创建 WebSocket 之前会有唯一一次 Http 请求 (Header 中包含 Upgrade 并且值为 websocketne)
                        // channel.pipeline().addLast("http-request", httpRequestHandler);
                        //顺序放错了
                        channel.pipeline().addLast("WebsocketServerHandler", websocketServerHandler);
                        // 处理所有委托管理的 WebSocket 帧类型以及握手本身
                        // 入参是 ws://gameserver:port/context_path 中的 contex_path
                        channel.pipeline().addLast("websocket-gameserver", new WebSocketServerProtocolHandler(socketUri));
                        // WebSocket RFC 定义了 6 种帧，TextWebSocketFrame 是我们唯一真正需要处理的帧类型
                        // channel.pipeline().addLast("webSocketFrameHandler", webSocketFrameHandler);
                        //添加通道关闭 在上面的idleStateHandler添加即可
                        //channel.pipeline().addLast("heartBeatHandler",new HeartBeatHandler());
                    }
                });
        ChannelFuture future = serverBootstrap.bind(socketPort).sync();
        future.addListener(fl -> {
            if (fl.isSuccess()) {
                serverChannel = future.channel();
                logger.info("Netty gameserver start");
                logger.info("gameserver port = {}",socketPort);
            }
        });
        future.channel().closeFuture().addListener(fl -> {
            //关闭netty服务器
            this.close();
        });
    }

    private void close() {
        //优雅关闭
        if (serverChannel != null) {
            serverChannel.close();
        }
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
        logger.info("Netty gameserver byeBye");
    }

    @Override
    public void destroy() throws Exception {
        close();
    }
}