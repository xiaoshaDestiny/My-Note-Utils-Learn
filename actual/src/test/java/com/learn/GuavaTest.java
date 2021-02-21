package com.learn;

import com.google.common.collect.*;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author xu.rb
 * @since 2020-12-31 00:00
 */
@Slf4j
public class GuavaTest {
    @Test
    public void testGuavaTable() {
        //Table<R,C,V> == Map<R,Map<C,V>>
        HashBasedTable<String, String, String> table = HashBasedTable.create();
        table.put("北京","男","小明");
        table.put("北京","男","小明1");
        table.put("北京","女","小莉");
        table.put("上海","男","小张");
        table.put("上海","女","小丽");

        String s = table.get("北京", "男");
        String s1 = table.get("上海", "女");
        Map<String, String> man = table.column("男");
        Map<String, String> beijin = table.row("北京");

        log.info(String.valueOf(table.size()));//4
        log.info(s);//小明1
        log.info(s1);//小丽
        log.info(man.toString());//{北京=小明1, 上海=小华}
        log.info(beijin.toString());//{男=小明1, 女=小莉}
    }

    @Test
    public void testGuavaArrayListMultimap() {
        ArrayListMultimap<Integer, String> multiMap = ArrayListMultimap.create();
        multiMap.put(1,"V1");
        multiMap.put(1,"V11");
        multiMap.put(2,"V2");
        multiMap.put(2,"V22");
        multiMap.put(3,"V3");
        multiMap.put(3,"V3");

        Set<Integer> keySet = multiMap.keySet();
        List<String> v3 = multiMap.get(3);

        log.info(multiMap.toString());//{1=[V1, V11], 2=[V2, V22], 3=[V3, V3]}
        log.info(v3.toString());//[V3, V3]
        log.info(String.valueOf(multiMap.size()));//6
        log.info(keySet.toString());//[1, 2, 3]
    }

    @Test
    public void testGuavaHashMultimap() {
        HashMultimap<Integer, String> multiMap = HashMultimap.create();
        multiMap.put(1,"V1");
        multiMap.put(1,"V11");
        multiMap.put(2,"V2");
        multiMap.put(2,"V22");
        multiMap.put(3,"V3");
        multiMap.put(3,"V3");

        Set<String> v1Set = multiMap.get(1);
        Set<Integer> keySet = multiMap.keySet();

        //log.info(multiMap.toString());{1=[V1, V11], 2=[V2, V22], 3=[V3]}
        //log.info(String.valueOf(multiMap.size()));5
        //log.info(String.valueOf(v1Set.toString()));[V1, V11]
        //log.info(String.valueOf(keySet.toString()));[1, 2, 3]
    }



    @Test
    public void testGuavaList(){
        //快速创建列表（自动识别类型）
        List<String> aWeek = Lists.newArrayList("Mon","Tues","Wed","Thur","Fri","Sat","Sun");
        ArrayList<Integer> num = Lists.newArrayList(2,3,5,7,11,13);

        //连接两个List 去重
        List<Object> collect = Stream.concat(num.stream(), Stream.of(13,17)).distinct().collect(Collectors.toList());

        //将列表反序返回新列表,不改变原来的列表顺序
        List<Integer> reverseNums = Lists.reverse(num);

        //创建不可变列表
        ImmutableList<String> weekend = ImmutableList.of("Sat","Sun");
        //String sat = weekend.set(0, "SAT");//Will Throw UnsupportedOperationException


        HashMap<Object, Object> objectObjectHashMap = Maps.newHashMap();
    }

    @Test
    public void testStringIntern(){
        String str1 = new StringBuilder("he").append("llo").toString();
        System.out.println(str1.intern() == str1);
        //hello这个字符串，在字符串的常量池中没有，添加字符串进常量池，返回引用，和上面创建的一致,结果为true

        String str2 = new StringBuilder("ja").append(" va").toString();
        System.out.println(str1.intern() == str2);
        //java这个字符串，在常量池中已经存在，不是第一次遇到，返回已经有的“java”的引用，和本次创建在java堆里面的引用不一致，结果为false
    }
}
