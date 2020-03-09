package learn.reference;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * @author xrb
 * @create 2020-03-09 21:01
 */
public class ReferenceQueueDemo {
    //引用队列
    public static void main(String[] args) throws InterruptedException {
        Object o1 = new Object();
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        WeakReference<Object> weakReference =
                new WeakReference<>(o1,referenceQueue);

        System.out.println(o1);
        System.out.println(weakReference.get());
        System.out.println(referenceQueue.poll());

        System.out.println("-------------");

        o1 = null;
        System.gc();
        Thread.sleep(1000);

        System.out.println(o1);
        System.out.println(weakReference.get());
        System.out.println(referenceQueue.poll());


    }
}
