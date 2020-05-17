package com.learn.netty.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author xu.rb
 * @since 2020-05-17 16:14
 */
public class NIOFileChannel {

    public static void main(String[] args) throws IOException {
        //writeToFile("hello,xiaosha！");
        readFile("d:\\file01.txt");
    }

    public static void readFile(String fileLocate) throws IOException {
        File file = new File(fileLocate);
        FileInputStream fileInputStream = new FileInputStream(file);

        FileChannel fileChannel = fileInputStream.getChannel();
        //创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());
        fileChannel.read(byteBuffer);
        //字节数组转为string
        String s = new String(byteBuffer.array());
        System.out.println(s);
        fileInputStream.close();
    }

    public static void writeToFile(String str) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("d:\\file01.txt");
        FileChannel fileChannel = fileOutputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(str.getBytes());
        byteBuffer.flip();
        fileChannel.write(byteBuffer);
        fileOutputStream.close();
    }
}
