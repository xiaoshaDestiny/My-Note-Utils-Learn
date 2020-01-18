package learn.java8;

import learn.java8.predict.People;
import org.junit.Test;

import java.io.PrintStream;
import java.util.Date;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author xrb
 * @create 2020-01-14 11:56
 */
public class MethodRef {

    //静态方法引用
    @Test
    public void test1(){
        PrintStream ps = System.out;
        Consumer<String> consumer = str-> ps.println(str);
        consumer.accept("aaa");

        Consumer<Integer> consumer1 = ps::println;
        consumer1.accept(110);
    }

    //对象引用
    @Test
    public void test2(){
        People people = new People(101,"六",22,5555.55,"三体星");
        Supplier<String> supplier = () -> people.getAddress();
        System.out.println(supplier.get());

        Supplier<Integer> supplier1 = people::getAge;
        System.out.println(supplier1.get());
    }
    @Test
    public void test3(){
        BiFunction<Object,Object,Integer> biFunction = (x,y) -> {
            int a = Integer.parseInt((String)x);
            int b = Integer.parseInt((String)y);
            return Math.max(a,b);
        };
        Integer apply = biFunction.apply("222", "201");
        System.out.println(apply);




    }

    @Test
    public void test4(){

    }

    @Test
    public void test5(){

    }
    @Test
    public void test6(){

    }
    @Test
    public void test7(){

    }
    @Test
    public void test8(){

    }

}
