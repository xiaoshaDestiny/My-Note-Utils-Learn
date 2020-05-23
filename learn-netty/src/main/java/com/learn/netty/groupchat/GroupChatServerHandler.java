package com.learn.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xu.rb
 * @since 2020-05-23 18:52
 */
public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {

    //定义一个Channel组 管理所有的Channel
    //GlobalEventExecutor.INSTANCE 全局的事件执行器  单例的
    private static ChannelGroup channelGroup= new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    /**
     * 该方法在连接建立之后 立即执行
     *
     * 将Channel加入到全局的Channel中
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        //将ChannelGroup中所有的Channel遍历并且发送 自己不需要遍历
        channelGroup.writeAndFlush("[客户端]"+channel.remoteAddress()+"加入聊天, 时间："+sdf.format(new Date())+"\n");

        //给其他发送完之后 再把自己加进去
        channelGroup.add(channel);
        System.out.println("当前ChannelGroup的大小：" + channelGroup.size());
    }

    /**
     * 断开连接的时候触发
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[客户端]"+channel.remoteAddress()+"离开\n");

        System.out.println("当前ChannelGroup的大小：" + channelGroup.size());
    }

    /**
     * 表示Channel处于一个活动的状态  提示XX上线
     * 在Channel连接成功之后触发调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " 上线了~ ");
    }

    /**
     * 在Channel断开连接之后触发调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "  离线~ ");
    }


    /**
     * 读取数据
     * @param channelHandlerContext
     * @param s
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        Channel channel = channelHandlerContext.channel();

        //遍历channelGroup
        channelGroup.forEach( ch ->{
            if(channel != ch){
                ch.writeAndFlush("[客户]" + channel.remoteAddress() + "发送消息：" + s +"\n");
            }else{
                ch.writeAndFlush("[自己]发送消息成功： " + s +"\n");

            }
        });
    }

    /**
     * 发生了异常时触发
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
