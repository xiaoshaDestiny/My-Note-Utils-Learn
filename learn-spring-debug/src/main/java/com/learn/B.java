package com.learn;

/**
 * @author xu.rb
 * @since 2020-05-12 16:56
 */
public class B {
    private A a;

    public B() {
        System.out.println("B create");
        System.out.println("a is: "+this.getA());
    }

    public A getA() {
        return a;
    }

    public void setA(A a) {
        this.a = a;
    }
}
