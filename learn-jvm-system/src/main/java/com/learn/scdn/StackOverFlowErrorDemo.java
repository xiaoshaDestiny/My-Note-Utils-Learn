package com.learn.scdn;

/**
 * @author xrb
 * @create 2020-02-03 12:04
 */
public class StackOverFlowErrorDemo {
    private static int count = 1;

    public static void main(String[] args) {
        System.out.println(count++);
        main(args);

    }
}
