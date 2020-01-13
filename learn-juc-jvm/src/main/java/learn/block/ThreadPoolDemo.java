package learn.block;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xrb
 * @create 2019-12-26 19:01
 */

public class ThreadPoolDemo implements Callable<String> {

    @Override
    public String call() throws Exception{
        System.out.println(Thread.currentThread().getName() + " 线程运行开始!");
        System.out.println(Thread.currentThread().getName() + " 运行业务");
        System.out.println(Thread.currentThread().getName() + " 线程运行结束!");
        return "over";
    }

    public static void main(String[] args) {
        //一池5线程
        ExecutorService threadPool1 = Executors.newFixedThreadPool(5);

        //模拟11个用户来办理业务，每个用户就是一个来自外部的请求线程
        try{
            for (int i = 1; i <= 11 ; i++) {
                threadPool1.execute(()->{
                    System.out.println(Thread.currentThread().getName()+"\t 办理业务");
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            threadPool1.shutdown();
        }
    }

}
