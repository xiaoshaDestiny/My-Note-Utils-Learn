package com.learn.design.adapter7.interfaceadapter;

/**
 * @author xrb
 * @create 2020-04-01 21:38
 */
public class Client {
    public static void main(String[] args) {
        SomeAdapter someAdapter = new SomeAdapter();
        someAdapter.m1();
        someAdapter.m2();

        new InterfaceAdapter(){
            @Override
            public void m2() {
                System.out.println("use m2 ...");
            }
        }.m2();
    }
}
