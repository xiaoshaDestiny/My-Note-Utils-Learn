package com.learn.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xrb
 * @create 2019-11-24 22:12
 */
public class ReentrantDemo {
    public static void main(String[] args) {
        Phone phone = new Phone();
        for (int i = 1; i <= 3; i++) {
            new Thread(()->{
                try {
                    phone.sendSMS();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            },"T"+i).start();
        }

        try {TimeUnit.SECONDS.sleep(2);} catch (InterruptedException e) { e.printStackTrace(); }
        System.out.println("========================================================");

        Thread T3 = new Thread(phone);
        Thread T4 = new Thread(phone);
        T3.start();
        T4.start();
    }
}
class Phone implements  Runnable{
    Lock lock = new ReentrantLock();//默认是false
    @Override
    public void run() {
        get();
    }
    public void get(){
        lock.lock();
        lock.lock();
        try{
            System.out.println(Thread.currentThread().getName() + "\t get()");
            set();
        }catch (Exception e){ e.printStackTrace(); }finally {
            lock.unlock();
            lock.unlock();
        }
    }

    public void set(){
        lock.lock();
        try{
            System.out.println(Thread.currentThread().getName() + "\t set()");
        }catch (Exception e){ e.printStackTrace(); }finally {
            lock.unlock();
        }
    }



    public void sendSMS() throws Exception{
        System.out.println(Thread.currentThread().getName() + "\t sendSMS()");
        sendEmail();
    }
    public void sendEmail() throws Exception{
        System.out.println(Thread.currentThread().getName() + "\t ...sendEmail()");
    }
}
