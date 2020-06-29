package com.learn.sftemplate;

import org.junit.Test;

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
}
