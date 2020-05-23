package com.learn.netty.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author xu.rb
 * @since 2020-05-23 20:19
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 心跳检测的 空闲触发
     * @param ctx
     * @param evt 事件
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {


        if(evt instanceof IdleStateEvent){
            IdleStateEvent event =  (IdleStateEvent) evt;
            String eventType = null;
            switch (event.state()){
                case READER_IDLE:eventType = "读空闲";break;
                case WRITER_IDLE:eventType = "写空闲";break;
                case ALL_IDLE:eventType = "读写空闲";break;
            }
            System.out.println("超时的客户端是： "+ctx.channel().remoteAddress()+"超时类型是："+eventType);
        }
    }

}

