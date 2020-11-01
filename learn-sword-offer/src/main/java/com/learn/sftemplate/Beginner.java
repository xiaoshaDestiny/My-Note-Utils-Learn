package com.learn.sftemplate;

import org.junit.Test;

import java.util.*;

/**
 * @author xu.rb
 * @since 2020-06-28 11:03
 */
public class Beginner {


    /**
     * 比较两个字符串 ，在 strA 字符串中找出 strB 字符串出现的第一个位置 (从 0 开始)。如果不存在，则返回 -1。
     *
     * xyz-abcd   abc
     */
    @Test
    public void test01(){
        String strA = "xyz-abc";
        String strB = "abc";
        char[] chaA = strA.toCharArray();
        char[] chaB = strB.toCharArray();

        for (int i = 0; i < (chaA.length - chaB.length) +1; i++) {
            for (int j = 0; j < chaB.length; j++) {
                if(chaA[i+j] != chaB[j]){
                    break;
                }

                if(chaB.length - 1 == j){
                    System.out.println(i);
                }
            }
        }
        System.out.println(-1);
    }

    /**
     * 给定一组不含重复元素的整数数组 nums，返回该数组所有可能的子集（幂集）。
     */
    @Test
    public void test02(){
        int[] num = {1,2,3,4};
        List<List<Integer>> result = new LinkedList<>();
        backtrack(num, 0, new LinkedList<>(), result);
        result.forEach(System.out::println);
    }

    //递归遍历数组，每到一个元素就有两种情况：1.选该元素 2.不选该元素
    private void backtrack(int[] nums, int current, LinkedList<Integer> track, List<List<Integer>> result) {
        //已经遍历完所有元素
        if (current == nums.length) {
            result.add(new LinkedList<>(track));
            return;
        }

        //不选当前元素
        backtrack(nums, current + 1, track, result);
        //选当前元素
        track.add(nums[current]);

        backtrack(nums, current + 1, track, result);
        //撤销选取当前元素（回溯）
        track.removeLast();
    }


}
