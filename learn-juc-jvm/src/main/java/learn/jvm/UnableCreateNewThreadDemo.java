package learn.jvm;

/**
 * @author xrb
 * @create 2020-03-10 14:36
 * 不能在创建本地线程了。
 *
 * 高并发请求服务器的时候，会出现以下异常：
 * 准确的讲该native thread异常与对应的平台有关。
 *
 * 导致原因：
 * 1、你的应用创建了太多线程，一个应用进程创建多个线程，超过系统承载极限。
 * 2、你的服务器部允许你的应用程序创建这么多线程，Linux系统默认允许单个进程可以创建的线程数是1024个，你的应用创建超过这个数量，就会报异常。
 *
 * 解决办法：
 * 1、想办法降低应用程序创建线程的数量，分析应用是否真的有必要创建这么多线程，如果不是，改代码将线程数降到最低。
 * 2、对于有的应用来说，确实需要创建很多线程，远远超过默认的1024个，可以修改Linux服务器配置，扩大Linux默认限制。
 */
public class UnableCreateNewThreadDemo {
    public static void main(String[] args) {

        for (int i = 0;; i++) {
            System.out.println(i);

            new Thread(()->{
                try { Thread.sleep(Integer.MAX_VALUE); } catch (InterruptedException e) { e.printStackTrace(); }
            },""+i).start();
        }
    }
}
