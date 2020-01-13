package learn.juc;

import java.util.concurrent.CountDownLatch;

/**
 * @author xrb
 * @create 2019-11-26 19:32
 */
public class CountDownLatchDemo {
    private static final int threadNum = 6;

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(threadNum);

        for (int i = 1; i <= 6; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"\t 事情做完，关闭线程");
                countDownLatch.countDown();
            },CountryEnum.forEach_CountryEnum(i).getRetMessage()).start();
        }
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName()+"=======main线程应该是最后出来");

        System.out.println(CountryEnum.ONE);
        System.out.println(CountryEnum.ONE.getRetCode());
        System.out.println(CountryEnum.ONE.getRetMessage());

    }

    private void closeDoor() throws InterruptedException{
        CountDownLatch countDownLatch = new CountDownLatch(threadNum);

        for (int i = 1; i <= 6; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"\t 事情做完，关闭线程");
                countDownLatch.countDown();
            },String.valueOf(i)).start();
        }
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName()+"=======main线程应该是最后出来");
    }
}
