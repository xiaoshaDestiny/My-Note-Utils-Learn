package learn.juc;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xrb
 * @create 2019-11-14 13:57
 *
 *
 * CAS是什么？
 *  compareAndSet : 比较并交换
 *
 *
 *
 *
 */
public class CASDemo {

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(4);

        System.out.println(atomicInteger.compareAndSet(4,20)+"\t current data is "+atomicInteger.get());
        System.out.println(atomicInteger.compareAndSet(4,1024)+"\t current data is "+atomicInteger.get());

        atomicInteger.getAndIncrement();

    }
}
