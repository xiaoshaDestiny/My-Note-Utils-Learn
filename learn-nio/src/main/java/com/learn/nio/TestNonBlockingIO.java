package com.learn.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author xrb
 * @create 2020-04-07 22:53
 */
public class TestNonBlockingIO {

    //客户端
    public static void main(String[] args)  throws IOException {
        //1、获取通道
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));

        //2、切换成非阻塞模式
        socketChannel.configureBlocking(false);

        //3、发送数据
        //分配指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);

        //4、发送数据给服务器
        Scanner scan = new Scanner(System.in);
        while(scan.hasNext()){
            String str = scan.next();
            buf.put((new Date().toString() + "\n" + str).getBytes());
            buf.flip();
            socketChannel.write(buf);
            buf.clear();
        }

        //5、关闭通道
        socketChannel.close();
    }

    //客户端
    public void testClient() throws IOException {

    }

    //服务
    @Test
    public void testServer() throws IOException {
        //1、获取连接
        ServerSocketChannel ssChannel = ServerSocketChannel.open();

        //2、切换成非阻塞模式
        ssChannel.configureBlocking(false);

        //3、绑定连接
        ssChannel.bind(new InetSocketAddress(9898));

        //4、获取选择器
        Selector selector = Selector.open();

        //5、将通道注册到选择器上 并且指定监听事件
        ssChannel.register(selector,SelectionKey.OP_ACCEPT);

        //6、轮询式获取选择器上已经准备就绪的事件
        while(selector.select() > 0){
            //7、获取当前选择器中所有注册的选择键   已经就绪的监听事件
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();

            while(it.hasNext()){
                //8、获取准备就绪的事件
                SelectionKey sk = it.next();

                //9、判断具体是什么事件接收就绪
                if(sk.isAcceptable()){
                    //10、若 接收 就绪 就获取客户端连接
                    SocketChannel socketChannel = ssChannel.accept();
                    //11、切换非阻塞模式
                    socketChannel.configureBlocking(false);
                    //12、将该通道注册到选择器上
                    socketChannel.register(selector,SelectionKey.OP_READ);
                }else if(sk.isReadable()){
                    //13、读就绪
                    SocketChannel socketChannel =(SocketChannel) sk.channel();
                    //14、读取数据
                    ByteBuffer buf = ByteBuffer.allocate(1024);
                    int len = 0;
                    while((len = socketChannel.read(buf)) > 0){
                        buf.flip();
                        System.out.println(new String(buf.array(), 0, len));
                        buf.clear();
                    }
                }
                //15、取消选择键
                it.remove();
            }
        }

        //4、获取客户端连接
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
