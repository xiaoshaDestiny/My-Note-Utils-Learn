package com.learn.jvm;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author xrb
 * @create 2019-11-17 19:06
 *
 * 解决ABA问题，类似于乐观锁，计算出来的数值的版本 小于或者 等于当前主物理内存的值得版本，都将其作废，重新读取最新的值回去计算
 *  AtomicStampedReference  类
 *
 */
public class ABADemo {


    static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);
    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100,1);

    public static void main(String[] args) {

        System.out.println("===============================ABA问题的产生===============================");

        new Thread(() -> {
            atomicReference.compareAndSet(100,101);
            atomicReference.compareAndSet(101,100);
        },"Thread 1").start();

        new Thread(() -> {
            //等待T1 保证它完成一次ABA操作
            try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) { e.printStackTrace();}

            System.out.println(atomicReference.compareAndSet(100,209) + "\t" + atomicReference.get());

        },"Thread 2").start();

        try {TimeUnit.SECONDS.sleep(2);} catch (InterruptedException e) { e.printStackTrace();}



        System.out.println("===============================ABA问题的解决===============================");

        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "\t 第1次版本号: " + stamp);

            //等T4也拿到最新的值
            try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) { e.printStackTrace();}

            //期望值，跟新值，期望版本号，跟新版本号
            atomicStampedReference.compareAndSet(100,101,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName() + "\t 第2次版本号: " + atomicStampedReference.getStamp());

            atomicStampedReference.compareAndSet(101,100,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName() + "\t 第3次版本号: " + atomicStampedReference.getStamp());

            },"Thread 3").start();
        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "\t 第1次版本号: " + stamp);

            //保证T3完成一次ABA操作
            try {TimeUnit.SECONDS.sleep(3);} catch (InterruptedException e) { e.printStackTrace();}
            System.out.println("最新版本号：" + atomicStampedReference.getStamp());
            boolean bool = atomicStampedReference.compareAndSet(100, 209, stamp, atomicStampedReference.getStamp() + 1);

            System.out.println(Thread.currentThread().getName()+"\t 修改是否成功" +bool);
            System.out.println("最新值"+ atomicStampedReference.getReference());

        },"Thread 4").start();
    }
}
