package com.learn.block;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xrb
 * @create 2019-12-09 15:58
 */
public class LockDemo {
    public static void main(String[] args) {
        FooTest fooTest = new FooTest();
        for (int i = 1; i < 10; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"开始运行");
                try { fooTest.get(); } catch (Exception e) { e.printStackTrace(); }
            },"T"+i).start();
        }
    }
}

class FooTest implements Runnable {
    Lock unFairLock = new ReentrantLock();
    Lock fairLock = new ReentrantLock(true);
    @Override
    public void run() {
        get();
    }
    public void get() {
        fairLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t 获取锁 do something ( maybe resolve dao )...");
        } catch (Exception e) { e.printStackTrace(); }
        finally {
            fairLock.unlock();
        }
    }
}