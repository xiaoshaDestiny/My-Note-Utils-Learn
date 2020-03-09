package learn.reference;

import java.lang.ref.WeakReference;

/**
 * @author xrb
 * @create 2020-03-09 21:41
 */
public class WeakReferenceDemo {
    //弱引用
    public static void main(String[] args) {
        Object o1 = new Object();
        WeakReference<Object> weakReference = new WeakReference<>(o1);

        System.out.println(o1);
        System.out.println(weakReference.get());

        o1 = null;
        System.gc();
        System.out.println("-------------");

        System.out.println(o1);
        System.out.println(weakReference.get());

    }
}
