package com.learn.design.singleton1.type5;

/**
 * @author xrb
 * @create 2020-03-29 18:49
 * 单例模式的第五种写法： 懒汉式(使用到再创建 线程安全 同步代码块)
 *
 * 步骤1：构造器私有，保证外部不能 new
 * 步骤2：在类的内部定义static对象的变量
 * 步骤3：提供一个静态的公有方法，当使用到该方法时，才去创建 instance
 *        为了解决线程安全问题，在get方法里面使用一个同步代码块
 *
 * 本意其实是对第四种方式的改进，因为方法4的同步效率太低，所以改为了同步代码块。
 * 但是：
 * 这种同步并不能起到线程同步的作用。假如两个线程同时进入了if语句，都有可能会产生多个实例。
 *
 * 结论：坚决不能使用这种单例模式。
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
    private static Singleton instance;

    private Singleton(){
    }
    public static  Singleton getInstance(){
        if(instance == null){
            synchronized (Singleton.class){
                instance = new Singleton();
            }
        }
        return instance;
    }
}