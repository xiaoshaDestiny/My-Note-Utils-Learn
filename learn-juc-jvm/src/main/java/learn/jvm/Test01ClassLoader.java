package learn.jvm;

/**
 * @author xrb
 * @create 2019-12-30 22:59
 */
public class Test01ClassLoader {
    public static void main(String[] args) throws ClassNotFoundException {


        //1、根加载器   启动类加载器   bootstrap
        Object o = new Object();
        System.out.println("Object : "+o.getClass().getClassLoader());


        Class c = Class.forName("com.learn.jvm.HelloWord");
        System.out.println("反射 : "+c.getClassLoader());


        //3、应用程序类加载器 APP  Java也叫系统类加载器，加载当前应用的classpath的所有类
        Test01ClassLoader t1 = new Test01ClassLoader();
        System.out.println("Test01ClassLoader : "+t1.getClass().getClassLoader());
        System.out.println("Test01ClassLoader Parent : "+t1.getClass().getClassLoader().getParent());
        System.out.println("Test01ClassLoader Parent Parent: "+t1.getClass().getClassLoader().getParent().getParent());

    }
}
