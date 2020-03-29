package com.learn.design.singleton1.type2;

/**
 * @author xrb
 * @create 2020-03-29 18:13
 * 单例模式的第二种写法： 饿汉式(静态代码块)
 *
 * 步骤1：构造器私有，保证外部不能 new
 * 步骤2：在类的内部定义static对象的变量
 * 步骤3：在静态代码块中创建对象
 * 步骤4：提供一个公有的静态方法，返回实例对象
 *
 * 这种方式和方式一类似，只不过将类实例化的过程放在了静态代码块中，也是在类装载的时候，就执行静态代码块中的代码，初始化类的实例。
 * 优：这种写法比较简单，就是在类装载的时候就完成实例化。避免了线程同步问题。没有多线程问题。
 * 缺：不用就会造成自资源浪费。
 * 结论：这种单例模式可用，但是可能造成内存浪费
 *
 * 测试
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
    /**
     * 步骤1
     */
    private Singleton(){
    }

    /**
     * 步骤2
     */
    private static Singleton INSTANCE;

    /**
     * 步骤3
     */
    static {
        INSTANCE = new Singleton();
    }

    /**
     * 步骤4
     */
    public static Singleton getInstance(){
        return INSTANCE;
    }
}
