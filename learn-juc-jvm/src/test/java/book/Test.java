package book;

import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

/**
 * @author xu.rb
 * @since 2020-09-21 22:13
 */
public class Test {


    @org.junit.Test
    public void test(){
        //无参数lambda
        Runnable noArg = ()-> System.out.println("hello world");
        noArg.run();

        //单参数lambda
        DoubleConsumer doubleConsumer = value -> System.out.println(value);
        doubleConsumer.accept(10.001);

        //表达式可以写多行
        Runnable notOneLine = ()-> {
            System.out.println("hello");
            System.out.println("word");
        };
        notOneLine.run();

        //多参数
        BinaryOperator<String> append = (x,y) -> x+y;
        System.out.println(append.apply("a","b"));

        //多参数
        BinaryOperator<Integer> add = (Integer x, Integer y) -> x+y;
        System.out.println(add.apply(1, 2));
    }


    @org.junit.Test
    public void test001(){
        List<String> strings = Stream.of("a", "b", "c").collect(Collectors.toList());

        Stream.of("hello", "word\n").map(string -> string.toUpperCase()).forEach(System.out::print);

        Stream.of("hello\n", "1abc\n","1def\n").filter(s -> s.startsWith("1")).forEach(System.out::print);

        List<Integer> integers = Stream.of(asList(1, 3), asList(2, 4)).flatMap(n -> n.stream()).collect(Collectors.toList());
        System.out.println(integers.size());
        integers.forEach(System.out::print);
        System.out.println();

        Object o = new Object();
        synchronized (o){
            System.out.println(Stream.of(1, 2, 3, 5).max(Comparator.comparingInt(x -> x*-1)).get());

        }

        synchronized (o){
            System.out.println(Stream.of(1, 2, 3, 5).max(Comparator.comparingInt(x -> x*-1)).get());
        }

        System.out.println(Stream.of(1, 2, 3, 5).max(Comparator.comparingInt(x -> x*-1)).get());

        System.out.println(Stream.of(1, 2, 3, 5).max(Comparator.comparingInt(x -> x*2)).get());


        System.out.println(Stream.of(1, 2, 4).reduce(1, (x, y) -> x * y));
        System.out.println(Stream.of(1, 2, 4).reduce(0, (x, y) -> x + y));
    }

    @org.junit.Test
    public void test002(){

        IntSummaryStatistics summaryStatistics = IntStream.of(1, 2, 3, 5).summaryStatistics();
        System.out.printf("%d,%d,%f",
                summaryStatistics.getMax(),
                summaryStatistics.getMin(),
                summaryStatistics.getAverage()
        );

    }





}
