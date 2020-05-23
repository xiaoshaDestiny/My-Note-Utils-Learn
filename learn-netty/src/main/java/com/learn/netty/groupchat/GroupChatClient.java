package com.learn.netty.groupchat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

/**
 * @author xu.rb
 * @since 2020-05-23 19:25
 */
public class GroupChatClient {
    private final String host;
    private final Integer port;

    public GroupChatClient(String host, Integer port) {
        this.host = host;
        this.port = port;
    }


    public void run() throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {

                            //获取pipeline
                            ChannelPipeline pipeline = socketChannel.pipeline();

                            //加入处理器
                            pipeline.addLast("decoder",new StringDecoder());
                            pipeline.addLast("encoder",new StringEncoder());
                            pipeline.addLast(new GroupChatClientHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            System.out.println("-------"+ channelFuture.channel().localAddress()+"--------");

            //输入
            Scanner scanner = new Scanner(System.in);
            while(scanner.hasNext()){
                String msg = scanner.nextLine();
                channelFuture.channel().writeAndFlush(msg+"\r\n");
            }

        }finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        GroupChatClient client = new GroupChatClient("127.0.0.1", 7000);
        client.run();
    }
}
