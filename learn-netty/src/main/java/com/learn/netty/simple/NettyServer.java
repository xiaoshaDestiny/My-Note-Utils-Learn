package com.learn.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author xu.rb
 * @since 2020-05-21 11:11
 */
public class NettyServer {
    public static void main(String[] args) throws InterruptedException {

        //1、创建两个线程组，bossGroup 和 workerGroup
        // bossGroup只负责连接请求的建立  真正和客户点的业务处理会交给workerGroup完成
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //创建服务端的启动配置对象
            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(bossGroup,workerGroup)//两个线程组
                    .channel(NioServerSocketChannel.class)//通道实现
                    .option(ChannelOption.SO_BACKLOG,128)//设置线程队列连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE,true)//保持活动连接
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        //给pipeline设置管道处理器
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NettyServerHandler());
                        }
                    });
            System.out.println("服务器准备完成。。。");

            //绑定了一个端口并且同步   启动服务器
            ChannelFuture cf = bootstrap.bind(6666).sync();

            //对关闭通道进行监听
            cf.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }



    }
}
