package learn.java8;

import learn.java8.predict.People;
import org.junit.Test;

import java.util.*;
import java.util.stream.Stream;


/**
 * @author xrb
 * @create 2020-01-14 15:27
 */
public class Stream1 {
    List<People> peoples = Arrays.asList(
            new People(101, "张三", 18, 9999.99,"云南"),
            new People(102, "李四", 18, 6666.66,"云南"),
            new People(102, "李四", 8, 6666.66,"云南"),
            new People(103, "王二", 28, 3333.33,"云南"),
            new People(104, "赵八", 38, 7777.77,"云南"),
            new People(104, "赵八", 28, 7777.77,"云南"),
            new People(104, "赵八", 48, 7777.77,"云南"),
            new People(104, "赵八", 8, 7777.77,"云南"),
            new People(105, "孙六", 38, 5555.55,"云南")
    );

    List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();


    public void initData(){
        HashMap<String,String> testMap1 = new HashMap<>();
        testMap1.put("column_a","ab1");
        testMap1.put("column_b","20");
        testMap1.put("column_c","1555.3");
        list.add(testMap1);

        HashMap<String,String> testMap2 = new HashMap<>();
        testMap2.put("column_a","ab1");
        testMap2.put("column_b","30");
        testMap2.put("column_c","142.36");
        list.add(testMap2);

        HashMap<String,String> testMap3 = new HashMap<>();
        testMap3.put("column_a","ab2");
        testMap3.put("column_b","20");
        testMap3.put("column_c","452.36");
        list.add(testMap3);

        HashMap<String,String> testMap4 = new HashMap<>();
        testMap4.put("column_a","ab2");
        testMap4.put("column_b","30");
        testMap4.put("column_c","442.16");
        list.add(testMap4);
    }

    public static Stream<Character> filterCharacter(String str){
        List<Character> list = new ArrayList<>();
        for (Character c : str.toCharArray()){
            list.add(c);
        }
        return list.stream();
    }

    @Test
    public void test01(){
        initData();
        list.stream().filter(map -> "20".equals(map.get("column_b"))).forEach(System.out::println);

    }

    /**
     * 映射
     * map——接收 Lambda ， 将元素转换成其他形式或提取信息。接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。
     * flatMap——接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流
     */
    @Test
    public void test02(){
        List<String> list1 = Arrays.asList("aaa","bbb","ccc","ddd","eee");

        peoples.stream().map(People::getName).distinct().forEach(System.out::println);

        Stream<Stream<Character>> stream = list1.stream().map(Stream1::filterCharacter);
        stream.forEach( mapstream -> mapstream.forEach(System.out::println));

        //功能同上
        list1.stream().flatMap(Stream1::filterCharacter).forEach(System.out::println);
    }


    /**
     *      sorted()——自然排序
     *      sorted(Comparator com)——定制排序
     */
    @Test
    public void test03() {
        peoples.stream()
                .sorted( (p1,p2) -> {
                    if(p1.getAge() == p2.getAge()){
                        return p1.getName().compareTo(p2.getName());
                    }else {
                        if(p1.getAge() > p2.getAge()){
                            return 1;
                        }else {
                            return -1;
                        }
                    }

                })
                .forEach(System.out::println);

    }

}
