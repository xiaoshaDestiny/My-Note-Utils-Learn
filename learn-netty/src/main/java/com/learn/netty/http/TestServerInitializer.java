package com.learn.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author xu.rb
 * @since 2020-05-22 9:38
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //向管道加入处理器
        ChannelPipeline pipeline = socketChannel.pipeline();

        //netty提供的处理http的编码解码器
        pipeline.addLast("myHttpServerCodec",new HttpServerCodec());

        //添加一个自定义的handler
        pipeline.addLast("myHttpServerHandler",new TestHttpServerHandler());
    }
}
