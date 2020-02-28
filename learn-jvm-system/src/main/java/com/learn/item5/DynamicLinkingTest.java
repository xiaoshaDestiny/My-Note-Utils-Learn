package com.learn.item5;

/**
 * @author xrb
 * @create 2020-01-30 18:44
 */
public class DynamicLinkingTest {

    int num = 10;

    public void methodA(){
        System.out.println("methodA()...");
    }

    public void methodB(){
        System.out.println("methodB()...");
        methodA();
        num++;
    }
}
