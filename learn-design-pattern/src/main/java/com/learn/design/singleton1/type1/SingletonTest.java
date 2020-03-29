package com.learn.design.singleton1.type1;

/**
 * @author xrb
 * @create 2020-03-29 18:13
 * 单例模式的第一种写法： 饿汉式( 静态常量)
 *
 * 步骤1：构造器私有，保证外部不能 new
 * 步骤2：在类的内部创建对象实例
 * 步骤3：提供一个公有的静态方法，返回实例对象
 *
 * 优点：这种写法比较简单，就是在类装载的时候就完成实例化。避免了线程同步问题。没有多线程问题。
 * 缺点：在类装载的时候就完成实例化，没有达到 Lazy Loading 的效果。如果从始至终从未使用过这个实例，则会造成内存的浪费
 *      这种方式基于 classloder 机制避免了多线程的同步问题，
 *      不过，instance 在类装载时就实例化，在单例模式中大多数都是调用 getInstance 方法，但是导致类装载的原因有很多种，
 *      因此不能确定有其他的方式（或者其他的静态方法）导致类装载，这时候初始化 instance 就没有达到 lazy loading 的效果
 * 结论：这种单例模式可用，可能造成内存浪费
 *       确定一定会用到的时候采用这种方式。
 *
 *
 * 测试
 */
public class SingletonTest {

    public static void main(String[] args) {
        //测试
        Singleton instance = Singleton.getInstance();
        Singleton instance1 = Singleton.getInstance();

        //==比较一致，hashcode一致
        System.out.println(instance == instance1);
        System.out.println(instance.hashCode());
        System.out.println(instance1.hashCode());
    }
}


class Singleton{

    /**
     * 步骤1
     */
    private Singleton(){
    }

    /**
     * 步骤2
     */
    private final static  Singleton INSTANCE = new Singleton();

    /**
     * 步骤3
     * @return
     */
    public static Singleton getInstance(){
        return INSTANCE;
    }
}
