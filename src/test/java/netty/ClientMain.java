package netty;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.teeqee.norak.login.LoginRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import xyz.noark.core.lang.ByteArray;
import xyz.noark.core.network.NetworkProtocol;
import xyz.noark.core.util.ByteArrayUtils;
import xyz.noark.network.codec.json.SimpleJsonCodec;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;

public class ClientMain {
    public static void main(String[] args) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap boot = new Bootstrap();
        boot.option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .group(group)
                .handler(new LoggingHandler(LogLevel.INFO))
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline p = socketChannel.pipeline();
                        p.addLast(new ChannelHandler[]{new HttpClientCodec(),
                                new HttpObjectAggregator(1024 * 1024 * 10)});
                        p.addLast("hookedHandler", new WebSocketClientHandler());
                    }
                });
        URI websocketURI = new URI("wss://testchaochong.teeqee.com/chaochong");
        HttpHeaders httpHeaders = new DefaultHttpHeaders();
        //进行握手
        WebSocketClientHandshaker handshaker = WebSocketClientHandshakerFactory.newHandshaker(websocketURI, WebSocketVersion.V13, (String) null, true, httpHeaders);
        System.out.println("connect");
        final Channel channel = boot.connect(websocketURI.getHost(), websocketURI.getPort()).sync().channel();
        WebSocketClientHandler handler = (WebSocketClientHandler) channel.pipeline().get("hookedHandler");
        handler.setHandshaker(handshaker);
        handshaker.handshake(channel);
        //阻塞等待是否握手成功
        handler.handshakeFuture().sync();
        websocketClientSendJson(channel);
    }

    private static void websocketClientSendJson(Channel channel) {
        //创建一个对象
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("222");
        loginRequest.setPassword("222");
        NetworkProtocol networkProtocol = new NetworkProtocol(1002, loginRequest);
        SimpleJsonCodec simpleJsonCodec = new SimpleJsonCodec();
        byte[] body = simpleJsonCodec.encodePacket(networkProtocol).array();
        ByteBuf byteBuf = Unpooled.buffer().writeBytes(body);
        BinaryWebSocketFrame binaryWebSocketFrame = new BinaryWebSocketFrame(byteBuf);
        String str= ByteBufUtil.hexDump(byteBuf);
        System.out.println("发送报文格式:"+str);
        channel.writeAndFlush(binaryWebSocketFrame);

    }

}
