package com.learn.design.adapter7.classadapter;

/**
 * @author xrb
 * @create 2020-04-01 21:05
 */
public class Client {
    public static void main(String[] args) {
        Phone phone = new Phone();
        phone.cd(new VoltageAdapter());

    }
}
