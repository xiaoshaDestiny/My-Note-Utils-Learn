package com.sort;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author xrb
 * @create 2020-04-13 22:26
 */
public class TestSort {

    //冒泡排序
    @org.junit.Test
    public void testBubble(){
        int[] array = {-1,5,8,-4,3};
        System.out.println(Arrays.toString(array));

        int temp = 0;
        for (int i = 0; i < array.length -1 ; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if(array[j] > array[j+1]){
                    temp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = temp;
                }
            }
            System.out.println(Arrays.toString(array));
        }
    }

    //选择排序
    @Test
    public void testSelect(){
        int[] array = {4,7,9,6,5,1};

        System.out.println(Arrays.toString(array));

        int temp = 0;
        for(int j=0; j<array.length; j++){
            int minIndex = j;
            int min = array[j];
            for(int i=1 + j ; i < array.length; i++){
                if(array[i] < min){
                    min = array[i];
                    minIndex = i;
                }
            }
            temp = array[j];
            array[j] = array[minIndex];
            array[minIndex] = temp;
        }
        System.out.println(Arrays.toString(array));
    }

    @Test
    public void testInsert(){
        int[] array = {9,4,5,7,6,1,3};
        System.out.println(Arrays.toString(array));

        for(int i=0;i<array.length-1;i++){
            int insertValue = array[i+1];
            int insertIndex = i;
            while(insertIndex >=0 && array[insertIndex] > insertValue){
                array[insertIndex + 1] = array[insertIndex];
                insertIndex--;
            }
            array[insertIndex + 1] = insertValue;
            System.out.println(Arrays.toString(array));
        }
    }
}
