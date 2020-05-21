package com.learn.netty.text;

import io.netty.util.NettyRuntime;

/**
 * @author xu.rb
 * @since 2020-05-21 15:35
 */
public class NettyTest {

    @org.junit.Test
    public void test01(){
        System.out.println(NettyRuntime.availableProcessors() * 2);
    }
}
