package com.learn.netty.simple;

import io.netty.buffer.ByteBuf;
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

        //服务阻塞时间很长的逻辑

        //解决方案1 ： 用户自定义普通任务放到TaskQueue中   异步执行  先返回响应信息

        /*
        ctx.writeAndFlush(Unpooled.copiedBuffer("正常不耗时业务完成",CharsetUtil.UTF_8));
        ctx.channel().eventLoop().execute(()->{
            //需要执行10秒钟
            try {
                ctx.writeAndFlush(Unpooled.copiedBuffer("服务端处理耗时业务中，耗时10秒钟",CharsetUtil.UTF_8));
                Thread.sleep(10000);
                ctx.writeAndFlush(Unpooled.copiedBuffer("服务端处理耗时业务完成",CharsetUtil.UTF_8));

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        */


        //解决方案2 ： 用户自定义定时任务 放到ScheduleTaskQueue   异步执行  先返回响应信息

        ctx.writeAndFlush(Unpooled.copiedBuffer("正常不耗时业务完成",CharsetUtil.UTF_8));
        ctx.channel().eventLoop().schedule(()->{
            try {
                ctx.writeAndFlush(Unpooled.copiedBuffer("服务端处理耗时业务中，耗时10秒钟",CharsetUtil.UTF_8));
                Thread.sleep(10000);
                ctx.writeAndFlush(Unpooled.copiedBuffer("服务端处理耗时业务完成",CharsetUtil.UTF_8));

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //延迟5秒钟之后去处理
        },5,TimeUnit.SECONDS);



        //正常的逻辑
        /*
        System.out.println("server 收到的 ctx is :" + ctx);
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送的消息是: " + buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端的地址是：" + ctx.channel().remoteAddress());
        */
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
