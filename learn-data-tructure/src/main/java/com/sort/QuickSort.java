package com.sort;

import java.util.Arrays;

/**
 * @author xrb
 * @create 2020-04-15 17:42
 * 快速排序
 *
 * 它是对冒泡排序的改进
 *
 */
public class QuickSort {
    public static void main(String[] args) {
        int[] arr = {1,7,5,23,2,3};
        quickSort(arr,0,arr.length -1);
        System.out.println(Arrays.toString(arr));

    }

    public static  void quickSort(int[] arr, int left,int right){
        System.out.println(Arrays.toString(arr));
        int l = left;
        int r = right;
        int pivot = arr[(left+right)/2];
        int temp = 0;

        //左边索引小于右边索引
        //让比pivot小的值放到左边 大的放到右边
        while( l < r){



            //从左边一直找，直到找到一个大于等于pivot的值 才退出
            while (arr[l] < pivot){
                l ++;
            }

            //从右边找
            while(arr[r] > pivot){
                r --;
            }

            // l >= r说明左边全部是排好序的了
            //右边也是全部排好序的了
            if(l >= r){
                break;
            }

            //交换
            temp = arr[r];
            arr[r] = arr[l];
            arr[l] = temp;

            //交换完毕 arr[l] = pivot  r--
            //交换完毕 arr[r] = pivot  l++

            if(arr[l] == pivot){
                r --;
            }

            if(arr[r] == pivot){
                l ++;
            }


            //保证不会内存溢出
            if(l == r){
                l++;
                r--;
            }

            if(left < r){
                quickSort(arr,left,r);
            }

            if(right > l){
                quickSort(arr,l,right);
            }
        }
    }
}
