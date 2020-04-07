package com.learn.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author xrb
 * @create 2020-04-07 22:19
 *
 * 一、使用NIO完成网咯通讯的核心
 * 1、通道Channel     负责连接
 *      |--SelectableChannel
 *          |--SocketChannel
 *          |--ServerSocketChannel
 *          |--DatagramChannel
 *
 * 2、缓冲区Buffer    负责数据的存取
 * 3、选择器Selector  是SelectableChannel的多路复用器。用于监控SelectableChannel的IO状况。
 *
 *
 *
 */
public class TestBlockingIO {



    //客户端
    @Test
    public void testClient() throws IOException {
        //1、获取通道
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));

        //2、发送数据
        //分配指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);

        //3、读取本地文件，并且发送到服务器
        FileChannel inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);

        while(inChannel.read(buf) != -1){
            buf.flip();
            socketChannel.write(buf);
            buf.clear();
        }

        //4、关闭通道
        inChannel.close();
        socketChannel.close();
    }

    //服务端
    @Test
    public void testServer() throws IOException {
        //1、获取连接
        ServerSocketChannel ssChannel = ServerSocketChannel.open();

        //2、绑定连接
        ssChannel.bind(new InetSocketAddress(9898));

        //3、获取客户端连接
        SocketChannel sChannel = ssChannel.accept();

        //4、分配一个指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);

        //5、接收客户端的数据，保存到本地
        FileChannel outChannel = FileChannel.open(Paths.get("socket.jpg"), StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW);

        while(sChannel.read(buf) != -1){
            buf.flip();
            outChannel.write(buf);
            buf.clear();
        }

        //6、关闭通道
        sChannel.close();
        outChannel.close();
        ssChannel.close();
    }
}
