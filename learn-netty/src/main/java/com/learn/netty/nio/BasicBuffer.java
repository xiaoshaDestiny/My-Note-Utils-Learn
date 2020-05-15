package com.learn.netty.nio;

import java.nio.IntBuffer;

/**
 * @author xu.rb
 * @since 2020-05-15 16:53
 */
public class BasicBuffer {
    public static void main(String[] args) {
        //buffer的使用
        IntBuffer intBuffer = IntBuffer.allocate(5);
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i*2);
        }
        //读写切换
        intBuffer.flip();

        while (intBuffer.hasRemaining()){
            System.out.print(intBuffer.get()+ " ");
        }
    }
}
