package com.learn.juc;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author xrb
 * @create 2019-11-27 15:14
 */
public class BlockingQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        //List list = new ArrayList<>();
        //阻塞队列的大小需要在初始的时候确定
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        System.out.println(blockingQueue.offer("a",1L,TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("a",1L,TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("a",1L,TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("a",4L,TimeUnit.SECONDS));



    /*    blockingQueue.put("a");
        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));
        System.out.println(blockingQueue.offer("X"));

        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());*/
    }
}
