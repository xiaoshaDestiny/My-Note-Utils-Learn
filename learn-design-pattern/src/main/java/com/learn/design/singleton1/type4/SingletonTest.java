package com.learn.design.singleton1.type4;

/**
 * @author xrb
 * @create 2020-03-29 18:49
 * 单例模式的第四种写法： 懒汉式(使用到再创建 线程安全 同步方法)
 *
 * 步骤1：构造器私有，保证外部不能 new
 * 步骤2：在类的内部定义static对象的变量
 * 步骤3：提供一个静态的公有方法，当使用到该方法时，才去创建 instance
 *        为了解决线程安全问题，在get方法上加synchronized关键字
 *
 * 解决了线程安全问题
 * 但是效率太低了，每个线程在想获得类的实例时候，执行 getInstance()方法都要进行同步。
 * 而其实这个方法只执行一次实例化代码就够了，后面的想获得该类实例，直接 return 就行了。
 * 方法进行同步效率太低
 * 结论：在实际开发中， 不推荐使用这种方式
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
    public static synchronized Singleton getInstance(){
        if(instance == null){
            instance = new Singleton();
        }
        return instance;
    }
}