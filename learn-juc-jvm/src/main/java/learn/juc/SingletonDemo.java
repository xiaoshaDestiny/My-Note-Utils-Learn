package learn.juc;

/**
 * @author xrb
 * @create 2019-11-14 10:35
 *
 * 一个自己对象类型的属性私有
 * 构造器私有
 * 提供一个静态共有方法获取
 *
 *
 *
 * 单例模式有6中写法
 * 在单线程的环境中，最经典的有两种，懒汉模式和饿汉模式
 *
 */
public class SingletonDemo {

    private static volatile SingletonDemo instance = null;

    private SingletonDemo(){
        System.out.println(Thread.currentThread().getName()+"\t 构造器方法被执行！");
    }

    /**
     * 双端检索 DCL (Double Check lock)
     */
    public static SingletonDemo getInstance(){
        if(instance == null){
            synchronized (SingletonDemo.class){
                if(instance == null){
                    instance = new SingletonDemo();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        for (int i = 1; i <= 2000; i++) {
            new Thread(()->{
                SingletonDemo.getInstance();
            },String.valueOf(i)).start();
        }
    }
}
