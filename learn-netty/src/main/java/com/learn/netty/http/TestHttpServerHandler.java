package com.learn.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * @author xu.rb
 * @since 2020-05-22 9:36
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {


    /**
     * 读取客户端数据
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        //判断 msg 是不是一个http request请求

        if(msg instanceof HttpRequest){
            System.out.println("msg 类型 ：" + msg.getClass() +" 客户端地址：" + ctx.channel().remoteAddress());
            HttpRequest request = (HttpRequest) msg;
            URI uri = new URI( request.uri());
            if("/favicon.ico".equals(uri.getPath())){
                System.out.println("favicon.ico 不做响应");
                return;
            }

            //回复信息给浏览器
            ByteBuf content = Unpooled.copiedBuffer("服务器读取到发送的数据", CharsetUtil.UTF_8);

            //构造Http响应
            DefaultHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK,content);

            httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
            httpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());

            //构建好的response返回
            ctx.writeAndFlush(httpResponse);
        }
    }
}
