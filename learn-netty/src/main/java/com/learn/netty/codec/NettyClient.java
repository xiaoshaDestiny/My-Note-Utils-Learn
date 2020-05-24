package com.learn.netty.codec;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

/**
 * @author xu.rb
 * @since 2020-05-21 15:06
 */
public class NettyClient {
    public static void main(String[] args) throws InterruptedException {

        //客户端只需要一个事件循环组即可
        NioEventLoopGroup group = new NioEventLoopGroup();

        try {
            //启动助手类 服务端用ServerBootstrap  客户端用BootStrap
            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(group)//线程组
                    .channel(NioSocketChannel.class)//设置客户端通道的实现类
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast("encoder",new ProtobufEncoder());
                            socketChannel.pipeline().addLast(new NettyClientHandler());//处理器
                        }
                    });
            System.out.println("客户端准备ok ... ");

            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6666).sync();
            //监听关闭通道事件
            channelFuture.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully();
        }
    }
}
