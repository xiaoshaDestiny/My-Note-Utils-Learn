package com.learn.design.singleton1.type8;

/**
 * @author xrb
 * @create 2020-03-29 22:52
 * 单例模式的第八种写法： 枚举
 *
 * 这借助 JDK1.5 中添加的枚举来实现单例模式。
 * 不仅能避免多线程同步问题，而且还能防止反序列化重新创建新的对象。
 * 这种方式是 Effective Java  作者 Josh Bloch  提倡的方式
 *
 * 结论： 推荐使用
 */
public class SingletonTest {

    public static void main(String[] args) {
        Singleton instance = Singleton.INSTANCE;
        Singleton instance1 = Singleton.INSTANCE;
        System.out.println(instance == instance1);
        System.out.println(instance.hashCode());
        System.out.println(instance1.hashCode());
    }
}

enum Singleton{
    INSTANCE;
    public void syaOk(){
        System.out.println("ok(这里代表了这个枚举能干的事。。。)");
    }
}