package com.learn.design;

import java.util.Calendar;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author xrb
 * @create 2020-03-30 22:15
 */
public class TestJDK {
    public static void main(String[] args) {
        Hashtable hashtable =  new Hashtable();
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap<String,Object>();

        Calendar cal = Calendar.getInstance();
// 注意月份下标从 0 开始，所以取月份要+1
        System.out.println("年:" + cal.get(Calendar.YEAR));
        System.out.println("月:" + (cal.get(Calendar.MONTH) + 1));
        System.out.println("日:" + cal.get(Calendar.DAY_OF_MONTH));
        System.out.println("时:" + cal.get(Calendar.HOUR_OF_DAY));
        System.out.println("分:" + cal.get(Calendar.MINUTE));
        System.out.println("秒:" + cal.get(Calendar.SECOND));

        StringBuilder sb = new StringBuilder();

    }
}
