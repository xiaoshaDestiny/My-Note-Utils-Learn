package com.learn.jvm;

/**
 * @author xrb
 * @create 2020-01-05 11:42
 */

public class StackOverFlowErrorDemo {
    public static void sayHello(){
        sayHello();
    }
    public static void main(String[] args) {
        sayHello();
    }
}
