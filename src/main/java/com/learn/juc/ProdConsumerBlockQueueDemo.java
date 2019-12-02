package com.learn.juc;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xrb
 * @create 2019-12-02 21:50
 */


class MyResource{
    private volatile boolean FLAG = true;
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    //架构传递接口
    BlockingQueue<String> blockQueue = null;
    public MyResource(BlockingQueue<String> blockQueue) {
        this.blockQueue = blockQueue;
        System.out.println(blockQueue.getClass().getName());
    }

    //生产
    public void myProd() throws Exception{
        String data = null;
        boolean retValue = true;
        while (FLAG){
            data = atomicInteger.incrementAndGet()+"";
            blockQueue.offer(data,2L,TimeUnit.SECONDS);
            if(retValue){
                System.out.println(Thread.currentThread().getName()+"\t 插入队列"+data + "成功");
            }else {
                System.out.println(Thread.currentThread().getName()+"\t 插入队列"+data + "失败");
            }
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(Thread.currentThread().getName() +"叫停，FLAG = false 生产动作结束");
    }

    //消费
    public void myConsumer() throws Exception{
        String result = null;
        while (FLAG){
            result = blockQueue.poll(2L,TimeUnit.SECONDS);
            if(null == result || result.equalsIgnoreCase("")){
                FLAG = false;
                System.out.println(Thread.currentThread().getName()+"\t 超过来秒钟没有去到蛋糕，退出");
                return;
            }
            System.out.println(Thread.currentThread().getName()+"消费队列"+result+"成功");
        }
    }

    public void stop() throws Exception{
        this.FLAG = false;
    }
}
public class ProdConsumerBlockQueueDemo {
    public static void main(String[] args) throws Exception {
        MyResource myResource = new MyResource(new ArrayBlockingQueue<>(10));

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t 生产线程启动");
            try {
                myResource.myProd();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"Prod").start();

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t 消费线程启动");
            try {
                myResource.myConsumer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"Consumer").start();


        try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }

        myResource.stop();

    }
}































