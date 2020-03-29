package com.learn.design.singleton1.type7;

/**
 * @author xrb
 * @create 2020-03-29 18:49
 * 单例模式的第七种写法： 静态内部类
 * 静态内部类的特点：
 * 1、当外边的类被装载的时候，内部类是不会被装载的
 * 2、静态内部类只会装载一次，在装载的时候，是线程安全的
 *
 *
 * 原理和特点：
 * 这种方式采用了类装载的机制来保证初始化实例时只有一个线程。
 * 静态内部类方式在 Singleton 类被装载时并不会立即实例化，而是在需要实例化时，
 * 调用 getInstance 方法，才会装载 SingletonInstance 类，从而完成 Singleton 的实例化。
 * 类的静态属性只会在第一次加载类的时候初始化，所以在这里，JVM 帮助我们保证了线程的安全性，在类进行初始化时，别的线程是无法进入的。
 * 优点：避免了线程不安全，利用 静态内部类特点实现延迟加载，效率高
 * 结论： 推荐使用.
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

    private Singleton(){}

    /**
     * 一个静态内部类，它的一个静态属性Singleton
     */
    private static class SingletonInstance{
        private static final Singleton INSTANCE = new Singleton();
    }

    public static Singleton getInstance(){
        return SingletonInstance.INSTANCE;
    }
}