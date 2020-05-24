package com.learn.netty.codec;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;


/**
 * @author xu.rb
 * @since 2020-05-21 14:46
 *
 * 自定义的Handler需要继承netty规定的一些Handler规范
 */

public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 读取实际数据
     *
     * @param ctx  上下文对象 含有管道pipeline  通道  连接地址
     * @param msg  客户端发送的数据  默认是Object类型的数据
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {


        StudentPOJO.Student student = (StudentPOJO.Student) msg;
        System.out.println("客户端发送的id:"+ student.getId());
        System.out.println("客户端发送的name:"+ student.getName());

    }

    /**
     * 读取数据完毕
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

        //将数据写到缓存 并且刷新 要对这个数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端",CharsetUtil.UTF_8));
    }

    /**
     * 发生异常  一般关闭通道即可
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
