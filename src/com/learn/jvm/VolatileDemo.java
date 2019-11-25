package com.learn.jvm;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class MyDate{
	volatile int num = 0;

	public void addTo60(){this.num = 60; }
	public  void addPlusPlus(){this.num ++; }
	AtomicInteger atomicInteger = new AtomicInteger();

	public void addAtomic(){atomicInteger.getAndIncrement();}
}
public class VolatileDemo {
	public static void main(String[] args) {
		MyDate myDate = new MyDate();
		for(int i = 1 ; i<=20 ; i++){
			new Thread(()->{
				for(int j= 1;j<=1000;j++){
					myDate.addPlusPlus();
					myDate.addAtomic();
				}
			},String.valueOf(i)).start();
		}
		//等待上面的20个线程全部计算完毕之后，取得num的值，看是否是20000
		//默认是main线程和后台GC线程
		while(Thread.activeCount()>2){Thread.yield();}
		System.out.println(Thread.currentThread().getName()+"\t finally number value = " + myDate.num);
		System.out.println(Thread.currentThread().getName()+"\t finally number value = " + myDate.atomicInteger);
	}



	//volatile可以保证可见性，及时通知其他的线程，主物理内存的值以及修改为60.
	public static void seeOKByVolatile(){
		MyDate myDate = new MyDate();

		//第一个线程
		new Thread(()->{
			System.out.println(Thread.currentThread().getName()+"\t come in ");

			try {TimeUnit.SECONDS.sleep(3);} catch (InterruptedException e) {e.printStackTrace();}

			myDate.addTo60();

			System.out.println(Thread.currentThread().getName()+"\t undated num to " + myDate.num);
		},"AAA").start();

		//第二个线程
		while(myDate.num == 0){

		}

		//main线程能否执行这段话
		System.out.println(Thread.currentThread().getName()+"\t mission is over ");

	}
}

