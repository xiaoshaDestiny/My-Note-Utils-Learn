package learn.block;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author xrb
 * @create 2019-11-30 21:37
 */
public class ABA {

    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(1,1);

    public static void main(String[] args) {
        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "\t 获取初始版本号: " + stamp);

            try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) { e.printStackTrace();}

            //期望值，跟新值，期望版本号，跟新版本号
            atomicStampedReference.compareAndSet(1,2,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName() + "\t 第1次修改的版本号: " + atomicStampedReference.getStamp());

            atomicStampedReference.compareAndSet(2,1,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName() + "\t 第2次修改的版本号: " + atomicStampedReference.getStamp());

        },"Thread-A").start();


        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "\t 获取初始版本号 " + stamp);

            //保证T3完成一次ABA操作
            try {TimeUnit.SECONDS.sleep(3);} catch (InterruptedException e) { e.printStackTrace();}

            System.out.println(Thread.currentThread().getName()+"修改之前的最新版本号：" + atomicStampedReference.getStamp());
            boolean bool = atomicStampedReference.compareAndSet(1, 100, stamp, atomicStampedReference.getStamp() + 1);

            System.out.println(Thread.currentThread().getName()+"\t 修改是否成功" +bool);
            System.out.println("变量的最新值是："+ atomicStampedReference.getReference());
        },"Thread-B").start();
    }
}


//