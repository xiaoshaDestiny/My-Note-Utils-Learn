package com.learn.juc;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author xrb
 * @create 2019-11-25 22:14
 * 多个线程同时操作一个资源类，所以为了满足并发量，读取共享资源应该可以同时进行。
 * 但是，如果有一个线程想去写共享资源，就不应该在有其他线程可以对改资源进行读或者写
 *
 * 读-读：可以共存
 * 读-写：不能共存
 * 写-写：不能共存
 *
 * 写：原子性+独占，写操作独占不可中断，中间不可被打断
 *
 *
 */

class MyCache{//资源类

    private volatile Map<String,Object> map = new HashMap<>();
    private ReentrantReadWriteLock rwlLock = new  ReentrantReadWriteLock();

    public void put(String key,Object value){
        rwlLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() +"\t 正在写入："+key);
            try { TimeUnit.MILLISECONDS.sleep(300); } catch (InterruptedException e) { e.printStackTrace(); }
            map.put(key,value);
            System.out.println(Thread.currentThread().getName() +"\t 写入完成："+key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rwlLock.writeLock().unlock();
        }
    }

    public void get(String key){
        rwlLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() +"\t 正在读取："+key);
            try { TimeUnit.MILLISECONDS.sleep(300); } catch (InterruptedException e) { e.printStackTrace(); }
            Object result = map.get(key);
            System.out.println(Thread.currentThread().getName() +"\t 读取完成："+result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rwlLock.readLock().unlock();
        }
    }
}
public class ReadWriteLockDemo {
    public static void main(String[] args) {
        MyCache myCache = new MyCache();
        for (int i = 1; i < 5; i++) {
            final  int tempInt = i;
            new Thread(()->{
                myCache.put(tempInt+"",tempInt+"");
            },String.valueOf(i)).start();
        }

        for (int i = 1; i < 5; i++) {
            final  int tempInt = i;
            new Thread(()->{
                myCache.get(tempInt+"");
            },String.valueOf(i)).start();
        }

    }
}
