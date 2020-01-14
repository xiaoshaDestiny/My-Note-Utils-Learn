package learn.java8;

import learn.java8.function.MyFunctionInterface;
import learn.java8.function.MyFunctionInterface2;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author xrb
 * @create 2020-01-14 9:33
 */
public class Lambda2 {
    public Integer simpleCalNum(Integer num, MyFunctionInterface myInterface){
        return myInterface.simpleCalNumber(num);
    }
    public Integer calTwoNumber(Integer num1, Integer num2, MyFunctionInterface2 myInterface2){
        return myInterface2.claTwoNumber(num1,num2);
    }

    @Test
    public void test1(){
        System.out.println(simpleCalNum(10, x -> x+1));
    }

    @Test
    public void test2(){
        System.out.println(calTwoNumber(10,11, (x,y) -> {
            x = x + 1;
            y = y + 1;
            return x + y;
        }));
    }


    /**
     * Consumer<T> 消费型接口 :
     * 有输入参数 没有返回的结果
     * 用于将数据扔给消息队列？消费者模式。看一下 forEach方法
     */
    @Test
    public void test3(){
        putNumInMQ("mq-data", (consumer) ->{
            System.out.println(consumer + "--->:check data is a good data");
            System.out.println(consumer + "--->:put into MQ");
        });
    }
    public void putNumInMQ(Object o, Consumer<Object> consumer){
        consumer.accept(o);
    }


    /**
     * Supplier<T> 供给型接口 :
     * 没有输入参数，只有输出数据
     * 从MQ下一个消息返回
     */
    @Test
    public void test4(){
        List<Integer> numList = getNumList(10, () -> (int)(Math.random() * 100));

        for (Integer num : numList) {
            System.out.println(num);
        }
    }
    //需求：产生指定个数的整数，并放入集合中
    public List<Integer> getNumList(int num, Supplier<Integer> sup){
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < num; i++) {
            Integer n = sup.get();
            list.add(n);
        }

        return list;
    }

    @Test
    public void test5(){
        System.out.println(getNextMQ(()-> "地址是127.0.0.1"));
    }
    public Object getNextMQ(Supplier<Object> supplier){
        System.out.println("获取MQ连接..");
        System.out.println(supplier.get()+ "---->:获取数据成功，值是：110");
        return 110;
    }

    //Predicate<T> 断言型接口：
    @Test
    public void test6(){
        List<String> list = Arrays.asList("Hello", "atguigu", "Lambda", "www", "ok");
        List<String> strList = filterStr(list, (s) -> s.length() > 3);

        for (String str : strList) {
            System.out.println(str);
        }
    }

    //需求：将满足条件的字符串，放入集合中
    public List<String> filterStr(List<String> list, Predicate<String> pre){
        List<String> strList = new ArrayList<>();

        for (String str : list) {
            if(pre.test(str)){
                strList.add(str);
            }
        }

        return strList;
    }


    /**
     * Function<T, R> 函数型接口：
     * 参数T运算之后得到参数R
     * 有输入  有输出
     */
    @Test
    public void test7(){
        String newStr = strHandler("\t\t\t aaaBBBCCCDD   ", (str) -> str.trim());
        System.out.println(newStr);

        String subStr = strHandler("hello word", (str) -> str.substring(2, 5));
        System.out.println(subStr);

        Integer num = strToInteger("121212121",(string) -> Integer.parseInt(string.substring(0,5)));
        System.out.println(num);
    }
    //需求：用于处理字符串
    public Integer strToInteger(String str, Function<String, Integer> fun){
        return fun.apply(str);
    }
    //需求：用于处理字符串
    public String strHandler(String str, Function<String, String> fun){
        return fun.apply(str);
    }
}
