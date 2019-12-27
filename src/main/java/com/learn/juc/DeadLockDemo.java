package com.learn.juc;

import java.util.concurrent.TimeUnit;


/**
 * @author xrb
 * @create 2019-12-26 22:24
 *
 * 死锁指的是两个或者多个以上的进程在执行过程中，因为争夺资源而造成的一种相互等待的现象，
 * 若无外力干涉那它们将会无法推进下去，若系统资源充足，进程资源请求都能够得到满足，死锁出现的可能性比较低。
 *
 * 产生的原因：
 * 系统资源不足
 * 进程运行推进的顺序不恰当
 * 资源分配不恰当
 *
 * 线程操纵资源类
 *
 */

public class DeadLockDemo {
    public static void main(String[] args) {
        String lockA = "lockA";
        String lockB = "lockB";
        new Thread( new HoldLockThread(lockA,lockB),"Thread-A").start();
        new Thread( new HoldLockThread(lockB,lockA),"Thread-B").start();
    }
}
class HoldLockThread implements Runnable{
    private String lockA;
    private String lockB;
    public HoldLockThread(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "\t start ...");
        synchronized (lockA){
            System.out.println(Thread.currentThread().getName() + "\t自己持有："+lockA+"\t 尝试获得：" + lockB);
            try {TimeUnit.SECONDS.sleep(2);} catch (InterruptedException e) { e.printStackTrace();}

            synchronized (lockB){
                System.out.println(Thread.currentThread().getName()+ "\t 自己持有："+lockB +"\t 尝试获得：" + lockA);
            }
        }
        System.out.println(Thread.currentThread().getName() + "\t end ...");
    }
}
//linux  ps -ef|grip xxx
//windows下的java运行程序 也有类似ps的查看进程命令，但是目前我们只需要查看java
//jps = java ps