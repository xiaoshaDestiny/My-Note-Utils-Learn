package com.learn.netty.nio;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author xu.rb
 * @since 2020-05-17 16:14
 */
public class NIOFileChannel01 {

    private static ByteBuffer byteBuffer;

    public static void main(String[] args) throws IOException {
        String str = "hello,xiaosha！";

        FileOutputStream fileOutputStream = new FileOutputStream("d:\\file01.txt");
        FileChannel fileChannel = fileOutputStream.getChannel();

        byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(str.getBytes());
        byteBuffer.flip();
        fileChannel.write(byteBuffer);
        fileOutputStream.close();

    }
}
