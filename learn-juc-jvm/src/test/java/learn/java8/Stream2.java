package learn.java8;

import com.atguigu2.java8.Employee;
import org.junit.Test;

import javax.sound.midi.Soundbank;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

/**
 * @author xrb
 * @create 2020-01-14 19:24
 */
public class Stream2 {
    /*  终止操作
    allMatch——检查是否匹配所有元素
    anyMatch——检查是否至少匹配一个元素
    noneMatch——检查是否没有匹配的元素
    findFirst——返回第一个元素
    findAny——返回当前流中的任意元素
    count——返回流中元素的总个数
    max——返回流中最大值
    min——返回流中最小值
 */
    List<Employee> emps = Arrays.asList(
            new Employee(102, "李四", 8, 6666.66,  Employee.Status.BUSY),
            new Employee(101, "张三", 18, 9999.99, Employee.Status.FREE),
            new Employee(101, "张三", 18, 9999.99, Employee.Status.FREE),
            new Employee(101, "张三", 18, 9999.99, Employee.Status.FREE),
            new Employee(101, "张三", 18, 9999.99, Employee.Status.FREE),
            new Employee(103, "王五", 48, 3333.33, Employee.Status.VOCATION),
            new Employee(104, "赵六", 18, 7777.77,  Employee.Status.BUSY),
            new Employee(104, "赵六", 18, 7777.77,  Employee.Status.VOCATION),
            new Employee(104, "赵六", 1, 7777.77,  Employee.Status.FREE),
            new Employee(105, "田七", 28, 5555.55,  Employee.Status.BUSY)
    );

    @Test
    public void test11(){
        emps.stream().filter(x -> !x.getName().equals("张三")).forEach(System.out::println);
        System.out.println("=================================");
        emps.stream().collect(Collectors.toSet()).forEach(System.out::println);
        System.out.println("=================================");
        emps.stream().filter(x -> x.getSalary()>0).sorted( (x,y)->Double.compare(x.getSalary(),y.getSalary())).forEach(System.out::println);
    }

    @Test
    public void test10(){
        Integer[] nums = new Integer[]{1,2,3,4,5};
        Arrays.stream(nums).map(x ->x*x).forEach(System.out::println);

        System.out.println("----------");
        System.out.println( emps.stream().map( e -> 1).reduce(Integer::sum).get());

    }


    //分片
    @Test
    public void test07(){
        System.out.println(emps.stream().collect(Collectors.partitioningBy( x ->x.getSalary()>5000)));
    }

    @Test
    public void test06(){
        //分组
        Map<Employee.Status, List<Employee>> map = emps.stream().collect(Collectors.groupingBy(Employee::getStatus));
        System.out.println(map);

        //多级分组
        Map<Employee.Status, Map<Integer, List<Employee>>> collect = emps.stream().collect(Collectors.groupingBy(Employee::getStatus, Collectors.groupingBy(Employee::getAge)));
        System.out.println(collect);
    }


    @Test
    public void test05(){
        System.out.println(emps.stream().collect(Collectors.summarizingDouble(Employee::getSalary)));
        //最大值
        Optional<Employee> collect = emps.stream().collect(Collectors.maxBy((x, y) -> Double.compare(x.getSalary(), y.getSalary())));
        System.out.println(collect.get());
        //最小值
        Optional<Double> collect1 = emps.stream().map(Employee::getSalary).collect(Collectors.minBy(Double::compare));
        System.out.println(collect1.get());
    }

    /**
     *  收集
     *  collect——将流转换为其他形式。接收一个 Collector接口的实现，用于给Stream中元素做汇总的方法
     */
    @Test
    public void test04(){
        System.out.println();
        List<String> collect = emps.stream().map(x -> x.getName()).collect(Collectors.toList());
        System.out.println(collect);

        System.out.println(emps.stream().map(Employee::getName).collect(Collectors.toSet()));
        HashSet<String> collect1 = emps.stream().map(Employee::getName).collect(Collectors.toCollection(HashSet::new));
        System.out.println(collect1);

        emps.stream().collect(Collectors.toSet()).forEach(System.out::println);
    }

    /**
     * 归约
     * reduce(T identity, BinaryOperator) / reduce(BinaryOperator) ——可以将流中元素反复结合起来，得到一个值。
     */
    @Test
    public void test03(){
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        Integer reduce = list.stream().reduce(0, (x, y) -> x + y);
        System.out.println(reduce);
        Integer reduce1 = emps.stream().map(x -> x.getAge()).reduce(0, (x, y) -> x + y);
        System.out.println(reduce1);

        System.out.println(emps.stream().map(x->x.getAge()).reduce(Integer::sum).get());
    }

    @Test
    public void test02(){
        System.out.println(emps.stream().count());
        System.out.println(emps.stream().max((x,y) -> Double.compare(x.getSalary(),y.getSalary())).get());
        System.out.println(emps.stream().map(x -> x.getSalary()).min(Double::compareTo).get());

    }

    @Test
    public void test1(){
        System.out.println(emps.stream().allMatch( e -> e.getStatus().equals(Employee.Status.BUSY)));
        System.out.println(emps.stream().anyMatch( e -> e.getStatus().equals(Employee.Status.FREE)));
        System.out.println(emps.stream().noneMatch( e -> e.getStatus().equals(Employee.Status.FREE)));

        Optional<Employee> first = emps.stream().sorted((x, y) -> Double.compare(x.getSalary(), y.getSalary())).findFirst();
        Optional<Employee> first1 = emps.stream().sorted((x, y) -> -Double.compare(x.getSalary(), y.getSalary())).findFirst();
        System.out.println(first.get());
        System.out.println(first1.get());

        Optional<Employee> any = emps.stream().filter(e -> e.getStatus().equals(Employee.Status.FREE)).findAny();
        System.out.println(any.get());

        boolean b = emps.stream().allMatch(e -> e.getStatus().equals(Employee.Status.FREE));
        System.out.println(b);
    }

}
