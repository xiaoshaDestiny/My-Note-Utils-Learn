package com.learn.netty.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xu.rb
 * @since 2020-05-15 16:03
 */
public class BIOServer {
    public static void main(String[] args) throws IOException {
         //1、创建一个线程池
         //2、如果有客户端连接了，就创建一个线程与之通讯
        ExecutorService threadPool = Executors.newCachedThreadPool();
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务器启动成功");

        while (true){
            Socket socket = serverSocket.accept();
            System.out.println("连接到一个客户端");
            threadPool.execute(()->{
                handler(socket);
            });
        }
    }
    //和客户端通讯
    public  static void  handler(Socket socket){
        try {
            byte[] bytes = new byte[1024];
            InputStream inputStream = socket.getInputStream();
            while(true){
                System.out.println(Thread.currentThread().getName());
                int read = inputStream.read(bytes);
                if(read != -1) {
                    System.out.print(new String(bytes,0,read));
                }else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("关闭和client的连接");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
