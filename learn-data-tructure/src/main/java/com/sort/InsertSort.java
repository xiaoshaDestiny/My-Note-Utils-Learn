package com.sort;

import java.util.Arrays;

/**
 * @author xrb
 * @create 2020-04-14 20:17
 * 插入排序
 *
 *
 */
public class InsertSort {
    public static void main(String[] args) {

        int[] array = {5,8,1,7,0,4};
        System.out.println(Arrays.toString(array));
        System.out.println("===================");

        for (int i = 0; i < array.length -1 ; i++) {

            int insertValue = array[i+1];
            int insertIndex = i;

            while(insertIndex >= 0 && insertValue < array[insertIndex]){
                array[insertIndex + 1] = array[insertIndex];
                insertIndex --;
            }
            array[insertIndex +1] = insertValue;
            System.out.println(Arrays.toString(array));

        }




    }
}
