package com.learn.item5;

/**
 * @author xrb
 * @create 2020-01-25 19:43
 *
 * 默认情况下是：11419
 * 设置栈大小之后-Xss256k    2460
 */
public class StackTest {
    public  void methodA(){
        int i = 10;
        int j = 20;
        long a = 122L;
        double b = 122.0000;
        int k = 10;
        methodB();
    }
    public  void methodB(){
        int k = 10;
        int m = 20;
    }

    private static int count = 1;
    public static void main(String[] args) {
        System.out.println(count);
        count ++;
        main(args);
    }
}
