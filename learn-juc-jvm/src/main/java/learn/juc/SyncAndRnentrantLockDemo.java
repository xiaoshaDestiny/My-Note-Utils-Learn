package learn.juc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xrb
 * @create 2019-12-01 23:03
 *
 * 1、原始构成
 * synchroized是关键字，属于JVM层面
 * 通过monitorenter（底层是通过mointor对象来完成），其实waite/notify等方法也是依赖于monitor对象，只有在同步代码块或者是方法中才能调用waite/notify等方法。
 * monitorexit
 * Lock是具体的实现类，是API层面的锁
 *
 * 2、使用方法
 * synchronized不需要用户去手动释放锁，当synchronized代码执行完之后系统会自动让现场释放对锁的占用
 * ReentrantLock则需要用户去手动释放锁，若没有主动的去释放锁，则程序可能导致出现死锁现象。需要lock() unlock()方法配合try-finally语句块来实现。
 *
 * 3、等待是否可以被中断
 * synchronized不可以被中断，除非抛出异常或者方法正常的运行完成。
 * ReentrantLock是可以中断的，设置超时方法 tryLock(Long timeout,TimeUnit unit),lockInterruptibly()放代码块中，调用interrupt()方法可中断
 *
 * 4、加锁是否公平
 * synchronized是非公平锁
 * ReentrantLock两者都可以，默认是非公平锁，构造方法可以传入boolean值，true为公平锁。
 *
 * 5、锁绑定多个条件Condition
 * synchronized没有，ReentrantLock用来实现分组唤醒需要唤醒线程们，可以精确唤醒，而不是像synchronized要么随机唤醒一个线程，要么唤醒全部线程。
 */


class ShareResource{
    private volatile int num = 0;  //A:1  B:2   C:3
    private AtomicInteger  numAtomic = new AtomicInteger(0);
    private ReentrantLock lock = new ReentrantLock();
    private List<Condition> conList = new ArrayList<>();
    Condition c1 = lock.newCondition();
    Condition c2 = lock.newCondition();
    Condition c3 = lock.newCondition();

    public ShareResource() {
        conList.add(c1);
        conList.add(c2);
        conList.add(c3);
    }

    public void print51(){
        lock.lock();
        try {
            //判断
            while (num != 0){
                c1.await();
            }

            //干活
            for (int i = 1; i <= 5 ; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            }
            //通知
            num = 1;
            c2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }
    public void print52(){
        lock.lock();
        try {
            //判断
            while (num != 1){
                c2.await();
            }

            //干活
            for (int i = 1; i <= 5 ; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            }
            //通知
            num = 2;
            c3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }
    public void print53(){
        lock.lock();
        try {
            //判断
            while (num != 2){
                c3.await();
            }

            //干活
            for (int i = 1; i <= 5 ; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            }
            //通知
            num = 0;
            c1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void print5(){
        lock.lock();
        try {
            //判断
            int index = numAtomic.get();

            while (numAtomic.get() != numAtomic.get()){ //0的时候
                conList.get(numAtomic.get()).await();
            }

            //干活
            for (int i = 1; i <= 5 ; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            }
            //通知
            numAtomic.getAndIncrement();
            if(numAtomic.get() == 3){
                numAtomic.compareAndSet(3,0);
            }
            conList.get(numAtomic.get()).signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }
}

public class SyncAndRnentrantLockDemo {
    /**
     * 多线程之间按照顺序调用 A-> B -> C
     */
    public static void main(String[] args) {
        ShareResource shareResource = new ShareResource();


/*        new Thread(()->{
            for (int i = 1; i < 3; i++) {
                shareResource.print51();
            }
        },"A").start();

        new Thread(()->{
            for (int i = 1; i < 3; i++) {
                shareResource.print52();
            }
        },"B").start();

        new Thread(()->{
            for (int i = 1; i < 3; i++) {
                shareResource.print53();
            }
        },"C").start();*/


        new Thread(()->{
            for (int i = 1; i < 3; i++) {
                shareResource.print5();
            }
        },"A").start();

        new Thread(()->{
            for (int i = 1; i < 3; i++) {
                shareResource.print5();
            }
        },"B").start();

        new Thread(()->{
            for (int i = 1; i < 3; i++) {
                shareResource.print5();
            }
        },"C").start();




    }
}
