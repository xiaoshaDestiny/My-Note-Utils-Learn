package com.learn.netty.heartbeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author xu.rb
 * @since 2020-05-23 20:06
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

                            //加入Netty提供的Idle
                            //IdleStateHandler  Netty提供的处理空闲状态的处理器
                            //心跳检测机制
                            // (读空闲)  3表示3秒没有读写         就会发送心跳检测包 检测连接是否正常
                            // (写空闲)  5表示5秒内没有写事件
                            // (读写空闲)7表示7秒内没有读也没有写
                            // 当IdleStateHandler触发之后，会传递给管道的下一个Handler去处理 userEventTriggered
                            pipeline.addLast(new IdleStateHandler(3,5,7,TimeUnit.SECONDS));
                            pipeline.addLast(new MyServerHandler());
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
