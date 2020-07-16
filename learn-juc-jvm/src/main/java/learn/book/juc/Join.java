package learn.book.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author xu.rb
 * @since 2020-07-14 23:02
 */
public class Join {
    public static void main(String[] args) throws Exception {
        Thread previous = Thread.currentThread();
        int start = 1;
        int end = 10;

        for (int i = 0; i < 2; i++) {
            FutureTask<Integer> task = new FutureTask<>(new Domino(previous, start, end));
            Thread t1 = new Thread(task, String.valueOf(i));
            t1.start();
            task.run();
            System.out.println(task.get());
            previous = t1;
            start = start + 10;
            end = end + 10;
        }
        TimeUnit.SECONDS.sleep(1);
        System.out.println(Thread.currentThread().getName() + " terminate.");
    }


    static class Domino implements Callable<Integer> {
        private Thread thread;
        private Integer start;
        private Integer end;

        public Domino(Thread thread, Integer start, Integer end) {
            this.thread = thread;
            this.start = start;
            this.end = end;
        }

        @Override
        public Integer call() throws Exception {
          /*  try {
                thread.join();
            } catch (InterruptedException e) {
                return 0;
            }*/

            Integer result = 0;
            for (int i = start; i <= end; i++) {
                result = result + i;
            }
            return result;
        }
    }
}


/*public class Join {
    public static void main(String[] args) throws Exception {
        Thread previous = Thread.currentThread();
        for (int i = 0; i < 10; i++) {
            // 每个线程拥有前一个线程的引用，需要等待前一个线程终止，才能从等待中返回
            Thread thread = new Thread(new Domino(previous), String.valueOf(i));
            thread.start();
            previous = thread;
        }
        TimeUnit.SECONDS.sleep(5);
        System.out.println(Thread.currentThread().getName() + " terminate.");
    }

    static class Domino implements Runnable {
        private Thread thread;
        public Domino(Thread thread) {
            this.thread = thread;
        }

        public void run() {
            try {
                thread.join();
            } catch (InterruptedException e) {
            }
            System.out.println(Thread.currentThread().getName() + " terminate.");
        }
    }*/



