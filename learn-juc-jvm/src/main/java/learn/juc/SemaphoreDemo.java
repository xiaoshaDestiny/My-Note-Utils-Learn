package learn.juc;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author xrb
 * @create 2019-11-26 23:14
 */
public class SemaphoreDemo {

    public static void main(String[] args) {
        Semaphore semaphone = new Semaphore(3);//模拟三个车位，底层是非公平锁
        for (int i = 1; i <=6 ; i++) {//模拟6个汽车,总有3个车抢不到
            new Thread(()->{
                try {
                    //占用
                    semaphone.acquire();
                    System.out.println(Thread.currentThread().getName()+"\t 抢到车位");
                    TimeUnit.SECONDS.sleep(3);//停车3秒钟
                    System.out.println(Thread.currentThread().getName()+"\t 停车3秒后离开车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    //释放
                    semaphone.release();
                }
            },String.valueOf(i)).start();
        }
    }
}
