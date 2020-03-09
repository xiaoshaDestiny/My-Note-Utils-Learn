package learn.reference;

/**
 * @author xrb
 * @create 2020-03-08 19:19
 */
public class StrongReferenceDemo {
    //强引用

    public static void main(String[] args) {
        Object obj1 = new Object(); //强引用
        Object obj2 =  obj1;
        obj1 = null;
        System.gc();
        System.out.println(obj2);

    }
}
