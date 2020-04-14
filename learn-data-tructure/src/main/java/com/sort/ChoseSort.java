package com.sort;

import java.util.Arrays;

/**
 * @author xrb
 * @create 2020-04-13 23:12
 * 选择排序
 *
 *
 * 第一趟找到  0-最后 最小的元素的下标 把这个元素和0位置的元素交换
 * 第二趟找到  1-最后 最小的元素的下标 把这个元素和1位置的元素交换
 * ......
 *
 */
public class ChoseSort {
    public static void main(String[] args) {
        int[] arr = {4,7,1,8,6,3};

        System.out.println(Arrays.toString(arr));

        int temp = 0;

        for (int j = 0; j < arr.length ; j++) {
            int minIndex = j;
            int min = arr[j];
            for (int i = j + 1; i < arr.length; i++) {
                if(min > arr [i]){
                    min = arr[i];
                    minIndex = i;
                }
            }
            temp = arr[j];
            arr[j] = arr[minIndex];
            arr[minIndex] = temp;
            System.out.println(Arrays.toString(arr));
        }
    }
}
