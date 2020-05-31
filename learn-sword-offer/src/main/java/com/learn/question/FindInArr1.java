package com.learn.question;

/**
 * @author xu.rb
 * @since 2020-05-28 17:31
 *
 * 在一个二维数组中（每个一维数组的长度相同），每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。
 * 请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
 */
public class FindInArr1 {

    public static void main(String[] args) {


    }

    public boolean find(int target, int [][] array) {
        boolean find = false;
        for(int i = 0;i< array.length;i++ ){
            for(int j=0;j<array[i].length;j++){
                if(array[i][j] == target){
                    return true;
                }
            }
        }
        return find;
    }
}
