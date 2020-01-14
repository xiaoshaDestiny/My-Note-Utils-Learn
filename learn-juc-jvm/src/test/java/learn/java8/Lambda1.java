package learn.java8;

import learn.java8.predict.People;
import learn.java8.predict.PredictAge;
import learn.java8.predict.PredictSalary;
import learn.java8.predict.PredictStrategyMode;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author xrb
 * @create 2020-01-13 16:26
 */
public class Lambda1 {

    List<People> peoples = Arrays.asList(
            new People(101, "张三", 18, 9999.99,"云南"),
            new People(102, "李四", 18, 6666.66,"云南"),
            new People(103, "王二", 28, 3333.33,"云南"),
            new People(104, "赵八", 48, 7777.77,"云南"),
            new People(105, "孙六", 38, 5555.55,"云南")
    );

    public List<People> filterPeople(List<People> people, PredictStrategyMode<People> predict){
        List<People> list = new ArrayList<>();
        for (People employee : peoples) {
            if(predict.checkOne(employee)){
                list.add(employee);
            }
        }
        return list;
    }

    //策略设计模式
    @Test
    public void test1(){
        List<People> list = filterPeople(peoples, new PredictAge());
        for (People people : list) {
            System.out.println(people);
        }

        System.out.println("------------------------------------------");

        List<People> list2 = filterPeople(peoples, new PredictSalary());
        for (People people : list2) {
            System.out.println(people);
        }
    }

    //匿名内部类
    @Test
    public void test2(){
        List<People> list = filterPeople(peoples, new PredictStrategyMode<People>() {
            @Override
            public boolean checkOne(People p) {
                return p.getId() <= 103;
            }

            @Override
            public boolean checkTwo(People people) {
                return false;
            }
        });

        for (People people : list) {
            System.out.println(people);
        }
    }

    //Lambda 表达式
    @Test
    public void test3(){
        List<People> list = filterPeople(peoples, (p) -> p.getId() >= 103);
        list.forEach(System.out::println);

        System.out.println("------------------------------------------");

        List<People> list2 = filterPeople(peoples, e -> e.getSalary() >= 5000);
        list2.forEach(System.out::println);
    }

    //Stream API
    @Test
    public void test4(){
        peoples.stream().filter(e -> e.getAge() <= 35)
                .forEach(System.out::println);

        System.out.println("----------------------------------------------");
        peoples.stream().map(People::getName).limit(3).forEach(System.out::println);
        System.out.println("----------------------------------------------");
        peoples.stream().map(People::getAge).distinct().forEach(System.out::println);
    }


}
