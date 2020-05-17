package com.learn.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author xu.rb
 * @since 2020-05-17 18:40
 */
public class NIOClient {
    public static void main(String[] args) throws IOException {
        //得到一个网络通道
        SocketChannel socketChannel = SocketChannel.open();

        //设置非阻塞
        socketChannel.configureBlocking(false);

        //提供服务器的IP和端口
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);

        //连接到服务端
        boolean connect = socketChannel.connect(inetSocketAddress);
        if(!connect){
            while(!socketChannel.finishConnect()){
                System.out.println("你就干其他事情去，过一会才能连接上！");
            }
        }

        //到这里是已经连上了，发送数据就好
        String str = "hello hello hello ";
        ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());

        //将buffer写到channel
        socketChannel.write(buffer);
        System.in.read();
    }
}
