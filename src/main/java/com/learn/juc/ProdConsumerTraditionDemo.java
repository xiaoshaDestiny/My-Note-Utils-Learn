package com.learn.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xrb
 * @create 2019-11-27 17:25
 * 传统的生产者消费者模式
 *
 * 初始值为0的变量，两个线程交替操作，一个加1，一个减1，来5轮
 *
 * 1、线程       操作(方法)          资源类
 * 2、判断       干活               通知
 * 3、防止虚假唤醒机制
 * 深 锁 明 细
 *
 */
class ShareData{
    private int number = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition= lock.newCondition();

    public void increment() throws Exception{
        lock.lock();
        try {
            //1、判断
            while(number != 0){
                //等待生产
                condition.await();
            }
            //2、干活
            number++;
            System.out.println(Thread.currentThread().getName()+"\t"+number);
            //3、通知唤醒
            condition.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        } finally{
            lock.unlock();
        }
    }

    public void decrement() throws Exception{
        lock.lock();
        try {
            //1、判断
            while(number == 0){
                //等待生产
                condition.await();
            }
            //2、干活
            number--;
            System.out.println(Thread.currentThread().getName()+"\t"+number);
            //3、通知唤醒
            condition.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        } finally{
            lock.unlock();
        }
    }
}

public class ProdConsumerTraditionDemo {
    public static void main(String[] args) {

        ShareData shareData = new ShareData();

        new Thread(()->{
            for (int i = 1; i <= 5; i++) {
                try {
                    shareData.increment();
                } catch (Exception e) { e.printStackTrace(); }
            }
        },"T1").start();

        new Thread(()->{
            for (int i = 1; i <= 5; i++) {
                try {
                    shareData.decrement();
                } catch (Exception e) { e.printStackTrace(); }
            }
        },"T2").start();

    }

}
