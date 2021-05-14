package com.learn.juc;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xu.rb
 * @description: TODO
 * @since 2021-04-01 22:37
 */
@Slf4j
public class SelfTest {

    @Test
    public void testCuncurrentHashMap() {
        ConcurrentHashMap<String,String> map = new ConcurrentHashMap<String,String>();
        String s = map.putIfAbsent("key", "value");
        String s1 = map.putIfAbsent("key", "value1");

        log.info(s);
    }

    @Test
    public void testCreateThread() {
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName());
        },"thread-name-test").start();
        System.out.println(Thread.currentThread().getName());

        //sout is:
            //main
            //thread-name-test
    }
}
