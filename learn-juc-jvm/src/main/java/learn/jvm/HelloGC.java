package learn.jvm;

/**
 * @author xrb
 * @create 2020-01-10 9:31
 */
public class HelloGC {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("hello GC");
        Thread.sleep(Integer.MAX_VALUE);
    }
}
