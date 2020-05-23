package com.learn.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author xu.rb
 * @since 2020-05-23 20:36
 */
public class MyServer {
    public static void main(String[] args) throws InterruptedException {

        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(8);

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();

                            //因为基于Http协议进行开发  所以要使用Http的编码，解码器
                            pipeline.addLast(new HttpServerCodec());

                            //因为是一块形式写 添加ChunkedWriteHandler处理器
                            pipeline.addLast(new ChunkedWriteHandler());

                            /**
                             * 因为http协议数据在传输的时候是分段的  将多个段聚合起来
                             * 浏览器发送大量数据的时候 就会发送出多次的http请求
                             */
                            pipeline.addLast(new HttpObjectAggregator(8192));

                            /**
                             * websocket 数据是以帧(frame)形式传输的
                             * 浏览器发送请求  ws://localhost:7000/xxx
                             * WebSocketServerProtocolHandler 将一个http协议升级成为一个ws协议 也就能保持长连接了
                             */
                            pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));

                            //处理业务逻辑的handler
                            pipeline.addLast(new MyTestWebSocketFrameHandler());

                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(7000).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }
}
