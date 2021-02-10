package com.learn.juc.part3;

import org.testng.annotations.Test;

/**
 * @author xu.rb
 * @since 2021-02-08 21:04
 *
 * 原子性、可见性、指令有序性
 * volatile不能保证原子性
 *
 * 第三章：对象的共享
 *  内存可见性：当一个线程修改对象的状态之后其他的线程能够感知到状态的变化
 *  最低安全性：当线程在没有同步的情况下读取变量，可能会得到一个失效的值，但是这个值是由之前某个线程设置的值，而不是一个随机值。
 *             非volatile的64位类型变量double和long类型不能保证最低安全性。JVM会允许将64位变量拆解为两个32位变量进行读写操作。
 *  Volatile变量：将变量的更新操作通知给其他线程。确保自身状态的可见性，确保他们锁引用对象的状态的可见性，以及标识一些重要的生命周期事件的发生。
 * 何时使用Volatile变量：
 *      1、变量的写入操作不依赖变量的当前值，或者确保只会有一个线程更新变量的值
 *      2、该变量不会与其他变量一起纳入不变性条件中
 *      3、在访问变量时不需要加锁
 *
 *  线程封闭：
 *  1、不共享数据，仅仅在单线程内访问数据，就不需要同步。JDBC的Connection对象，大多数情况都是单个线程来同步的
 *  2、Ad-hoc:维护线程封闭性的职责交给程序来实现。线程封闭脆弱，没有一种语言特性能将对象封闭到目标线程上
 *  3、栈封闭：
 *
 *
 *
 *
 */
public class NoVisibilityTest {

    @Test
    public void testNoVisibility(){
        for (int i = 0; i < 2; i++) {
            NoVisibility noVisibility = new NoVisibility();
            new Thread(()->{
                while (noVisibility.isReady()) {
                    Thread.yield();
                    System.out.println(noVisibility.getNumber());
                }
            }).start();
            noVisibility.setNumber(10);
            noVisibility.setReady(true);
        }
    }

}