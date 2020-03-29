package com.learn.design.composite;

/**
 * @author xrb
 * @create 2020-03-29 16:10
 * 合成复用原则
 */
public class HcfyRule {
    public static void main(String[] args) {
        System.out.println("------继承------");
        B1 b1 = new B1();
        b1.bUse();

        System.out.println("------依赖------");
        B2 b2 = new B2();
        b2.bUse(new A());

        System.out.println("------聚合------");
        B3 b3 = new B3(new A());
        b3.bUse();

        System.out.println("------组合------");
        B4 b4 = new B4();
        b4.bUse();
    }
}

//需求：类B想用类A的方法

class A {
    public void a1function(){
        System.out.println("类A的方法被使用......");
    }
}

//方法一：使用继承
class B1 extends A {
    public void  bUse(){
        System.out.println("类B开始使用类A的方法...start");
        this.a1function();
        System.out.println("类B使用类A的方法技术...end");
    }
}

//方法二：使用依赖
class B2{
    public void bUse(A a){
        System.out.println("类B开始使用类A的方法...start");
        a.a1function();
        System.out.println("类B使用类A的方法技术...end");
    }
}

//方法三：使用聚合
class B3{
    private A a;

    public B3(A a) {
        this.a = a;
    }

    public void bUse(){
        System.out.println("类B开始使用类A的方法...start");

        a.a1function();
        System.out.println("类B使用类A的方法技术...end");
    }
}

//方法四：使用组合
class B4{
    private A a = new A();
    public void bUse(){
        System.out.println("类B开始使用类A的方法...start");
        a.a1function();
        System.out.println("类B使用类A的方法技术...end");
    }
}
