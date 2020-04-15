package com.sort;

/**
 * @author xrb
 * @create 2020-04-16 0:00
 *
 * 二分查找
 */
public class BinarySearch {


    public static void main(String[] args) {
        int[] arr = {1,2,4,8,16,32};
        int index = binarySearch(arr,0,arr.length-1,33);
        System.out.println("index:"+index);
    }

    /**
     *
     * @param arr 数组
     * @param left 左边索引
     * @param right 右边索引
     * @param findValue 要查找的值
     * @return 如果找到就返回下标 没有就返回-1
     */
    public static int binarySearch(int[] arr,int left,int right,int findValue){

        if(left > right){
            return -1;
        }

        int mid = (left + right)/2;
        int midVal = arr[mid];

        if(findValue > midVal){
            //向右边递归
            return binarySearch(arr,mid+1,right,findValue);
        }else if(findValue < midVal){
            //向左递归
            return binarySearch(arr,left,mid-1,findValue);
        }else {
            return mid;
        }
    }
}
