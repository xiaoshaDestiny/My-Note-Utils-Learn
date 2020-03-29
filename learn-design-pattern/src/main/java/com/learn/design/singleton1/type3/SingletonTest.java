package com.learn.design.singleton1.type3;

/**
 * @author xrb
 * @create 2020-03-29 18:49
 * 单例模式的第三种写法： 懒汉式(使用到再创建 线程不安全)
 *
 * 步骤1：构造器私有，保证外部不能 new
 * 步骤2：在类的内部定义static对象的变量
 * 步骤3：提供一个静态的公有方法，当使用到该方法时，才去创建 instance
 *
 * 起到了 Lazy Loading 的效果，但是只能在单线程下使用。
 * 如果在多线程下，一个线程进入了 if (singleton == null)判断语句块，还未来得及往下执行，另一个线程也通过了这个判断语句，
 * 这时便会 产生多个实例。所以在多线程环境下不可使用这种方式
 *
 * 结论：在实际开发中， 不要使用这种方式.
 */
public class SingletonTest {
    public static void main(String[] args) {

        Singleton instance = Singleton.getInstance();
        Singleton instance1 = Singleton.getInstance();

        /**
         * ==比较一致，hashcode一致
         */
        System.out.println(instance == instance1);
        System.out.println(instance1.hashCode() +" : " + instance.hashCode());
    }
}

class Singleton{
    private static Singleton instance;

    private Singleton(){

    }
    public static Singleton getInstance(){
        if(instance == null){
            instance = new Singleton();
        }
        return instance;
    }
}