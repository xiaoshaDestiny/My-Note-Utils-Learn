package com.learn.juc;

import java.util.concurrent.*;

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
 * 1、在创建了线程池之后，等待提交过来的任务请求
 * 2、当调用execute()方法添加一个请求任务时，线程池会做以下判断：
 * 2.1、如果正在运行的线程数量小于corePoolSize，那么马上创建线程运行这个任务；
 * 2.2、如果正运行的线程数量大于或者等于corePoolSize，那么会将这个线程放入队列；
 * 2.3、如果这个时候队列满了并且正在运行的线程数量小于maximumPoolSize，那么还是		要创建非核心线程立刻运行这个任务
 * 2.4、如果队列满了并且正在运行的线程数量大于或者等于maximumPoolSize，那么线程		池会启动饱和和拒绝策略来执行。
 * 3、当一个线程完成任务的时候，它会从队列中取下一个任务来执行。
 * 4、当一个线程无事可做超过一定时间(KeepAliveTime)时，线程池会判断：
 * 如果当前运行的线程数量大于corePoolSize，那么这个线程就被停掉。
 * 所以线程池的所有任务完成后它最终会收缩到corePoolSize的大小。
 *
 *
 * JDK内置的拒绝策略：
 * 1、AbortPolicy(莫仍)：直接抛出RejectedException异常阻止系统正常运行
 * 2、CallerRunsPolicy:“调用者运行”一种调节机制，给策略既不会抛弃任务，也不会抛出异常，而是将某些任务回退带调用者，从而降低新任务的流量。
 * 3、DiscardOldestPolicy:抛弃队列中等待最久的任务，然后把当前任务加入队列中尝试再次提交当前任务。
 * 4、DiscardPolicy:直接丢弃任务，不予处理也不抛出异常。如果运行任务失败，这是最好的一种方案。
 * JDK内置的拒绝策略均是实现了RejectedExecutionHandler接口。
 *
 *
 * 工作中单一\固定数目\可变的 三种创建线程池的方式，一般使用哪个？
 * 一个都不用！
 *  强制线程池的创建不允许使用Executors去创建，而是通过ThreadPoolExecutior的方式
 *  Executors创建线程池的弊端：
 *  1、FixedThreadPool和SingleThreadPool允许的请求队列长度是Integer.MAX_VALUE 会堆积大量请求，导致OOM
 *  2、CacheThreadPool和ScheduledThreadPool允许创建的线程的数量时Integer.MAX_VALUE,创建大量线程，也会导致OOM
 *
 *
 * 线程资源必须通过线程池提供，不允许在应用中自行显示创建线程
 * 减少在创建和销毁上锁损耗的时间以及系统资源的开销，解决资源不足问题，
 * 如果不适用线程池，有可能造成系统创建大量同类线程而导致消耗完内存或者“过度切换"问题。
 *
 * 如何配置最佳的线程池数量？配置线程池改考虑些什么问题？
 * 根据业务类型去决定线程数目的最佳数值！
 * 首先获取运行环境的CPU核心数 Runtime.getRuntime().availableProcessors();
 * CPU密集型：改任务需要大量运算，没有阻塞，CPU一直在全速运行，
 *      CPU密集任务只有在真正的多核CPU上才可能通过多线程得到加速，单核的是不可能加速的
 *      CPU密集型的任务尽可能的少配置线程的数量，一般公式  CPU核心数+1 个线程的线程池
 * IO密集型：
 *      分为两种：1、IO密集型的任务线程并不是一直在执行任务，则应该配置尽可能多的线程数量  CPU核心数 * 2
 *               2、参考公式： CPU核数  /  1-阻塞系数 阻塞系数在0.8~0.9之间
 *               比如8核CPU 8/1-0.9 = 80
 *               为什么呢？
 *               任务需要大量的IO,会导致CPU运算能力的浪费，一直在等待，所以IO密集型任务中使用多线程可以大大加速程序运行，即使是在单核的CPU，也能加速。充分利用等待IO而浪费的时间。
 *
 *
 */
public class ThreadPoolDemo {

    /**
     * 仅仅是学习演示，实际中一定不能出现这样的代码
     * @param args
     */
    public static void main(String[] args) {
        ExecutorService threadPool =
                new ThreadPoolExecutor(2,
                        5,
                        1L,
                        TimeUnit.SECONDS,
                        new LinkedBlockingDeque<>(3),
                        Executors.defaultThreadFactory(),
                       //更换拒绝策略
                        // 1、抛出异常
                        //new ThreadPoolExecutor.AbortPolicy());
                        //2、回退给调用者
                        //new ThreadPoolExecutor.CallerRunsPolicy());
                        //3、抛弃等待时间最久的一个
                        //new ThreadPoolExecutor.DiscardOldestPolicy());
                        //4、丢弃任务
                        new ThreadPoolExecutor.DiscardPolicy());
        try{
            for (int i = 1; i <= 10 ; i++) {
                //超过5+3的时候，会采取拒绝策略
                threadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName()+"\t 办理业务");
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            threadPool.shutdown();
        }
    }

    public static void initThreadPool(){

        //查看本子的线程数量
        System.out.println(Runtime.getRuntime().availableProcessors());
        //一池5线程
        ExecutorService threadPool1 = Executors.newFixedThreadPool(5);
        //一池1线程
        ExecutorService threadPool2 = Executors.newSingleThreadExecutor();
        //可以扩容 随机的扩容变动线程
        ExecutorService threadPool3 = Executors.newCachedThreadPool();
        //模拟10个用户来办理业务，每个用户就是一个来自外部的请求线程
        try{
            for (int i = 1; i <= 7 ; i++) {
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

