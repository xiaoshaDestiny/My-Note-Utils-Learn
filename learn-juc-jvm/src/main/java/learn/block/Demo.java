package learn.block;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xrb
 * @create 2019-11-29 20:27
 */

class Foo{
    volatile int i = 0;// 普通的int
    AtomicInteger ai = new AtomicInteger(0);//原子包装过的int

    public void addPlusPlus(){
        this.i++;
    }
    public void addAiPlusPlus(){
        ai.getAndIncrement();
    }
}

public class Demo {
    public static void main(String[] args) {

        Foo foo = new Foo();
        for (int i = 1; i <= 1000; i++) {
             new Thread(()->{
                 for (int j = 0; j < 10; j++) {
                     foo.addPlusPlus();      //普通的整形变量    i  进行加 1 操作
                     foo.addAiPlusPlus();    //原子类的整形变量  ai 进行加 1 操作
                     System.out.println(Thread.currentThread().getName()+"\t a = "+foo.i);
                     System.out.println(Thread.currentThread().getName()+"\t ai = "+foo.ai);
                  }
              },"T"+i).start();
        }

        try {TimeUnit.SECONDS.sleep(2);} catch (InterruptedException e) { e.printStackTrace();}

        System.out.println(" i result is : " + foo.i);
        System.out.println(" ai result is : " + foo.ai);
    }
}
/*
     compareAndSet()方法，传入的两个参数分别是期望值，和更新值
     意为：如果当前主物理内存中的值和期望值相等，那么就将主物理内存中的值更新为第二个参数的值
/*
    AtomicInteger atomicInteger = new AtomicInteger(0);
    System.out.println(atomicInteger.compareAndSet(0,1)+"\t current data is "+atomicInteger.get());
    System.out.println(atomicInteger.compareAndSet(1,10)+"\t current data is "+atomicInteger.get());
    System.out.println(atomicInteger.compareAndSet(1,100)+"\t current data is "+atomicInteger.get());*/
