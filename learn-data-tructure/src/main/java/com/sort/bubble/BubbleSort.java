package com.sort.bubble;

import java.util.Arrays;

/**
 * @author xrb
 * @create 2020-04-13 20:49
 * 冒泡排序
 *
 * 一共进行数组长度-1次的大循环
 *
 *
 * 每一趟找出 最大 的值确定
 *
 *
 *
 */
public class BubbleSort {
    public static void main(String[] args) {
        int[] arr = {4,-1,5,3};
        System.out.println(Arrays.toString(arr));

        for (int j = 0; j < arr.length - 1 ; j++) {
            //总共循环数组长度减一次
            int temp = 0;
            for (int i = 0; i < arr.length - 1 - j; i++) {
                //第一趟的比较次数 是数组长度-1次
                //第二趟          是数组长度-2次

                if(arr[i] > arr [i+1]){
                    temp = arr[i];
                    arr[i] = arr[i+1];
                    arr[i+1] = temp;
                }
            }
        }
        System.out.println(Arrays.toString(arr));
    }
}
