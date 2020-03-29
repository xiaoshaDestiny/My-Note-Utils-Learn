package com.learn.design.singleton1.type6;

/**
 * @author xrb
 * @create 2020-03-29 18:49
 * 单例模式的第六种写法： 懒汉式(使用到再创建 同步代码块带双重检查)
 *
 * Double-Check 概念是多线程开发中常使用到的，如代码中所示，我们进行了两次 if (singleton == null)检查，这样就可以保证线程安全了。
 * 这样，实例化代码只用执行一次，后面再次访问时，判断 if (singleton == null)，直接 return 实例化对象，也避免的反复进行方法同步.
 * 线程安全； 延迟加载； 效率较高
 *
 * 结论：在实际开发中， 推荐使用这种单例设计模式
 */
public class SingletonTest {
    public static void main(String[] args) {

        Singleton instance = Singleton.getInstance();
        Singleton instance1 = Singleton.getInstance();

        System.out.println(instance == instance1);
        System.out.println(instance1.hashCode() +" : " + instance.hashCode());
    }
}

class Singleton{
    /**
     * 这个实例变量用volatile关键字修饰，保证线程内存可见
     */
    private static volatile Singleton instance;

    private Singleton(){
    }
    public static Singleton getInstance(){
        if(instance == null) {
            synchronized (Singleton.class) {
                if(instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}