package com.learn.block;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author xrb
 * @create 2019-12-03 16:58
 */
public class DataContainerNotSafe {
    public static void main(String[] args) {
        Map<String, String> map1 = Collections.synchronizedMap(new HashMap<>());
        Map<String, String> map2 = new ConcurrentHashMap<String, String>(10);
        for (int i = 1; i <=10 ; i++) {
            new Thread(()->{
                map1.put(UUID.randomUUID().toString().substring(0,3),UUID.randomUUID().toString().substring(0,3));
                map2.put(UUID.randomUUID().toString().substring(0,3),UUID.randomUUID().toString().substring(0,3));
                System.out.println("Collections工具类生成的map:"+map1);
                System.out.println("ConcurrentHashMap"+map2);
            },"线程"+i).start();
        }
    }
}
/*        Set<String> strSet = new HashSet<>();
        Map<String,String> strMap = new HashMap<>();
        try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }


                for (int i = 1; i <=10 ; i++) {
                new Thread(()->{
                strSet.add(UUID.randomUUID().toString().substring(0,2));
                System.out.println("Set类型:"+strSet);
                },"线程"+i).start();
                }

                try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }

                for (int i = 1; i <=10 ; i++) {
                new Thread(()->{
                strMap.put(UUID.randomUUID().toString().substring(0,2),UUID.randomUUID().toString().substring(0,2));
                System.out.println("Map类型:"+strMap);
                },"线程"+i).start();
                }*/
