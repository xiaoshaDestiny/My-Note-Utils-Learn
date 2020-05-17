package com.learn.netty.nio.chat;

import lombok.val;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @author xu.rb
 * @since 2020-05-17 19:18
 */
public class GroupChatServer {
    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT = 6667;

    public GroupChatServer() {

        try {
            //得到选择器
            selector = Selector.open();
            listenChannel = ServerSocketChannel.open();
            //绑定选择器
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            //设置非阻塞
            listenChannel.configureBlocking(false);
            listenChannel.register(selector,SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen(){
        try {
            while (true){
                int count = selector.select(2000);
                if(count > 0){
                    //有事件要处理
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

                    while (iterator.hasNext()){
                        SelectionKey key = iterator.next();
                        //连接
                        if(key.isAcceptable()){
                            SocketChannel channel = listenChannel.accept();
                            channel.configureBlocking(false);
                            //将sc注册到selector上
                            channel.register(selector,SelectionKey.OP_READ);
                            System.out.println("地址是："+channel.getRemoteAddress() + "的人已经上线");
                        }
                        //读取
                        if(key.isReadable()){

                            readData(key);
                        }
                        //把当期那key删除
                        iterator.remove();
                    }
                }else {
                    //System.out.println("等待。。。。");

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //专门处理读取客户端的方法
    private void readData(SelectionKey key){
        //获得SocketChannel
        SocketChannel channel = (SocketChannel) key.channel();
        try {

            //创建Buffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = channel.read(buffer);
            if(count > 0){
                //缓冲区数据转换成字符串
                String msg = new String(buffer.array());
                //服务端打印
                System.out.println("客户端："+ msg);

                //向其他的客户端转发消息(去掉自己)
                sendInfoToOtherClients(msg,channel);
            }
        } catch (IOException e) {
            try {
                System.out.println(channel.getRemoteAddress() +" 已经离线");
                key.channel();
                channel.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }


    //转发消息给其它客户(通道)
    private void sendInfoToOtherClients(String msg, SocketChannel self ) throws  IOException{

        System.out.println("服务器转发消息中...");
        System.out.println("服务器转发数据给客户端线程: " + Thread.currentThread().getName());
        //遍历 所有注册到selector 上的 SocketChannel,并排除 self
        for(SelectionKey key: selector.keys()) {

            //通过 key  取出对应的 SocketChannel
            Channel targetChannel = key.channel();

            //排除自己
            if(targetChannel instanceof  SocketChannel && targetChannel != self) {

                //转型
                SocketChannel dest = (SocketChannel)targetChannel;
                //将msg 存储到buffer
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                //将buffer 的数据写入 通道
                dest.write(buffer);
            }
        }

    }

    public static void main(String[] args) {
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }
}
