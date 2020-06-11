package learn.test;

/**
 * @author xrb
 * @create 2020-03-19 15:13
 */
public class MyTest {
    public static void main(String args[]) throws CloneNotSupportedException {

        Dog dog = new Dog("aa",new Cat("a",1));


        //深拷贝  dog是两个dog cat也要是两个cat
        //实现方法  1、重写Object类的clone方法  2、利用序列化
        Dog clone = (Dog) dog.clone();
        System.out.println(dog.hashCode()  + "  " + clone.hashCode());
        System.out.println(dog.getCat().hashCode()  + "  " + clone.getCat().hashCode());

        System.out.println("------------------------------------");

        //序列化
        Dog dog1 = (Dog) dog.deepClone2();
        System.out.println(dog.hashCode()  + "  " + dog1.hashCode());
        System.out.println(dog.getCat().hashCode()  + "  " + dog1.getCat().hashCode());




    }
}
