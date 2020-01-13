package learn.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * 创建多线程
 * Runnable和callable
 * Callable方法带返回值，可以抛异常
 *
 * Thread的构造方法都是去掉Runnable
 * What  How
 * 接口适配
 *
 * 高并发加锁不难，难在控制
 * 使用Callable的好处在于，携带了返回值之后，可以进行计算结果的合并
 * fork join 分支合并
 *
 *
 * get()就是一个获取callable计算结果的方法，但是建议放在最后，因为还没有算完，就去取值的话会导致阻塞。
 * 如何判断一个callable计算完毕呢？使用 futureTask.isDone()方法
 *
 * 多个线程去抢一个futureTask 只会有一个得到计算结果
 *
 * @author xrb
 * @create 2019-12-12 16:30
 */


class MyThread1 implements Runnable{
    @Override
    public void run() {
        System.out.println("runnable ......");
    }
}

class MyThread2 implements Callable<Integer>{
    @Override
    public Integer call() throws Exception {
        try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
        System.out.println("callable ......");
        return 1024;
    }
}

public class CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask = new FutureTask<>(new MyThread2());

        Thread t1 = new Thread(new MyThread1(),"T1");
        t1.start();

        Thread t2 = new Thread(futureTask,"T2");
        t2.start();


        while (futureTask.isDone()){

        }
        System.out.println("futureTask  result is :"+futureTask.get());

    }

}
