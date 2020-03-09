package learn.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * @author xrb
 * @create 2020-03-09 22:03
 */
public class PhantomReferenceDemo {
    //虚引用  在对象被清除之前最后的遗言  确保生命周期的完整性
    public static void main(String[] args) throws InterruptedException {
        Object o1 = new Object();
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        PhantomReference<Object> phantomReference =
                new PhantomReference<>(o1,referenceQueue);

        System.out.println(o1);
        System.out.println(phantomReference.get());
        System.out.println(referenceQueue.poll());

        System.out.println("--------------------");

        o1 = null;
        System.gc();
        Thread.sleep(1000);


        System.out.println(o1);
        System.out.println(phantomReference.get());
        System.out.println(referenceQueue.poll());

    }
}
