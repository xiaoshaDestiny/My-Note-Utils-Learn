package com.test;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author xu.rb
 * @since 2020-04-30 12:29
 */
public class Test {

    @org.junit.Test
    public void test08() {
        for (int i = 0; i < 6; i++) {
            Random random = new Random();
            int in = random.nextInt(40);
            System.out.println(in);
        }
    }


    @org.junit.Test
    public void test07() {
        //参数1
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 35; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("netWeight", 35);
            map.put("otherWeight", 35);
            map.put("other", "other" + i);
            list.add(map);
        }
        getNumbers(list, "otherWeight", 35.0, 0.4, 34.5, 35.5);
    }

    /**
     * 造假
     *
     * @param list        List<HashMap<String,Object>>
     * @param field       字段 String
     * @param avg         均值 Double
     * @param ratio       比例 Double
     * @param lowLevel    下限 Double
     * @param heightLevel 上限 Double
     */
    public static List<Map<String, Object>> getNumbers(List<Map<String, Object>> list, String field, Double avg, Double ratio, Double lowLevel, Double heightLevel) {

        Double cal = list.size() * ratio;
        Integer num = cal.intValue();
        if (num % 2 != 0) {
            num = num - 1;
        }

        Set s = new HashSet<Double>();
        for (int i = 0; i < 200; i++) {
            BigDecimal bd = new BigDecimal(Math.random() * (heightLevel - lowLevel) + lowLevel).setScale(1, BigDecimal.ROUND_DOWN);
            Double sjNum = bd.doubleValue();
            if (sjNum.doubleValue() != avg.doubleValue() && sjNum.doubleValue() < avg.doubleValue()) {
                s.add(sjNum);
            }
        }
        Object[] sjList = s.toArray();

        ArrayList<Integer> indexList = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            Random random = new Random();
            Integer in = random.nextInt(list.size());
            boolean findFlag = false;
            for (int j = 0; j < indexList.size(); j++) {
                if (indexList.get(j) == in) {
                    findFlag = true;
                    i = i - 1;
                }
            }
            if (findFlag != true) {
                indexList.add(in);
            }
        }

        for (int i = 0; i < indexList.size(); i++) {
            int sjIndex = indexList.get(i) % sjList.length;
            Double sjiz1 = (Double) sjList[sjIndex];
            Double sjiz2 = (avg - sjiz1) + avg;
            list.get(indexList.get(i)).put(field, sjiz1);
            list.get(indexList.get(i + 1)).put(field, sjiz2);
            i++;
        }
        return list;
    }


    @org.junit.Test
    public void test01() {
        char ch = 'q';
        String str = "aaa10";
        int i = ch % 10;
        System.out.println(i);

        int a = 7;
        int b = 16;
        System.out.println((a - 1) & b);
        System.out.println((b - 1) & a);
    }
}
