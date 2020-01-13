package learn.block;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author xrb
 * @create 2019-12-09 15:58
 */
public class LockDemo {
    public static void main(String[] args) throws InterruptedException {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(10,()->{
            System.out.println("***10个线程都执行完毕***");
        });

        for (int i = 1; i <= 10; i++) {
            final int tmpInt = i;
            new Thread(()->{
                System.out.println(Thread.currentThread().getName() +"\t 做完事情");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) { e.printStackTrace(); } catch (BrokenBarrierException e) { e.printStackTrace(); }
            },"T"+i).start();
        }
    }
}


class FooTest implements Runnable {
    @Override
    public void run() {
        function1();
    }
    public synchronized void function1(){
        System.out.println(Thread.currentThread().getName() + "\t function 1 ...");
        function2();
    }
    public synchronized void function2(){
        System.out.println(Thread.currentThread().getName() + "\t function 2 ...");
    }
}

/**
 * Lock unFairLock = new ReentrantLock();
 public void get() {
 fairLock.lock();
 try {
 System.out.println(Thread.currentThread().getName() + "\t 获取锁 do something ( maybe resolve dao )...");
 } catch (Exception e) { e.printStackTrace(); }
 finally {
 fairLock.unlock();
 }
 }*/