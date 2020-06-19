package com.learn.question;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xu.rb
 * @since 2020-06-19 09:30
 */
public class LeetCode {

    /**
     * LeetCode第一题：  两数之和
     * 找出数组中和是X的两个数的下标
     */
    @Test
    public void test01(){
        int[] arr = {4,5,8,7,2,1};  //数组
        int sum  = 12;              //找数组中和是12

        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < arr.length; i++) {
            int c = sum - arr[i];
            if (map.containsKey(c)) {
                int[] res =  {map.get(c), i};
                System.out.println(Arrays.toString(res));
                //break;
            }
            map.put(arr[i], i);
        }
        System.out.println(map.toString());
    }




}
