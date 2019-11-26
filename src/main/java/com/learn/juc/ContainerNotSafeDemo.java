package com.learn.juc;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author xrb
 * @create 2019-11-17 19:49
 *          集合类线程不安全问题
 */

public class ContainerNotSafeDemo {
    public static void main(String[] args) {
        Map<String,String> map = new HashMap<>();
        Map<String, String> map1 = Collections.synchronizedMap(new HashMap<>());
        Map<String, String> map2 = new ConcurrentHashMap<String, String>();

        for (int i = 1; i < 30; i++) {
            new Thread(() -> {
                map2.put(UUID.randomUUID().toString().substring(0,3),UUID.randomUUID().toString().substring(0,8));
                System.out.println(map2);
            },"i").start();
        }

    }

    private void setNotSafe(){
        Set<String> set = new HashSet<>();                                  //线程不安全
        Set<String> set1 = Collections.synchronizedSet(new HashSet<>());    //借助Collections类
        Set<String> set2 = new CopyOnWriteArraySet<>();                     //OUC并发包下利用写时复制解决线程不安全问题

        for (int i = 1; i < 30; i++) {
            new Thread(() -> {
                set2.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(set2);
            },"i").start();
        }
    }


    private void arrayListNotSafe(){

  /*      List<String> list = Arrays.asList("a","b","c","d","e");
        list.forEach(System.out::println);

        Cat c1 = new Cat("1",1,"red");
        Cat c2 = new Cat("2",2,"yellow");
        Cat c3 = new Cat("3",3,"write");

        List<Cat> catList = Arrays.asList(c1,c2,c3);
        catList.forEach(c -> age = age +c.getAge());
        System.out.println(age);*/

/**
 * 写时复制 copyOnWrite
 * 容器即写时复制的容器 往容器添加元素的时候,不直接往当前容器object[]添加,而是先将当前容器object[]进行copy
 * 复制出一个新的object[] newElements 然后向新容器object[] newElements里面添加元素
 * 添加元素后,再将原容器的引用指向新的容器 setArray(newElements);
 * setArray(newElements);这样的好处是可以对copyOnWrite容器进行并发的读,而不需要加锁 因为当前容器不会添加任何容器.
 * 所以copyOnwrite容器也是一种读写分离的思想,读和写不同的容器.
 */
        List<String> list = new ArrayList<>();
        List<String> list1 = new Vector<>();
        List<String> list2 = Collections.synchronizedList(new ArrayList<>());
        List<String> list3 = new CopyOnWriteArrayList<>();

        for (int i = 1; i <=10 ; i++) {
            new Thread(()->{
                list3.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list3);
            },"i").start();

        }

    }
}
class Cat{
    private String name;
    private Integer age;
    private String color;

    public Cat(String name, Integer age, String color) {
        this.name = name;
        this.age = age;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}