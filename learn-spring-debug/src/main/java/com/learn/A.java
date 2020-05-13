package com.learn;

/**
 * @author xu.rb
 * @since 2020-05-12 16:55
 */
public class A {
    private B b;

    public A() {
        System.out.println("A create");
        System.out.println("b is: "+this.getB());
    }

    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }
}
