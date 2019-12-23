package com.learn.juc;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author xrb
 * @create 2019-12-13 11:16
 * Array Arrays[辅助工具类]
 *
 * Java中的线程池是通过Executor框架实现的，该框架中用到了Executor,Executors,ExecutorService,ThreadPoolExecutor这几个类。
 * Java8 总共有五种
 * 1、继承Thread类
 * 2、实现Runnable接口  不带返回值，不抛异常
 * 3、实现Callable接口  带返回值，会抛异常
 * 4、线程池
 *
 *Executors.newFixedThreadPool(5);//一池5线程
 *Executors.newSingleThreadExecutor();//一池1线程
 *Executors.newCachedThreadPool();//随机的扩容变动线程
 * 底层都是 new ThreadPoolExecutor
 *
 *
 *
 * 线程池的五大参数意义？
 *
 * 一、newFixedThreadPool(int nThread){}  执行长期任务，性能好很多
 * 特点：
 * 1、创建一个定长线程池，可以控制最大并发数量，超出的线程会在队列中等待。
 * 2、newFixedThreadPool创建线程池corePoolSize和minMumPoolSize是相等的，使用的是LinkedBlockingQueue
 *
 * 二、Executors.newSingleThreadExecutor(){} 一个任务一个任务执行的场景
 * 特点：
 * 1、创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序执行
 * 2、newSingleThreadExecutor将corePoolSize和MaxMumPoolSize都设置为1，它使用的是LinkedBlockingQueue
 *
 * 三、Executors.newCachedThreadPool(){} 执行很多短期异步的小程序或者负载较轻的服务器
 * 特点：
 * 1、创建一个可以缓存的线程池，如果线程池长度超过处理需要，可以里胡哦哦回收空线程，若无可收回，则创建新的线程
 * 2、newCacheThreadPool将corePoolSize设置为0，将maximumPoolSize设置为Integer.MAXVALUE，使用的是SynchronousQueue，也就是说来任务就创建线程运行，当线程空闲超过60秒，就销毁线程
 *
 * 其余两个创建线程池的方式
 * Executors.newScheduledThreadPool()
 * Executors.newWorkStealingPool(int) java8新特性
 *
 *
 *
 * 线程池的七大参数：
 * 1、corePoolSize：线程池中的常驻核心线程数
 * 在创建了线程池之后，当有请求任务来了之后，就会安排池中的线程去执行请求任务，近似理解为今日当值线程。
 * 当线程池中的线程数目达到corePoolSize后，就会把到达的任务放到缓存队列中。
 * 2、maximumPoolSize : 线程池所能容纳同时执行的最大线程数，必须大于等于1
 * 3、keepAliveTile：多余的空闲线程的存活时间，当线程池数量超过corePoolSize的时，当空闲时间达到该值得时候，多余空闲线程会被销
 * 默认情况下，只有当线程池中的线程数大于corePoolSize的时候，keepAliveTime才会起作用，知道线程池中的线程数不大于corePoolSize .
 * 毁直到只剩下corePoolSize个线程为止。
 * 4、unit ：keepAliveTime的时间单位
 *  	在规定的时间单位里面，没有新的任务了，线程没有执行，这些线程就会被销毁，直至数量降低为corePoolSize的数量。
 * 5、workQueue ： 任务队列，被提交但是还没有被执行的任务。
 * 6、threadFactory：表示生产线程池中工作线程的线程工厂，用于创建线程一般默认的即可。
 * 7、handler： 拒绝策略，表示当队列满了并且工作线程大于等于线程池的最大线程数时，如何来拒绝
 *
 *
 * 线程池底层核心原理：
 *
 *
 */
public class ThreadPoolDemo {
    public static void main(String[] args) {
        //查看本子的线程数量
        System.out.println(Runtime.getRuntime().availableProcessors());
        ExecutorService threadPool1 = Executors.newFixedThreadPool(5);//一池5线程
        ExecutorService threadPool2 = Executors.newSingleThreadExecutor();//一池1线程
        ExecutorService threadPool3 = Executors.newCachedThreadPool(); //可以扩容 随机的扩容变动线程


        //模拟10个用户来办理业务，每个用户就是一个来自外部的请求线程
        try{
            for (int i = 1; i <= 10 ; i++) {
                threadPool3.execute(()->{
                    System.out.println(Thread.currentThread().getName()+"\t 办理业务");
                });
                try {TimeUnit.MILLISECONDS.sleep(200);} catch (InterruptedException e) { e.printStackTrace();}

            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            threadPool3.shutdown();

        }
 }
}
